package com.mapbox.mapboxsdk.annotations;

import android.support.annotation.NonNull;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

public abstract class Annotation implements Comparable<Annotation> {
    private long id = -1;
    protected MapView mapView;
    protected MapboxMap mapboxMap;

    protected Annotation() {
    }

    public long getId() {
        return this.id;
    }

    public void remove() {
        if (this.mapboxMap != null) {
            this.mapboxMap.removeAnnotation(this);
        }
    }

    public void setId(long j) {
        this.id = j;
    }

    public void setMapboxMap(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
    }

    protected MapboxMap getMapboxMap() {
        return this.mapboxMap;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    protected MapView getMapView() {
        return this.mapView;
    }

    public int compareTo(@NonNull Annotation annotation) {
        if (this.id < annotation.getId()) {
            return 1;
        }
        if (this.id > annotation.getId()) {
            return -1;
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Annotation)) {
            return false;
        }
        if (this.id != ((Annotation) obj).getId()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }
}
