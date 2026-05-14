package de.storagemanager.core.flat.value;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanValue implements Value {
    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);

    private final boolean value;

    public boolean get() {
        return value;
    }
}
