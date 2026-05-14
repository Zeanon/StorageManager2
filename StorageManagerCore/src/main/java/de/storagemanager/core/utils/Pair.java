package de.storagemanager.core.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@SuppressWarnings("UnusedReturnValue")
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Pair<K, V> {

	private @Nullable K key;

	private @Nullable V value;

	public @Nullable K setKey(final @NotNull K key) {
		final @Nullable K currentKey = this.key;
		this.key = key;
		return currentKey;
	}

	public @Nullable V setValue(final @Nullable V value) {
		final @Nullable V currentValue = this.value;
		this.value = value;
		return currentValue;
	}

	@Override
	public String toString() {
		return this.key + "=" + this.value;
	}
}