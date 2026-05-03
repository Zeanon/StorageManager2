package de.storagemanager.core.interfaces;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

/**
 * Base interface for FileType implementations, providing different methods to be implemented
 *
 * @author YoyoNow
 */
public interface FileType {
    boolean isType(final @NonNull Path path);

    default boolean isType(final @NonNull File file) {
        return isType(file.toPath());
    }

    @NotNull Path appendExtension(final @NonNull Path path);

    default @NotNull File appendExtension(final @NonNull File file) {
        return appendExtension(file.toPath()).toFile();
    }

    @NotNull Path removeExtension(final @NonNull Path path);

    default @NotNull File removeExtension(final @NonNull File file) {
        return removeExtension(file.toPath()).toFile();
    }
}
