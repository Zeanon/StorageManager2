package de.storagemanager.core.flat.value;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NullValue implements Value {
    public static final NullValue INSTANCE = new NullValue();
}
