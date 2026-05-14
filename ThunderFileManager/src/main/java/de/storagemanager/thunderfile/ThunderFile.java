package de.storagemanager.thunderfile;

import de.storagemanager.core.interfaces.FileType;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class ThunderFile {

    public static final FileType FileType = new FileType() {
        @Override
        public boolean isType(@NonNull Path path) {
            return false;
        }

        @Override
        public @NotNull Path appendExtension(@NonNull Path path) {
            return null;
        }

        @Override
        public @NotNull Path removeExtension(@NonNull Path path) {
            return null;
        }
    };
}
