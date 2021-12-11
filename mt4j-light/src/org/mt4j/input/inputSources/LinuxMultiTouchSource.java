package org.mt4j.input.inputSources;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.ActiveCursorPool;
import org.mt4j.input.inputData.InputCursor;
import org.mt4j.input.inputData.MTFingerInputEvt;
import org.mt4j.input.inputData.MTLinuxTouchInputEvt;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;

import com.dgis.input.evdev.EventDevice;
import com.dgis.input.evdev.InputAxisParameters;
import com.dgis.input.evdev.InputEvent;
import com.dgis.input.evdev.InputListener;


public class LinuxMultiTouchSource extends AbstractInputSource {
	private static final ILogger logger = MTLoggerFactory.getLogger(LinuxMultiTouchSource.class.getName());
	static{
//		logger.setLevel(ILogger.ERROR);
//		logger.setLevel(ILogger.DEBUG);
		logger.setLevel(ILogger.INFO);
	}

	@SuppressWarnings("serial")
	private static Map<String, Point> knownDevices = new HashMap<String, Point>(){{
			put("G2Touch Multi-Touch by G2TSP", new Point(2240, 1440));
	}};
	private AbstractMTApplication app;
	private List<LinuxTouchDevice> touchDevices = new ArrayList<>();
	private Rectangle defaultMapping;

