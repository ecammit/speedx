package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C1478R;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzab;
import com.mapbox.mapboxsdk.telemetry.MapboxEvent;

public final class CameraPosition extends AbstractSafeParcelable {
    public static final zza CREATOR = new zza();
    public final float bearing;
    private final int mVersionCode;
    public final LatLng target;
    public final float tilt;
    public final float zoom;

    public static final class Builder {
        private LatLng aic;
        private float aid;
        private float aie;
        private float aif;

        public Builder(CameraPosition cameraPosition) {
            this.aic = cameraPosition.target;
            this.aid = cameraPosition.zoom;
            this.aie = cameraPosition.tilt;
            this.aif = cameraPosition.bearing;
        }

        public Builder bearing(float f) {
            this.aif = f;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(this.aic, this.aid, this.aie, this.aif);
        }

        public Builder target(LatLng latLng) {
            this.aic = latLng;
            return this;
        }

        public Builder tilt(float f) {
            this.aie = f;
            return this;
        }

        public Builder zoom(float f) {
            this.aid = f;
            return this;
        }
    }

    CameraPosition(int i, LatLng latLng, float f, float f2, float f3) {
        zzab.zzb((Object) latLng, (Object) "null camera target");
        boolean z = 0.0f <= f2 && f2 <= 90.0f;
        zzab.zzb(z, "Tilt needs to be between 0 and 90 inclusive: %s", Float.valueOf(f2));
        this.mVersionCode = i;
        this.target = latLng;
        this.zoom = f;
        this.tilt = f2 + 0.0f;
        if (((double) f3) <= 0.0d) {
            f3 = (f3 % 360.0f) + 360.0f;
        }
        this.bearing = f3 % 360.0f;
    }

    public CameraPosition(LatLng latLng, float f, float f2, float f3) {
        this(1, latLng, f, f2, f3);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition cameraPosition) {
        return new Builder(cameraPosition);
    }

    public static CameraPosition createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, C1478R.styleable.MapAttrs);
        LatLng latLng = new LatLng((double) (obtainAttributes.hasValue(C1478R.styleable.MapAttrs_cameraTargetLat) ? obtainAttributes.getFloat(C1478R.styleable.MapAttrs_cameraTargetLat, 0.0f) : 0.0f), (double) (obtainAttributes.hasValue(C1478R.styleable.MapAttrs_cameraTargetLng) ? obtainAttributes.getFloat(C1478R.styleable.MapAttrs_cameraTargetLng, 0.0f) : 0.0f));
        Builder builder = builder();
        builder.target(latLng);
        if (obtainAttributes.hasValue(C1478R.styleable.MapAttrs_cameraZoom)) {
            builder.zoom(obtainAttributes.getFloat(C1478R.styleable.MapAttrs_cameraZoom, 0.0f));
        }
        if (obtainAttributes.hasValue(C1478R.styleable.MapAttrs_cameraBearing)) {
            builder.bearing(obtainAttributes.getFloat(C1478R.styleable.MapAttrs_cameraBearing, 0.0f));
        }
        if (obtainAttributes.hasValue(C1478R.styleable.MapAttrs_cameraTilt)) {
            builder.tilt(obtainAttributes.getFloat(C1478R.styleable.MapAttrs_cameraTilt, 0.0f));
        }
        return builder.build();
    }

    public static final CameraPosition fromLatLngZoom(LatLng latLng, float f) {
        return new CameraPosition(latLng, f, 0.0f, 0.0f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) obj;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzaa.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public String toString() {
        return zzaa.zzz(this).zzg("target", this.target).zzg(MapboxEvent.KEY_ZOOM, Float.valueOf(this.zoom)).zzg("tilt", Float.valueOf(this.tilt)).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
