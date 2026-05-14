package de.storagemanager.core.flat;

import de.storagemanager.core.flat.data.Comment;
import de.storagemanager.core.flat.data.NewLine;
import de.storagemanager.core.flat.data.SingleEntry;
import de.storagemanager.core.flat.value.Value;

import java.util.List;
import java.util.Optional;

public abstract class FlatList implements Value {

    private List<Type> list;

    public Type get(int key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public interface Type extends Comment, NewLine, SingleEntry {
        default <T extends Type> boolean is(Class<T> clazz) {
            return clazz.isInstance(this);
        }

        default <T extends Type> Optional<T> as(Class<T> clazz) {
            return is(clazz) ? Optional.of(clazz.cast(this)) : Optional.empty();
        }
    }
}
