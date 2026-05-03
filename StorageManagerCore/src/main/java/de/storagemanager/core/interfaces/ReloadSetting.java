package de.storagemanager.core.interfaces;

import de.storagemanager.core.files.FlatFile;
import lombok.NonNull;


/**
 * Base interface for ReloadSettings, provides a method to check whether to reload with the given setting
 *
 * @author Zeanon
 */
@SuppressWarnings({"EmptyMethod", "rawtypes"})
public interface ReloadSetting {

	/**
	 * Check whether the given FlatFile should be reloaded
	 *
	 * @param flatFile the FlatFile to check
	 */
    boolean shouldReload(final @NonNull FlatFile flatFile);
}