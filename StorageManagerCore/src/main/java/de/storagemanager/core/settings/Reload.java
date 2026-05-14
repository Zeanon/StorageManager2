package de.storagemanager.core.settings;

import de.storagemanager.core.flat.FlatFile;
import de.storagemanager.core.interfaces.ReloadSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enum defining the reload behaviour of the Data classes
 *
 * @author Zeanon
 */
@SuppressWarnings({"unused", "rawtypes"})
public enum Reload implements ReloadSetting {

	/**
	 * Reloads every time you try to get something from the config
	 */
	AUTOMATICALLY {
		@Override
		public boolean shouldReload(final @Nullable FlatFile flatFile) {
			return true;
		}
	},

	/**
	 * Reloads only if the File has changed
	 */
	INTELLIGENT {
		@Override
		public boolean shouldReload(final @NotNull FlatFile flatFile) {
			return flatFile.hasChanged();
		}
	},

	/**
	 * Only reloads if you manually call the reload
	 */
	MANUALLY {
		@Override
		public boolean shouldReload(final @Nullable FlatFile flatFile) {
			return false;
		}
	},
    ;
}