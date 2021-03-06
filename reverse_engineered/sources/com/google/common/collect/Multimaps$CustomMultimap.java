package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;

class Multimaps$CustomMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 0;
    transient Supplier<? extends Collection<V>> factory;

    Multimaps$CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
        super(map);
        this.factory = (Supplier) Preconditions.checkNotNull(supplier);
    }

    protected Collection<V> createCollection() {
        return (Collection) this.factory.get();
    }

    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.factory);
        objectOutputStream.writeObject(backingMap());
    }

    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.factory = (Supplier) objectInputStream.readObject();
        setMap((Map) objectInputStream.readObject());
    }
}
