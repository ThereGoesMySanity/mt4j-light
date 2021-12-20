package basic;

import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.panProcessor.PanProcessorTwoFingers;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomEvent;
import org.mt4j.input.inputProcessors.componentProcessors.zoomProcessor.ZoomProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.logging.Log4jLogger;
import org.mt4j.util.logging.MTLoggerFactory;

public class SimpleSwing extends AbstractMTApplication {

	public SimpleSwing(JFrame window) {
		super(window);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(null);
		JPanel panel = new JPanel();
		MTLoggerFactory.setLoggerProvider(new Log4jLogger());
		AbstractMTApplication app = new SimpleSwing(frame);
		app.registerGlobalInputProcessor(new CursorTracer(app));

		AbstractMTLayer<JPanel> touchLayer = new AbstractMTLayer<JPanel>(app)
		{
			@Override
			public boolean processGestureEvent(MTGestureEvent ge) {
				System.out.println(ge.getId());
				if (ge instanceof ZoomEvent) {
					System.out.println(((ZoomEvent) ge).getCamZoomAmount());
				}
				return true;
			}
		};
		touchLayer.registerInputProcessor(new PanProcessorTwoFingers(500));
		touchLayer.registerInputProcessor(new ZoomProcessor(500));
		JLayer<JPanel> layer = new JLayer<JPanel>(panel, touchLayer);
		frame.add(layer);
		layer.setBounds(100, 100, 540, 380);
		
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
