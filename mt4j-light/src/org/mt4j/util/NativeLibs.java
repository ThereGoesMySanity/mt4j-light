package org.mt4j.util;

import java.io.File;
import java.io.IOException;

public class NativeLibs {
    //from JNA
    static boolean deleteLibrary(File lib) {
        if (lib.delete()) {
            return true;
        }

        // Couldn't delete it, mark for later deletion
        //markTemporaryFile(lib);

        return false;
    }
    public static void loadNativeLibrary(String libName) {
        try {
            String mappedName = "/" + System.mapLibraryName(libName);
            File lib = com.sun.jna.Native.extractFromResourcePath(mappedName, NativeLibs.class.getClassLoader());
            if (lib == null) {
                throw new UnsatisfiedLinkError("Could not find native lib " + libName);
            }
            System.load(lib.getAbsolutePath());
            // Attempt to delete immediately once successfully
            // loaded.  This avoids the complexity of trying to do so on "exit",
            // which point can vary under different circumstances (native
            // compilation, dynamically loaded modules, normal application, etc).
            //deleteLibrary(lib);
        }
        catch(IOException e) {
            throw new UnsatisfiedLinkError(e.getMessage());
        }
    }
}
