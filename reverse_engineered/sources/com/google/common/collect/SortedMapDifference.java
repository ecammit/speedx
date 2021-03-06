package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.MapDifference.ValueDifference;
import java.util.SortedMap;

@GwtCompatible
public interface SortedMapDifference<K, V> extends MapDifference<K, V> {
    SortedMap<K, ValueDifference<V>> entriesDiffering();

    SortedMap<K, V> entriesInCommon();

    SortedMap<K, V> entriesOnlyOnLeft();

    SortedMap<K, V> entriesOnlyOnRight();
}
