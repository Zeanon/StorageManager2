package de.storagemanager.core.exceptions;

import de.storagemanager.core.interfaces.FileType;

import java.io.File;

public final class FileTypeException extends RuntimeException {

    public FileTypeException(File file, FileType fileType) {
        super("File '" + file.getAbsolutePath() + "' is not of type '" + fileType + "'.");
    }
}
