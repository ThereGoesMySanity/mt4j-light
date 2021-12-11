package org.mt4j.input;

import org.mt4j.AbstractMTApplication;
import org.mt4j.input.inputSources.LinuxMultiTouchSource;
import org.mt4j.input.inputSources.Win7NativeTouchSource;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.VersionHelpers;

/**
 * The Class DesktopInputManager.
 */
public class DesktopInputManager extends InputManager{
	/**
	 * Instantiates a new desktop input manager.
	 * @param mtApplication 
	 *
	 * @param app the app
	 */
	public DesktopInputManager(AbstractMTApplication mtApplication) {
		this(mtApplication, true);
	}
	
	/**
	 * Instantiates a new desktop input manager.
	 *
	 * @param app the app
	 * @param registerDefaultSources the register default sources
	 */
	public DesktopInputManager(AbstractMTApplication mtApplication, boolean registerDefaultSources) {
		super(mtApplication, registerDefaultSources);
	}

	/* (non-Javadoc)
	 * @see org.mt4j.input.InputManager#registerDefaultInputSources()
	 */
	@Override
	/**
	 * Initialize default input sources.
	 */
	protected void registerDefaultInputSources(AbstractMTApplication app){
		super.registerDefaultInputSources(app);

//		MouseInputSource mouseInput = new MouseInputSource(app);
//		this.registerInputSource(mouseInput);

	    //Check if we run windows 7
		
	    if (Platform.isWindows() && VersionHelpers.IsWindows7OrGreater()) {
	    	Win7NativeTouchSource win7NativeInput = new Win7NativeTouchSource(app);
	    	if (win7NativeInput.isSuccessfullySetup()){
	    		this.registerInputSource(win7NativeInput);
	    	}
	    }
	    
	    //check which versions it supports and only start there!
	    /*
	    if (System.getProperty("os.name").toLowerCase().contains("mac os x")){
	    	this.registerInputSource(new MacTrackpadSource(app));
	    }
	    */

	    if (Platform.isLinux()){
	    	this.registerInputSource(new LinuxMultiTouchSource(app));
	    }
	}
}
