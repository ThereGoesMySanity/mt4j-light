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
package org.mt4j.input.inputProcessors.globalProcessors;


import java.util.ArrayList;

import org.mt4j.input.IMTInputEventListener;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.IInputProcessor;
import org.mt4j.input.inputSources.IinputSourceListener;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;


/**
 * The Class AbstractInputprocessor.
 * 
 * @author Christopher Ruff
 */
public abstract class AbstractGlobalInputProcessor implements IinputSourceListener, IInputProcessor {
	protected static final ILogger logger = MTLoggerFactory.getLogger(AbstractGlobalInputProcessor.class.getName());
	static{
		logger.setLevel(ILogger.ERROR);
	}
	
	/** if disabled. */
	private boolean disabled;
	
	private ArrayList<IMTInputEventListener> inputListeners;
	
	/**
	 * Instantiates a new abstract input processor.
	 */
	public AbstractGlobalInputProcessor() {
		this.disabled 	= false;
		inputListeners = new ArrayList<IMTInputEventListener>();
	} 

	
	
	/* (non-Javadoc)
	 * @see org.mt4j.input.inputSources.IinputSourceListener#processInputEvent(org.mt4j.input.test.MTInputEvent)
	 */
	@Override
	final public boolean processInputEvent(MTInputEvent inputEvent){
		this.processInputEvtImpl(inputEvent);
		return true;
	}

	
	/**
	 * Process input evt implementation.
	 * 
	 * @param inputEvent the input event
	 */
	abstract public void processInputEvtImpl(MTInputEvent inputEvent);
	
	
	/**
	 * Checks if is disabled.
	 * 
	 * @return true, if is disabled
	 */
	@Override
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * Sets the disabled.
	 * 
	 * @param disabled the new disabled
	 */
	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}


	/**
	 * Adds the processor listener.
	 * 
	 * @param listener the listener
	 */
	public synchronized void addProcessorListener(IMTInputEventListener listener){
		if (!inputListeners.contains(listener)){
			inputListeners.add(listener);
		}
		
	}
	
	/**
	 * Removes the processor listener.
	 * 
	 * @param listener the listener
	 */
	public synchronized void removeProcessorListener(IMTInputEventListener listener){
		if (inputListeners.contains(listener)){
			inputListeners.remove(listener);
		}
	}
	
	/**
	 * Gets the processor listeners.
	 * 
	 * @return the processor listeners
	 */
	public synchronized IMTInputEventListener[] getProcessorListeners(){
		return inputListeners.toArray(new IMTInputEventListener[this.inputListeners.size()]);
	}
	
	/**
	 * Fire gesture event.
	 *
	 * @param ie the ie
	 */
	protected void fireInputEvent(MTInputEvent ie) {
		for (IMTInputEventListener listener : inputListeners){
			listener.processInputEvent(ie);
		}
	}
}
