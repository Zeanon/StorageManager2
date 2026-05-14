package de.storagemanager.thunderfile;

import de.storagemanager.core.exceptions.FileParseException;
import de.storagemanager.core.filelock.ExtendedFileLock;
import de.storagemanager.core.interfaces.*;
import de.storagemanager.core.settings.Comment;
import de.storagemanager.core.utils.Pair;
import lombok.NonNull;
import lombok.experimental.StandardException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class Parser {

    public @NotNull DataMap<String, Object> readData(final @NotNull File file,
                                                     final @NotNull CollectionsProvider<DataMap, List> collectionsProvider,
                                                     final @NotNull CommentSetting commentSetting,
                                                     final int buffer_size) throws ThunderParseException {
        try {
            final @NonNull ListIterator<String> lines;
            try (final @NonNull ReadWriteFileLock tempLock = new ExtendedFileLock(file, true, false).readLock();
                 final @NonNull BufferedReader reader = tempLock.createBufferedReader(buffer_size)) {
                tempLock.lock();
                lines = reader.lines().toList().listIterator();
            } catch (final @NotNull IOException e) {
                throw new UncheckedIOException("Error while reading content from '" + file.getAbsolutePath() + "'", e);
            }
            if (commentSetting == Comment.PRESERVE) {
                return this.readWithComments(lines, collectionsProvider);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private @NotNull DataMap<String, Object> readWithComments(final @NotNull ListIterator<String> lines,
                                                                     final @NotNull CollectionsProvider<? extends DataMap, ? extends List> collectionsProvider) throws ThunderParseException {
        try {
            final @NonNull Stack<DataMap<String, Object>> maps = new Stack<>();
            //noinspection unchecked
            maps.push(collectionsProvider.newMap());

            @NotNull String tempLine;
            @Nullable String tempKey = null;
            while (lines.hasNext()) {
                tempLine = lines.next().trim();

                if (tempLine.contains("}")) {
                    if (maps.size() <= 1) {
                        throw new ThunderParseException("Syntax Error at line '" + lines.previousIndex() + "' -> Block closed without being opened");
                    }
                    maps.peek().trimToSize();
                    maps.pop();
                } else if (tempLine.isEmpty()) {
                    maps.peek().add(tempLine, LineType.BLANK_LINE);
                } else if (tempLine.startsWith("#")) {
                    maps.peek().add(tempLine, LineType.COMMENT);
                } else if (tempLine.endsWith("{")) {
                    if (!tempLine.equals("{")) {
                        tempKey = tempLine.substring(0, tempLine.length() - 1).trim();
                    } else if (tempKey == null) {
                        throw new ThunderParseException("'" + tempLine + "' (line: " + lines.previousIndex() + ") -> Key must not be null");
                    }
                    //noinspection unchecked
                    DataMap<String, Object> newMap = collectionsProvider.newMap();
                    maps.peek().add(tempKey, newMap);
                    maps.push(newMap);
                } else {
                    this.readKey(lines, maps.peek(), tempLine, collectionsProvider);
                    tempKey = null;
                }
            }
            maps.peek().trimToSize();
            return maps.peek();
        } catch (final @NotNull IndexOutOfBoundsException e) {
            throw new ThunderParseException("Could not parse content", e);
        }
    }

    @SuppressWarnings("rawtypes")
    private void readKey(final @NotNull ListIterator<String> lines,
                         final @NotNull DataMap<String, Object> tempMap,
                         final @NotNull String tempLine,
                         final @NotNull CollectionsProvider<? extends DataMap, ? extends List> collectionsProvider) throws ThunderParseException {
        if (tempLine.contains("=")) {
            final @NotNull String[] line = tempLine.split("=", 2);
            line[0] = Parser.trimString(line[0]);
            line[1] = Parser.trimString(line[1]);

            if (line[1].startsWith("[")) {
                if (line[1].endsWith("]")) {
                    if (line[1].contains(":")) {
                        final @NotNull String[] pair = line[1].substring(1, line[1].length() - 1).split(":");
                        if (pair.length > 2) {
                            throw new ThunderParseException("'" + tempLine + "' (line: " + lines.previousIndex() + ") ->  Illegal Object(Pairs may only have two values");
                        } else if (pair.length < 2) {
                            throw new ThunderParseException("'" + tempLine + "' (line: " + lines.previousIndex() + ") ->  Illegal Object(Pairs need two values");
                        } else {
                            tempMap.add(line[0], new Pair<>(Parser.trimString(pair[0]), Parser.trimString(pair[1])));
                        }
                    } else {
                        final @NotNull String[] listArray = line[1].substring(1, line[1].length() - 1).split(",");
                        //noinspection unchecked
                        final @NotNull List<String> list = collectionsProvider.newList();
                        for (final @NotNull String value : listArray) {
                            list.add(Parser.trimString(value));
                        }
                        tempMap.add(line[0], list);
                    }
                } else {
                    tempMap.add(line[0], Parser.readList(lines, collectionsProvider));
                }
            } else {
                tempMap.add(line[0], line[1]);
            }
        } else {
            throw new ThunderParseException("'" + tempLine + "' (line: " + lines.previousIndex() + ") -> Line does not contain value or subblock");
        }
    }

    @SuppressWarnings("rawtypes")
    private static @NotNull List<String> readList(final @NotNull ListIterator<String> lines,
                                                  final @NotNull CollectionsProvider<? extends DataMap, ? extends List> collectionsProvider) throws ThunderParseException {
        @NotNull String tempLine;
        @NotNull String tempValue;
        //noinspection unchecked
        final @NotNull List<String> tempList = collectionsProvider.newList();
        while (lines.hasNext()) {
            tempLine = lines.next().trim();
            if (tempLine.startsWith("-")) {
                if (tempLine.endsWith("]")) {
                    tempList.add(Parser.trimString(tempLine.substring(1, tempLine.length() - 1)));
                    return tempList;
                } else {
                    tempList.add(Parser.trimString(tempLine.substring(1)));
                }
            } else if (tempLine.endsWith("]")) {
                return tempList;
            } else {
                throw new ThunderParseException("Syntax Error at '" + tempLine + "' (line: " + lines.previousIndex() + ") -> missing '-'");
            }
        }
        throw new ThunderParseException("Syntax Error at line '" + lines.previousIndex() + "' -> List not closed properly");
    }

    private static String trimString(final @NotNull String string) {
        @NotNull String tempString = string.trim();
        if ((tempString.startsWith("\"") || tempString.startsWith("'")) && (tempString.endsWith("\"") || tempString.endsWith("'"))) {
            tempString = tempString.substring(1, tempString.length() - 1);
        }
        return tempString;
    }


    public enum LineType {
        VALUE,
        COMMENT,
        BLANK_LINE
    }

    @StandardException
    public static class ThunderParseException extends FileParseException {
    }
}
