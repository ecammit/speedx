package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;

@GwtCompatible(emulated = true)
@Beta
public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
    final DiscreteDomain<C> domain;

    abstract ContiguousSet<C> headSetImpl(C c, boolean z);

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet);

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType boundType, BoundType boundType2);

    abstract ContiguousSet<C> subSetImpl(C c, boolean z, C c2, boolean z2);

    abstract ContiguousSet<C> tailSetImpl(C c, boolean z);

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(discreteDomain);
        try {
            Range intersection;
            if (range.hasLowerBound()) {
                Range<C> range2 = range;
            } else {
                intersection = range.intersection(Range.atLeast(discreteDomain.minValue()));
            }
            if (!range.hasUpperBound()) {
                intersection = intersection.intersection(Range.atMost(discreteDomain.maxValue()));
            }
            Object obj = (intersection.isEmpty() || Range.compareOrThrow(range.lowerBound.leastValueAbove(discreteDomain), range.upperBound.greatestValueBelow(discreteDomain)) > 0) ? 1 : null;
            if (obj != null) {
                return new EmptyContiguousSet(discreteDomain);
            }
            return new RegularContiguousSet(intersection, discreteDomain);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }

    ContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(Ordering.natural());
        this.domain = discreteDomain;
    }

    public ContiguousSet<C> headSet(C c) {
        return headSetImpl((Comparable) Preconditions.checkNotNull(c), false);
    }

    @GwtIncompatible("NavigableSet")
    public ContiguousSet<C> headSet(C c, boolean z) {
        return headSetImpl((Comparable) Preconditions.checkNotNull(c), z);
    }

    public ContiguousSet<C> subSet(C c, C c2) {
        boolean z;
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        if (comparator().compare(c, c2) <= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z);
        return subSetImpl((Comparable) c, true, (Comparable) c2, false);
    }

    @GwtIncompatible("NavigableSet")
    public ContiguousSet<C> subSet(C c, boolean z, C c2, boolean z2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(comparator().compare(c, c2) <= 0);
        return subSetImpl((Comparable) c, z, (Comparable) c2, z2);
    }

    public ContiguousSet<C> tailSet(C c) {
        return tailSetImpl((Comparable) Preconditions.checkNotNull(c), true);
    }

    @GwtIncompatible("NavigableSet")
    public ContiguousSet<C> tailSet(C c, boolean z) {
        return tailSetImpl((Comparable) Preconditions.checkNotNull(c), z);
    }

    public String toString() {
        return range().toString();
    }

    @Deprecated
    public static <E> ImmutableSortedSet$Builder<E> builder() {
        throw new UnsupportedOperationException();
    }
}
