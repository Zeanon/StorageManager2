package de.storagemanager.core.flat;

import de.storagemanager.core.exceptions.FileTypeException;
import de.storagemanager.core.flat.value.StringValue;
import de.storagemanager.core.interfaces.FileType;
import de.storagemanager.core.interfaces.ReloadSetting;
import de.storagemanager.core.interfaces.WriteSetting;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

@Getter
public abstract class FlatFile extends FlatSection implements Comparable<FlatFile> {

    @Delegate(types = {FileDelegate.class})
    private final @NotNull File file;
    private final @NotNull FileType fileType;

    @Setter(AccessLevel.PROTECTED)
    protected volatile long lastLoaded;

    @Setter(onMethod_ = @NonNull)
    @Accessors(fluent = true, chain = true)
    private @NotNull ReloadSetting reloadSetting;

    @Setter(onMethod_ = @NonNull)
    @Accessors(fluent = true, chain = true)
    private @NotNull WriteSetting writeSetting;

    protected FlatFile(final @NonNull File file,
                       final @NonNull FileType fileType,
                       final @NonNull ReloadSetting reloadSetting,
                       final @NonNull WriteSetting writeSetting) {
        if (!fileType.isType(file)) {
            throw new FileTypeException(file, fileType);
        }

        this.file = file;
        this.fileType = fileType;
        this.reloadSetting = reloadSetting;
        this.writeSetting = writeSetting;

        if (writeSetting.shouldWrite(this)) {
            // TODO: Write the file based from the InputStream!
        }
    }

    public boolean hasChanged() {
        return lastLoaded < file.lastModified();
    }

    public void tryReload() {
        if (reloadSetting.shouldReload(this)) {
            reload();
        }
    }

    public void reload() {
        // TODO: Implement
        lastLoaded = System.currentTimeMillis();
    }

    public void tryWrite() {
        if (writeSetting.shouldWrite(this)) {
            write();
        }
    }

    public abstract void write();

    public void clear() {
    }

    @Override
    public int compareTo(final @NotNull FlatFile o) {
        return file.compareTo(o.file);
    }

    private interface FileDelegate {
        String getName();
        Path toPath();
        boolean exists();
        boolean isHidden();
        long lastModified();
        long length();
        boolean delete();
        void deleteOnExit();
    }
}
