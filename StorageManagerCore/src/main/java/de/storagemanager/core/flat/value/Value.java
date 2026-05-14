package de.storagemanager.core.flat.value;

import java.util.Optional;

public interface Value {

    default <T extends Value> boolean is(Class<T> clazz) {
        return clazz.isInstance(this);
    }

    default <T extends Value> Optional<T> as(Class<T> clazz) {
        return is(clazz) ? Optional.of(clazz.cast(this)) : Optional.empty();
    }
}
