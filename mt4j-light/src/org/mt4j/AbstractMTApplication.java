package org.mt4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mt4j.input.DesktopInputManager;
import org.mt4j.input.InputManager;
import org.mt4j.input.inputSources.IinputSourceListener;
import org.mt4j.util.ArrayDeque;

import java.awt.Window;
import java.awt.Point;

public abstract class AbstractMTApplication implements IMTApplication {
	private List<AbstractMTLayer<?>> layers;
	private InputManager manager;
	private Window window;
	protected ArrayDeque<IPreDrawAction> preDrawActions;
	private ArrayDeque<Runnable> invokeLaterActions;
	
	private Thread updateThread;
	private float updateRate = 60;

	public AbstractMTApplication(Window window) {
		this(window, true);
	}
	
	public AbstractMTApplication(Window window, boolean threaded) {
		this.window = window;
		preDrawActions = new ArrayDeque<IPreDrawAction>();
		invokeLaterActions = new ArrayDeque<Runnable>();
		layers = new ArrayList<>();
		manager = new DesktopInputManager(this);
		if (threaded)
		{
			updateThread = new Thread(new Runnable(){
				@Override
				public void run() {
					long lastTime = System.currentTimeMillis();
					while (true) {
						update();
						try {
							long timeout = (long) (1000 / updateRate - (System.currentTimeMillis() - lastTime));
							if (timeout > 0) Thread.sleep(timeout);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			updateThread.setDaemon(true);
			updateThread.start();
		}
	}
	
	public void update(){
		this.runApplication();
	}
	
	/* (non-Javadoc)
	 * @see org.mt4j.IMTApplication#registerPreDrawAction(org.mt4j.sceneManagement.IPreDrawAction)
	 */
	@Override
	public void registerPreDrawAction(final IPreDrawAction action){
		synchronized (preDrawActions) {
//			this.preDrawActions.addLast(action);
			invokeLater(new Runnable() {
				@Override
				public void run() {
					preDrawActions.addLast(action);
				}
			});
		}
	}

	
	/* (non-Javadoc)
	 * @see org.mt4j.IMTApplication#unregisterPreDrawAction(org.mt4j.sceneManagement.IPreDrawAction)
	 */
	@Override
	public void unregisterPreDrawAction(final IPreDrawAction action){
		synchronized (preDrawActions) {
			if (preDrawActions.contains(action)){
//				this.preDrawActions.remove(action);
				invokeLater(new Runnable() {
					@Override
					public void run() {
						preDrawActions.remove(action);
					}
				});
			}
		}
	}
	
	/**
	 * Main run loop.
	 * <li>Updates the time passed since the last time drawn.
	 * <li>Updates any animations with the new time delta.
	 * <li>Updates and draws the current scene.
	 * <li>Updates and draws the current scene transitions.
	 */
	private void runApplication(){ 
		//Process preDrawActions
		synchronized (preDrawActions) {
			for (Iterator<IPreDrawAction> iter = preDrawActions.iterator(); iter.hasNext();) {
				IPreDrawAction action = iter.next();
				action.processAction();
				if (!action.isLoop()){
					iter.remove(); 
				}
			}
		}
		//Run invoke later actions
		synchronized (invokeLaterActions) {
			while (!invokeLaterActions.isEmpty()){
				invokeLaterActions.pollFirst().run();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.mt4j.IMTApplication#invokeLater(java.lang.Runnable)
	 */
	@Override
	public void invokeLater(Runnable runnable){
		synchronized (invokeLaterActions) {
			invokeLaterActions.addLast(runnable);	
		}
	}
	
	public void addLayer(AbstractMTLayer<?> layer) {
		layers.add(layer);
	}
	public void removeLayer(AbstractMTLayer<?> layer) {
		layers.remove(layer);
	}
	
	public Window getWindow() {
		return window;
	}

	@Override
	public InputManager getInputManager() {
		return manager;
	}

	@Override
	public void setInputManager(InputManager inputManager) {
		manager = inputManager;
	}
	
	public float getUpdateRate() {
		return updateRate;
	}

	public void setUpdateRate(float updateRate) {
		this.updateRate = updateRate;
	}

	public AbstractMTLayer<?> findLayer(Point p) {
		return layers.stream().filter((AbstractMTLayer<?> l) -> l.isInLayer(p)).findFirst().orElse(null);
	}

}