	public LinuxMultiTouchSource(AbstractMTApplication mtApp) {
		super(mtApp);
		this.app = mtApp;
		
		GraphicsDevice[] monitors = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		defaultMapping = Stream.of(monitors)
				.map(GraphicsDevice::getDefaultConfiguration)
				.map(GraphicsConfiguration::getBounds)
				.reduce((Rectangle a, Rectangle b) -> {
					Point tl = new Point(Math.min(a.x, b.x), Math.min(a.y, b.y));
					Point br = new Point(Math.max(a.x + a.width, b.x + b.width), Math.max(a.y + a.height, b.y + b.height));
					return new Rectangle(tl.x, tl.y, br.x - tl.x, br.y - tl.y);
				}).orElse(null);
		try {
			Files.walk(Paths.get("/dev/input/"))
				.filter((Path p) -> p.getFileName().toString().startsWith("event"))
				.map((Path p) -> {
					try {
						return new EventDevice(p.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				})
				.forEach((EventDevice ev) -> {
					if (ev.getSupportedEvents().containsKey((int)InputEvent.EV_KEY)
							&& ev.getSupportedEvents()
								.get((int)InputEvent.EV_KEY)
								.contains((int)InputEvent.BTN_TOUCH)) {
						String devName = ev.getDeviceName().trim();
						Optional<Rectangle> mapping = Optional.empty();
						if (knownDevices.containsKey(devName)) {
							mapping = Arrays.stream(monitors)
									// .filter((GraphicsDevice dev) -> new Point().equals(knownDevices.get(devName)))
									.map((GraphicsDevice dev) -> dev.getDefaultConfiguration().getBounds())
									.filter((Rectangle r) -> knownDevices.get(devName).equals(r.getLocation()))
									.findFirst();
						}
						if (ev.getSupportedEvents().containsKey((int)InputEvent.EV_ABS) 
								&& ev.getSupportedEvents().get((int)InputEvent.EV_ABS).contains((int)InputEvent.ABS_MT_SLOT)) {
							touchDevices.add(new LinuxMultiTouchDevice(ev, mapping.orElse(defaultMapping)));
						} else {
							touchDevices.add(new LinuxSingleTouchDevice(ev, mapping.orElse(defaultMapping)));
						}
//					} else {
//						//TODO: sometimes close() blocks forever?
//						Thread t = new Thread(() -> ev.close());
//						t.setDaemon(true);
//						t.start();
					}});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private abstract class LinuxTouchDevice implements InputListener {
		protected class LinuxInputCursor {
			int x, y;
			int eventType;
			long cursorId;
			public LinuxInputCursor() {
				eventType = -1;
			}
		}
		protected EventDevice ev;
		private Rectangle deviceBounds;
		private Rectangle displayBounds;
		
		public LinuxTouchDevice(EventDevice ev, Rectangle displayBounds) {
			this.ev = ev;
			this.displayBounds = displayBounds;
			this.deviceBounds = getDeviceBounds();
			ev.addListener(this);
		}
		
		protected void sendInputEvent() {
			LinuxInputCursor c = getCurrentCursor();
			Point2D p = new Point2D.Float((float)(c.x - deviceBounds.x) / deviceBounds.width, (float)(c.y - deviceBounds.y) / deviceBounds.height);
			Point location = new Point(displayBounds.x + (int)(p.getX() * displayBounds.width), displayBounds.y + (int)(p.getY() * displayBounds.height));
			
			AbstractMTLayer<?> layer;
			InputCursor cursor = null;
			if (c.eventType == MTFingerInputEvt.INPUT_STARTED) {
				layer = app.findLayer(location);
				cursor = new InputCursor();
				c.cursorId = cursor.getId();
				ActiveCursorPool.getInstance().putActiveCursor(cursor.getId(), cursor);
			} else {
				cursor = ActiveCursorPool.getInstance().getActiveCursorByID(c.cursorId);
				layer = cursor.getTarget();
			}
			if (layer != null) {
				layer.convertToLayer(location);
				AbstractCursorInputEvt evt = new MTLinuxTouchInputEvt(LinuxMultiTouchSource.this, location.x, location.y, c.eventType, cursor);
				evt.setTarget(layer);
				enqueueInputEvent(evt);
			}
			if (c.eventType == MTFingerInputEvt.INPUT_ENDED) {
				ActiveCursorPool.getInstance().removeCursor(c.cursorId);
			}
		}

		public abstract LinuxInputCursor getCurrentCursor();
		protected abstract Rectangle getDeviceBounds();
	}
	
	private class LinuxSingleTouchDevice extends LinuxTouchDevice {
		private LinuxInputCursor cursor;
		private Optional<Boolean> touch = Optional.empty();

		public LinuxSingleTouchDevice(EventDevice ev, Rectangle mapping) {
			super(ev, mapping);
			cursor = new LinuxInputCursor();
		}

		@Override
		public void event(InputEvent e) {
			if (e.type == InputEvent.EV_KEY && e.code == InputEvent.BTN_TOUCH) {
				touch = Optional.of(e.value > 0);
			} if (e.type == InputEvent.EV_ABS) {
				if (e.code == InputEvent.ABS_X) {
					cursor.x = e.value;
				} else if (e.code == InputEvent.ABS_Y) {
					cursor.y = e.value;
				}
			} else if (e.type == InputEvent.EV_SYN && e.code == InputEvent.SYN_REPORT) {
				sendInputEvent();
			}
		}
		
		@Override
		protected void sendInputEvent() {
			if (!touch.isPresent()) {
				cursor.eventType = MTFingerInputEvt.INPUT_UPDATED;
			} else if (touch.get()) {
				cursor.eventType = MTFingerInputEvt.INPUT_STARTED;
			} else {
				cursor.eventType = MTFingerInputEvt.INPUT_ENDED;
			}
			super.sendInputEvent();
			touch = Optional.empty();
		}

		@Override
		public LinuxInputCursor getCurrentCursor() {
			return cursor;
		}

		@Override
		protected Rectangle getDeviceBounds() {
			InputAxisParameters xParams = ev.getAxisParameters(InputEvent.ABS_X);
			InputAxisParameters yParams = ev.getAxisParameters(InputEvent.ABS_Y);
			return new Rectangle(xParams.getMin(), yParams.getMin(), xParams.getMax() - xParams.getMin(), yParams.getMax() - yParams.getMin());
		}
	}
	
	private class LinuxMultiTouchDevice extends LinuxTouchDevice {
		private HashMap<Integer, LinuxInputCursor> cursors = new HashMap<>();
		private int slot;
		private Optional<Integer> id = Optional.empty();
		
		private boolean updated;

		public LinuxMultiTouchDevice(EventDevice ev, Rectangle mapping) {
			super(ev, mapping);
			updated = true;
			slot = ev.getAxisParameters(InputEvent.ABS_MT_SLOT).getValue();
		}

		@Override
		public void event(InputEvent e) {
 			if (e.type == InputEvent.EV_ABS) {
				switch (e.code) {
				case InputEvent.ABS_MT_POSITION_X:
					cursors.get(slot).x = e.value;
					break;
				case InputEvent.ABS_MT_POSITION_Y:
					cursors.get(slot).y = e.value;
					break;
				case InputEvent.ABS_MT_TRACKING_ID:
					if (e.value > -1) {
						cursors.put(slot, new LinuxInputCursor());
					}
					id = Optional.of(e.value);
					break;
				case InputEvent.ABS_MT_SLOT:
					sendInputEvent();
					slot = e.value;
				}
				updated = false;
			} else if (e.type == InputEvent.EV_SYN && e.code == InputEvent.SYN_REPORT) {
				sendInputEvent();
			}
		}

		@Override
		protected void sendInputEvent() {
			if (updated) return;
			int eventId = -1;
			if (!id.isPresent()) {
				eventId = MTFingerInputEvt.INPUT_UPDATED;
			} else if (id.get() > -1) {
				eventId = MTFingerInputEvt.INPUT_STARTED;
			} else if (id.get() == -1) {
				eventId = MTFingerInputEvt.INPUT_ENDED;
			}
			getCurrentCursor().eventType = eventId;
			super.sendInputEvent();
			if (eventId == MTFingerInputEvt.INPUT_ENDED) {
				cursors.remove(slot);
			}
			id = Optional.empty();
			updated = true;
		}

		@Override
		public LinuxInputCursor getCurrentCursor() {
			return cursors.get(slot);
		}

		@Override
		protected Rectangle getDeviceBounds() {
			InputAxisParameters xParams = ev.getAxisParameters(InputEvent.ABS_MT_POSITION_X);
			InputAxisParameters yParams = ev.getAxisParameters(InputEvent.ABS_MT_POSITION_Y);
			return new Rectangle(xParams.getMin(), yParams.getMin(), xParams.getMax() - xParams.getMin(), yParams.getMax() - yParams.getMin());
		}
	}
}
