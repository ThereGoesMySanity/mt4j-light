package org.mt4j;

import org.mt4j.input.InputManager;

public interface IMTApplication {
	public InputManager getInputManager();

	/**
	 * Sets the input manager.
	 * 
	 * @param inputManager the new input manager
	 */
	public void setInputManager(InputManager inputManager);

	/**
	 * Registers an action to be processed before the next frame
	 * in the main drawing thread.
	 * 
	 * @param action the action
	 */
	public void registerPreDrawAction(final IPreDrawAction action);

	/**
	 * Unregisters an PreDrawAction.
	 * 
	 * @param action the action
	 */
	public void unregisterPreDrawAction(final IPreDrawAction action);
	
	/**
	 * Invokes the specified runnable at the beginning the next rendering loop in the rendering thread.
	 * This is especially useful for executing opengl commands from another thread - which would lead to errors
	 * if not synchronized with the rendering thread.
	 * 
	 * @param runnable the runnable
	 */
	public void invokeLater(Runnable runnable);
}