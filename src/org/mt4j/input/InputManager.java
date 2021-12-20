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
package org.mt4j.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mt4j.AbstractMTApplication;
import org.mt4j.AbstractMTLayer;
import org.mt4j.input.inputData.AbstractCursorInputEvt;
import org.mt4j.input.inputData.MTInputEvent;
import org.mt4j.input.inputProcessors.globalProcessors.AbstractGlobalInputProcessor;
import org.mt4j.input.inputSources.AbstractInputSource;
import org.mt4j.input.inputSources.IinputSourceListener;
import org.mt4j.util.logging.ILogger;
import org.mt4j.util.logging.MTLoggerFactory;



/**
 * Manages the InputSources and Inputprocessors for each scene.
 * Starts up the default input sources.
 * 
 * @author Christopher Ruff
 */
public class InputManager implements IinputSourceListener {
	/** The Constant logger. */
	protected static final ILogger logger = MTLoggerFactory.getLogger(InputManager.class.getName());
	static{
//		logger.setLevel(ILogger.ERROR);
//		logger.setLevel(ILogger.DEBUG);
		logger.setLevel(ILogger.INFO);
	}
	
	/** The registered input sources. */
	private List<AbstractInputSource> registeredInputSources;
	
	private List<AbstractGlobalInputProcessor> inputProcessors;
	
	/**
	 * Instantiates a new input manager.
	 * 
	 * @param pa the processing context
	 */
	public InputManager(AbstractMTApplication app) {
		this(app, true);
	}
	
	
	/**
	 * Instantiates a new input manager.
	 * 
	 * @param pa the processing context
	 */
	public InputManager(AbstractMTApplication app, boolean registerDefaultSources) {
		this.registeredInputSources	= new ArrayList<AbstractInputSource>();
		this.inputProcessors = new ArrayList<AbstractGlobalInputProcessor>();
		
		if (registerDefaultSources)
			this.registerDefaultInputSources(app);
	}
	
	
	/**
	 * Initialize default input sources.
	 */
	protected void registerDefaultInputSources(AbstractMTApplication app){	}
	
	
	/**
	 * Registers a new input source for the application.
	 * 
	 * @param newInputSource the new input source
	 */
	public void registerInputSource(AbstractInputSource newInputSource){
		if (!registeredInputSources.contains(newInputSource)){
			registeredInputSources.add(newInputSource);
			newInputSource.addInputListener(this);
			//Add all processors to the new input source
            for (AbstractGlobalInputProcessor processor : inputProcessors) {
                //newInputSource.addInputListener(processor);
                this.saveAddInputListenerToSource(newInputSource, processor);
            }
			
			//Inform the input source that it is now registered with the application
			newInputSource.onRegistered();
		}else{
			logger.error("input source already registered! - " + newInputSource);
		}
	}
	
	
	/**
	 * Unregisters a input source.
	 * @param is the input source
	 */
	public void unregisterInputSource(AbstractInputSource is){
		synchronized (registeredInputSources) {
			if (registeredInputSources.contains(is)){
				registeredInputSources.remove(is);
				
				//Inform the input source that it is now UN-registered from the application
				is.onUnregistered();
			}
		}
	}
	
	/**
	 * Gets the input sources.
	 * @return the input sources
	 */
	public AbstractInputSource[] getInputSources(){
		return this.registeredInputSources.toArray(new AbstractInputSource[this.registeredInputSources.size()]);
	}
	
	public void addLayer(AbstractMTLayer<?> layer) {
		
	}
	
	public void removeLayer(AbstractMTLayer<?> layer) {
		
	}
	
	/**
	 * Registers a new inputprocessor and adds it to the inputsources as listeners.
	 * 
	 * @param scene the scene
	 * @param inputprocessor the input processor
	 */
	public void registerGlobalInputProcessor(AbstractGlobalInputProcessor inputprocessor){
		inputprocessor.setDisabled(false);

		inputProcessors.add(inputprocessor);
		//Register the processor with all registered inputsources
		for (AbstractInputSource source: registeredInputSources){
			this.saveAddInputListenerToSource(source, inputprocessor);
		}
	}
	
	
	private void saveAddInputListenerToSource(AbstractInputSource source, AbstractGlobalInputProcessor inputprocessor){
		//Only add input processor to input sources 
		//that fire the event type that the processor is interested in
//		if (source.firesEventType(inputprocessor.getListenEventType())){
		
			List<IinputSourceListener> sourceListener = Arrays.asList(source.getInputListeners());
			if (!sourceListener.contains(inputprocessor)){ //Prevent adding same global input processor twice
				source.addInputListener(inputprocessor);
			}
//		}
	}
	
	/**
	 * Unregisters a inputprocessor from _all_ the registered inputsources.
	 * 
	 * @param inputprocessor the input processor
	 */
	public void unregisterGlobalInputProcessor(AbstractGlobalInputProcessor inputprocessor){
		//Remove the input processor
		inputProcessors.remove(inputprocessor);	
		
		for (AbstractInputSource source: registeredInputSources){
			source.removeInputListener(inputprocessor);
		}
	}
	
	
	/**
	 * Gets the global inputprocessors associated with the specified scene.
	 * 
	 * @param scene the scene
	 * 
	 * @return the scene inputprocessors
	 */
	public AbstractGlobalInputProcessor[] getGlobalInputProcessors(){
		return inputProcessors.toArray(new AbstractGlobalInputProcessor[inputProcessors.size()]);
	}
	
	/**
	 * Enables the global inputprocessors
	 * 
	 * @param scene the scene
	 */
	public void enableGlobalInputProcessors(){
        for (AbstractGlobalInputProcessor processor : inputProcessors) {
        	processor.setDisabled(false);
        }
	}
	
	/**
	 * Disables the global inputprocessors
	 * 
	 * @param scene the scene
	 */
	public void disableGlobalInputProcessors(){
        for (AbstractGlobalInputProcessor processor : inputProcessors) {
        	processor.setDisabled(true);
        }
	}
	
	
	/**
	 * Removes input processors from listening to the registered input sources.
	 * 
	 * @param scene the scene
	 */
	public void removeGlobalInputProcessors(){
        for (AbstractGlobalInputProcessor abstractGlobalInputProcessor : this.getGlobalInputProcessors()) {
            this.unregisterGlobalInputProcessor(abstractGlobalInputProcessor);
        }
	}


	@Override
	public boolean processInputEvent(MTInputEvent inputEvent) {
		if (inputEvent instanceof AbstractCursorInputEvt) {
			AbstractCursorInputEvt e = (AbstractCursorInputEvt)inputEvent;
			e.getTarget().processInputEvent(inputEvent);
		} else {
			//TODO: app handles it?
		}
		return false;
	}


	@Override
	public boolean isDisabled() {
		return false;
	}
}
