package de.storagemanager.core.interfaces;

import de.storagemanager.core.files.FlatFile;
import lombok.NonNull;

/**
 * Base interface for WriteSettings, provides a method to check whether to write with the given setting
 *
 * @author YoyoNow
 */
@SuppressWarnings({"EmptyMethod", "rawtypes"})
public interface WriteSetting {

    /**
     * Check whether the given FlatFile should be reloaded
     *
     * @param flatFile the FlatFile to check
     */
    boolean shouldWrite(final @NonNull FlatFile flatFile);
}
