package de.storagemanager.core.interfaces;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@SuppressWarnings("rawtypes")
public interface CollectionsProvider<M extends Map, L extends List> {

    @NotNull M newMap();

    @NotNull M newMap(final @NonNull Map map);

    @NotNull L newList();

    @NotNull L newList(final @NonNull Collection collection);

    final class Default implements CollectionsProvider<Map, List> {
        @Override
        public @NotNull Map newMap() {
            return new HashMap<>();
        }

        @Override
        public @NotNull Map newMap(@NonNull Map map) {
            return new HashMap<>(map);
        }

        @Override
        public @NotNull List newList() {
            return new ArrayList<>();
        }

        @Override
        public @NotNull List newList(@NonNull Collection collection) {
            return new ArrayList<>(collection);
        }
    }

    default CollectionsProvider<Map, List> synchronize() {
        CollectionsProvider<M, L> self = this;
        return new CollectionsProvider<>() {
            @Override
            public @NotNull Map newMap() {
                return Collections.synchronizedMap(self.newMap());
            }

            @Override
            public @NotNull Map newMap(@NonNull Map map) {
                return Collections.synchronizedMap(self.newMap(map));
            }

            @Override
            public @NotNull List newList() {
                return Collections.synchronizedList(self.newList());
            }

            @Override
            public @NotNull List newList(@NonNull Collection collection) {
                return Collections.synchronizedList(self.newList(collection));
            }

            @Override
            public CollectionsProvider<Map, List> synchronize() {
                return this;
            }
        };
    }
}
