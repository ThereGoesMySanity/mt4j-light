/***********************************************************************
 * mt4j Copyright (c) 2008 - 2009, C.Ruff, Fraunhofer-Gesellschaft All rights reserved.
 *  
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************/

package org.mt4j;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;

import org.mt4j.input.ComponentInputProcessorSupport;
import org.mt4j.input.GestureEventSupport;
import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.InputManager;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.AbstractComponentProcessor;
import org.mt4j.util.logging.ILogger;



/**
 * Use this class to create a new multitouch application.
 * <br>The best way to create your application would be to extend this class and
 * put the <code>main</code> method into that class.
 * In the <code>main</code> method call the <code>initialize()</code> method.
 * Then override the <code>startUp()</code> method which is called
 * automatically after the initialize method. The <code>startUp()</code> method can be used to
 * create your scenes (extend the <code>AbstractScene</code> class) and add them to
 * the application by calling <code>addScene</code> method.
 * 
 * <p>Internally, the main method of processings PApplet class is called with the class name
 * of the extended PApplet class as an argument. The PApplet class then instantiates the given
 * class and calls its setup() and then repeatedly its run() method.
 * 
 * @author Christopher Ruff
 * @param <T>
 */
public class AbstractMTLayer<T extends JComponent> extends LayerUI<T> implements IGestureEventListener {
	/** The Constant logger. */
	protected static ILogger logger;
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The input manager. */
	private InputManager inputManager;
	
	protected JLayer<T> layer;
	
	private AbstractMTApplication app;
	
	/** The input listeners. */
	private ArrayList<IMTInputEventListener> inputListeners;

	/** The input processors support. */
	private ComponentInputProcessorSupport inputProcessorsSupport;
	
	/** The gesture evt support. */
	private GestureEventSupport gestureEvtSupport;
	
	/** The allowed gestures. */
	private ArrayList<Class<? extends IInputProcessor>> allowedGestures;
	
	private BufferedImage overlay;
	private Graphics2D graphics;
	

	public AbstractMTLayer(AbstractMTApplication app) {
		this.app = app;
		inputManager = app.getInputManager();
		allowedGestures = new ArrayList<Class<? extends IInputProcessor>>(5);

		this.inputListeners = new ArrayList<IMTInputEventListener>(3);

		this.inputProcessorsSupport = new ComponentInputProcessorSupport(app, this);
		this.addInputListener(inputProcessorsSupport);

		this.gestureEvtSupport = new GestureEventSupport();
		initGraphics(1, 1);
	}
	
	public void initGraphics(int x, int y) {
		overlay = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		graphics = overlay.createGraphics();
	}
	
	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		g.drawImage(overlay, 0, 0, null);
		Dimension currentSize = c.getSize();
		if (!currentSize.equals(new Dimension(overlay.getWidth(), overlay.getHeight()))) {
			initGraphics(currentSize.width, currentSize.height);
		} else {
			graphics.setColor(new Color(0, 0, 0, 0));
			graphics.fillRect(0, 0, (int)currentSize.width, (int)currentSize.height);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		layer = (JLayer<T>)c;
		app.addLayer(this);
	}

