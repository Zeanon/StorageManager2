package de.storagemanager.core.settings;

import de.storagemanager.core.files.FlatFile;
import de.storagemanager.core.interfaces.WriteSetting;
import org.jetbrains.annotations.Nullable;

public enum Write implements WriteSetting {

    /**
     * Always write the changes to the file.
     */
    ALWAYS {
        @Override
        public boolean shouldWrite(final @Nullable FlatFile flatFile) {
            return true;
        }
    },

    /**
     * Never writes the changes to file. If the file is reloaded these changes are lost.
     */
    NEVER {
        @Override
        public boolean shouldWrite(final @Nullable FlatFile flatFile) {
            return false;
        }
    },
    ;
}
