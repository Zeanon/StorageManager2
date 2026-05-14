package de.storagemanager.core.flat;

import de.storagemanager.core.flat.data.Comment;
import de.storagemanager.core.flat.data.NewLine;
import de.storagemanager.core.flat.data.StringEntry;
import de.storagemanager.core.flat.value.Value;
import de.storagemanager.core.flat.value.StringValue;

import java.util.List;
import java.util.Optional;

public abstract class FlatSection implements Value {

    private List<Type> list;

    public Type get(StringValue key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public interface Type extends Comment, NewLine, StringEntry {
        default <T extends Type> boolean is(Class<T> clazz) {
            return clazz.isInstance(this);
        }

        default <T extends Type> Optional<T> as(Class<T> clazz) {
            return is(clazz) ? Optional.of(clazz.cast(this)) : Optional.empty();
        }
    }
}