	@Override
	public void uninstallUI(JComponent c) {
		// TODO Auto-generated method stub
		super.uninstallUI(c);
		layer = null;
		app.removeLayer(this);
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public void setInputManager(InputManager inputManager) {
		this.inputManager = inputManager;
	}
	
	public JLayer<T> getLayer() {
		return layer;
	}
	
	public Dimension getSize() {
		return layer.getSize();
	}
	
	public boolean isInLayer(Point pt) {
		Point p = (Point) pt.clone();
		return convertToLayer(p);
	}
	
	public boolean convertToLayer(Point pt) {
		SwingUtilities.convertPointFromScreen(pt, getLayer());
		return contains(getLayer(), pt.x, pt.y);
	}
	// INPUT LISTENER STUF ////
	/**
	 * Adds an input listener to this component. The listener will be informed if
	 * this component recieves an input event.
	 * 
	 * @param inputListener the input listener
	 */
	public synchronized void addInputListener(IMTInputEventListener inputListener){
		if (inputListener instanceof AbstractComponentProcessor) {
			logger.warn("An abstract component processor (" + inputListener + ") was added to component '" + this + "' using addInputListener(). You probably need to use the registerInputProcessor() method instead!");
		}
		this.inputListeners.add(inputListener);
	}
	
	/**
	 * Removes the input listener.
	 * @param inputListener the input listener
	 */
	public synchronized void removeInputListener(IMTInputEventListener inputListener){
		this.inputListeners.remove(inputListener);
	}
	
	/**
	 * Gets the input listeners.
	 * @return the input listeners
	 */
	public IMTInputEventListener[] getInputListeners(){
		return this.inputListeners.toArray(new IMTInputEventListener[this.inputListeners.size()]);
	}
	
	/**
	 * Fire input event.
	 * 
	 * @param iEvt the i evt
	 */
	protected boolean dispatchInputEvent(MTInputEvent iEvt){
		boolean handled = false; //TODO REALLY IMPLEMENT, CHECK LISTENERS WHAT THEY RETURN, PROPAGET ETC!
		for (IMTInputEventListener listener : inputListeners){
			boolean handledListener = listener.processInputEvent(iEvt);
			if (!handled && handledListener){
				handled = true;
			}
		}
		return handled;
	}
	// INPUT LISTENER STUF ////
	
	
	// INPUT HANDLER ////////////////////////////////////////
	/**
	 * Registers an component input processor with this component. Input processors are used to process
	 * the input events a component recieves by checking them for special patterns and conditions and
	 * firing gesture events back to the component.
	 * To recognize a multi-touch drag gesture on a component for example, we would register a
	 * <code>DragProcessor</code> object with this component.
	 * 
	 * @param inputProcessor the input processor
	 */
	public void registerInputProcessor(AbstractComponentProcessor inputProcessor) {
		AbstractComponentProcessor[] processors = inputProcessorsSupport.getInputProcessors();
        for (AbstractComponentProcessor abstractComponentProcessor : processors) {
            if (inputProcessor.getClass() == abstractComponentProcessor.getClass()) {
                logger.warn("Warning: The same type of input processor (" + inputProcessor.getName() + ") is already registered at component: " + this);
            }
        }
		inputProcessorsSupport.registerInputProcessor(inputProcessor);
		this.setGestureAllowance(inputProcessor.getClass(), true); //Enable by default
	}
	
	/**
	 * Unregister a component input processor.
	 * @param inputProcessor the input processor
	 */
	public void unregisterInputProcessor(AbstractComponentProcessor inputProcessor) {
		inputProcessorsSupport.unregisterInputProcessor(inputProcessor);
	}
	
	/**
	 * Unregister all previously registered component input processors.
	 */
	public void unregisterAllInputProcessors() {
		AbstractComponentProcessor[] ps = inputProcessorsSupport.getInputProcessors();
        for (AbstractComponentProcessor p : ps) {
            inputProcessorsSupport.unregisterInputProcessor(p);
        }
	}
	
	/**
	 * Gets the component input processors.
	 * @return the input processors
	 */
	public AbstractComponentProcessor[] getInputProcessors() {
		return inputProcessorsSupport.getInputProcessors();
	}
	// INPUT HANDLER ////////////////////////////////////////

	
	// GESTURE LISTENER EVENT SUPPORT ///////////////////////////////////////
	/**
	 * Adds a gesture listener to this component. The specified gesture listener's 
	 * <code>processGestureEvent(..)</code> method will be called when a gesture event 
	 * is processed by this component. The <code>IInputProcessor</code> paramter type specifies the source of
	 * the gesture event we are interested in. So to listen to drag events only for example, we would specify
	 * the <code>DragProcessor.class</code> as the first parameter.
	 * 
	 * @param gestureEvtSender the gesture evt sender
	 * @param listener the listener
	 */
	public void addGestureListener(Class<? extends IInputProcessor> gestureEvtSender, IGestureEventListener listener){
		this.gestureEvtSupport.addGestureEvtListener(gestureEvtSender, listener);
	}
	
	/**
	 * Removes the gesture event listener.
	 * @param gestureEvtSender the gesture evt sender
	 * @param listener the listener
	 */
	public void removeGestureEventListener(Class<? extends IInputProcessor> gestureEvtSender, IGestureEventListener listener) {
		gestureEvtSupport.removeGestureEventListener(gestureEvtSender, listener);
	}
	
	/**
	 * Removes the all gesture event listeners.
	 */
	public void removeAllGestureEventListeners() {
		this.gestureEvtSupport.clearListeners();
	}
	
	/**
	 * Removes the all gesture event listeners who listen to the specified input processor.
	 * @param gestureEvtSender the gesture evt sender
	 */
	public void removeAllGestureEventListeners(Class<? extends IInputProcessor> gestureEvtSender) {
		IGestureEventListener[] l = this.getGestureListeners();
        for (IGestureEventListener gestureEventListener : l) {
            this.removeGestureEventListener(gestureEvtSender, gestureEventListener);
        }
	}
	
	/**
	 * Returns the gesture listeners.
	 * @return the gesture listeners
	 */
	public final IGestureEventListener[] getGestureListeners() {
		return gestureEvtSupport.getListeners();
	}
	// GESTURE LISTENER EVENT SUPPORT ///////////////////////////////////////


	
	
	//@Override
	/* (non-Javadoc)
	 * @see org.mt4j.components.interfaces.IMTComponent#processInputEvent(org.mt4j.input.inputData.MTInputEvent)
	 */
	public boolean processInputEvent(MTInputEvent inEvt) {
		if (inEvt.getEventPhase() != MTInputEvent.BUBBLING_PHASE && inEvt.getTarget().equals(this) /*&& inEvt.bubbles()*/){
			inEvt.setEventPhase(MTInputEvent.AT_TARGET);
		}
		
		if (this.getLayer().isEnabled()){
//			System.out.println("Comp: " + this.getName() + " Evt: " + inEvt);
			//TODO do only if not handled maybe?
			//THIS IS A HACK TO ALLOW Global GESTURE PROCESSORS to send MTGEstureevents TO WORK
			if (inEvt instanceof MTGestureEvent){  
				this.processGestureEvent((MTGestureEvent)inEvt);
			}else{
				//Fire the same input event to all of this components' input listeners
				this.dispatchInputEvent(inEvt);
			}
		}
		
		
//		if (inEvt.getBubbles() && !inEvt.isPropagationStopped() && inEvt.getEventPhase() == MTInputEvent.AT_TARGET){
//			inEvt.setEventPhase(MTInputEvent.BUBBLING_PHASE);	
//		}
//
//		if (inEvt.getBubbles() && !inEvt.isPropagationStopped() && inEvt.getEventPhase() == MTInputEvent.BUBBLING_PHASE){
//			MTComponent theParent = this.getParent();
//			if (theParent != null){
//				inEvt.setCurrentTarget(theParent);
//				theParent.processInputEvent(inEvt);
//				//TODO (register interest in cursors -> done automatically?)
//				//TODO use getCurrentTarget in all inputProcessors, at least at sending the event - also at canvas processors?
//				//TODO (check if currentTarget = this component in processorsupport? -> not really needed, make optional?)
//				//TODO remove default input processors form AbstractShape! -> make helper instead -> also remove from SVGs
//				//TODO in inputprocessors always intersect current target -> is the actual target also chcked then? -> to get composite effect..
//				//TODO in inputprocessors get intersection points using the cursors getTarget() -> because one cursors target may be different than the other cursor now 
//				//TODO if no input processor is registered all events are bubbled up - prevent that by default? - how?
//				//TODO also allow bubbling of MTGestureEvents?
//				//TODO in inputprocessors intersect with target and if no hit- with currenttarget?
//				
//				//FIXME if cursor is unlocked in dispatchInputEvent(), unlocked() may be called in an I.P. up the tree, but currentTarget is still the same as target!
//				
//				//TODO send locked() signal only to those who currently lock the cursor (not all lower priority ones)
//				
//				//FIXME use headMap -> inclusive = false? so it doesent send cursorLocked to some with same priority in cursorLockedByHigherPriorityGesture
//				
//				//TODO why only rotate processor registered at Keyboard when switching to rot/scale at parent?
//				
//				//TODO priorities-> float
//				//TODO tap prior > drag prior -> really default that?
//				//TODO (abort tap if moved too much -> start drag/scale)
//			}
//		}
		
		return false;
	}

	
	/**
	 * Processes gesture events.<br>
	 * Fires the specified gesture event to the attached IGestureEventListers of this component.
	 * 
	 * @param gestureEvent the gesture event
	 * @return true, if successful
	 */
	@Override
	public boolean processGestureEvent(MTGestureEvent gestureEvent){
		this.gestureEvtSupport.fireGestureEvt(gestureEvent);
		System.out.println("processGestureEvent: gestureEvent source: " + gestureEvent.getSource() +" ID: " +  gestureEvent.getId());
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.mt4j.components.interfaces.IMTComponent#isGestureAllowed(java.lang.Class)
	 */
	public boolean isGestureAllowed(Class<? extends IInputProcessor> c){
		return this.allowedGestures.contains(c);
	}
	
	/**
	 * Sets the gesture allowance. 
	 * @param c the gesture processors class
	 * @param allowed allowance
	 */
	public void setGestureAllowance(Class<? extends IInputProcessor> c, boolean allowed){
		if (allowed){
			if (!this.allowedGestures.contains(c)){
				this.allowedGestures.add(c);
			}
		}else{
			if (this.allowedGestures.contains(c)){
				this.allowedGestures.remove(c);
			}
		}
	}

	public Graphics2D getGraphics() {
		return graphics;
	}


}
