package com.squareup.picasso;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Request {
    private static final long TOO_LONG_LOG = TimeUnit.SECONDS.toNanos(5);
    public final boolean centerCrop;
    public final boolean centerInside;
    public final Config config;
    public final boolean hasRotationPivot;
    int id;
    int networkPolicy;
    public final boolean onlyScaleDown;
    public final Picasso$Priority priority;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    public final String stableKey;
    long started;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        private boolean centerCrop;
        private boolean centerInside;
        private Config config;
        private boolean hasRotationPivot;
        private boolean onlyScaleDown;
        private Picasso$Priority priority;
        private int resourceId;
        private float rotationDegrees;
        private float rotationPivotX;
        private float rotationPivotY;
        private String stableKey;
        private int targetHeight;
        private int targetWidth;
        private List<Transformation> transformations;
        private Uri uri;

        public Builder(Uri uri) {
            setUri(uri);
        }

        public Builder(int i) {
            setResourceId(i);
        }

        Builder(Uri uri, int i, Config config) {
            this.uri = uri;
            this.resourceId = i;
            this.config = config;
        }

        private Builder(Request request) {
            this.uri = request.uri;
            this.resourceId = request.resourceId;
            this.stableKey = request.stableKey;
            this.targetWidth = request.targetWidth;
            this.targetHeight = request.targetHeight;
            this.centerCrop = request.centerCrop;
            this.centerInside = request.centerInside;
            this.rotationDegrees = request.rotationDegrees;
            this.rotationPivotX = request.rotationPivotX;
            this.rotationPivotY = request.rotationPivotY;
            this.hasRotationPivot = request.hasRotationPivot;
            this.onlyScaleDown = request.onlyScaleDown;
            if (request.transformations != null) {
                this.transformations = new ArrayList(request.transformations);
            }
            this.config = request.config;
            this.priority = request.priority;
        }

        boolean hasImage() {
            return (this.uri == null && this.resourceId == 0) ? false : true;
        }

        boolean hasSize() {
            return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
        }

        boolean hasPriority() {
            return this.priority != null;
        }

        public Builder setUri(Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.uri = uri;
            this.resourceId = 0;
            return this;
        }

        public Builder setResourceId(int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.resourceId = i;
            this.uri = null;
            return this;
        }

        public Builder stableKey(String str) {
            this.stableKey = str;
            return this;
        }

        public Builder resize(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            } else if (i2 == 0 && i == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            } else {
                this.targetWidth = i;
                this.targetHeight = i2;
                return this;
            }
        }

        public Builder clearResize() {
            this.targetWidth = 0;
            this.targetHeight = 0;
            this.centerCrop = false;
            this.centerInside = false;
            return this;
        }

        public Builder centerCrop() {
            if (this.centerInside) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.centerCrop = true;
            return this;
        }

        public Builder clearCenterCrop() {
            this.centerCrop = false;
            return this;
        }

        public Builder centerInside() {
            if (this.centerCrop) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.centerInside = true;
            return this;
        }

        public Builder clearCenterInside() {
            this.centerInside = false;
            return this;
        }

        public Builder onlyScaleDown() {
            if (this.targetHeight == 0 && this.targetWidth == 0) {
                throw new IllegalStateException("onlyScaleDown can not be applied without resize");
            }
            this.onlyScaleDown = true;
            return this;
        }

        public Builder clearOnlyScaleDown() {
            this.onlyScaleDown = false;
            return this;
        }

        public Builder rotate(float f) {
            this.rotationDegrees = f;
            return this;
        }

        public Builder rotate(float f, float f2, float f3) {
            this.rotationDegrees = f;
            this.rotationPivotX = f2;
            this.rotationPivotY = f3;
            this.hasRotationPivot = true;
            return this;
        }

        public Builder clearRotation() {
            this.rotationDegrees = 0.0f;
            this.rotationPivotX = 0.0f;
            this.rotationPivotY = 0.0f;
            this.hasRotationPivot = false;
            return this;
        }

        public Builder config(Config config) {
            this.config = config;
            return this;
        }

        public Builder priority(Picasso$Priority picasso$Priority) {
            if (picasso$Priority == null) {
                throw new IllegalArgumentException("Priority invalid.");
            } else if (this.priority != null) {
                throw new IllegalStateException("Priority already set.");
            } else {
                this.priority = picasso$Priority;
                return this;
            }
        }

        public Builder transform(Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            } else if (transformation.key() == null) {
                throw new IllegalArgumentException("Transformation key must not be null.");
            } else {
                if (this.transformations == null) {
                    this.transformations = new ArrayList(2);
                }
                this.transformations.add(transformation);
                return this;
            }
        }

        public Builder transform(List<? extends Transformation> list) {
            if (list == null) {
                throw new IllegalArgumentException("Transformation list must not be null.");
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                transform((Transformation) list.get(i));
            }
            return this;
        }

        public Request build() {
            if (this.centerInside && this.centerCrop) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            } else if (this.centerCrop && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            } else if (this.centerInside && this.targetWidth == 0 && this.targetHeight == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            } else {
                if (this.priority == null) {
                    this.priority = Picasso$Priority.NORMAL;
                }
                return new Request(this.uri, this.resourceId, this.stableKey, this.transformations, this.targetWidth, this.targetHeight, this.centerCrop, this.centerInside, this.onlyScaleDown, this.rotationDegrees, this.rotationPivotX, this.rotationPivotY, this.hasRotationPivot, this.config, this.priority);
            }
        }
    }

    private Request(Uri uri, int i, String str, List<Transformation> list, int i2, int i3, boolean z, boolean z2, boolean z3, float f, float f2, float f3, boolean z4, Config config, Picasso$Priority picasso$Priority) {
        this.uri = uri;
        this.resourceId = i;
        this.stableKey = str;
        if (list == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(list);
        }
        this.targetWidth = i2;
        this.targetHeight = i3;
        this.centerCrop = z;
        this.centerInside = z2;
        this.onlyScaleDown = z3;
        this.rotationDegrees = f;
        this.rotationPivotX = f2;
        this.rotationPivotY = f3;
        this.hasRotationPivot = z4;
        this.config = config;
        this.priority = picasso$Priority;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Request{");
        if (this.resourceId > 0) {
            stringBuilder.append(this.resourceId);
        } else {
            stringBuilder.append(this.uri);
        }
        if (!(this.transformations == null || this.transformations.isEmpty())) {
            for (Transformation key : this.transformations) {
                stringBuilder.append(' ').append(key.key());
            }
        }
        if (this.stableKey != null) {
            stringBuilder.append(" stableKey(").append(this.stableKey).append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        }
        if (this.targetWidth > 0) {
            stringBuilder.append(" resize(").append(this.targetWidth).append(CoreConstants.COMMA_CHAR).append(this.targetHeight).append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        }
        if (this.centerCrop) {
            stringBuilder.append(" centerCrop");
        }
        if (this.centerInside) {
            stringBuilder.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            stringBuilder.append(" rotation(").append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                stringBuilder.append(" @ ").append(this.rotationPivotX).append(CoreConstants.COMMA_CHAR).append(this.rotationPivotY);
            }
            stringBuilder.append(CoreConstants.RIGHT_PARENTHESIS_CHAR);
        }
        if (this.config != null) {
            stringBuilder.append(' ').append(this.config);
        }
        stringBuilder.append(CoreConstants.CURLY_RIGHT);
        return stringBuilder.toString();
    }

    String logId() {
        long nanoTime = System.nanoTime() - this.started;
        if (nanoTime > TOO_LONG_LOG) {
            return plainId() + '+' + TimeUnit.NANOSECONDS.toSeconds(nanoTime) + 's';
        }
        return plainId() + '+' + TimeUnit.NANOSECONDS.toMillis(nanoTime) + "ms";
    }

    String plainId() {
        return "[R" + this.id + ']';
    }

    String getName() {
        if (this.uri != null) {
            return String.valueOf(this.uri.getPath());
        }
        return Integer.toHexString(this.resourceId);
    }

    public boolean hasSize() {
        return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
    }

    boolean needsTransformation() {
        return needsMatrixTransform() || hasCustomTransformations();
    }

    boolean needsMatrixTransform() {
        return hasSize() || this.rotationDegrees != 0.0f;
    }

    boolean hasCustomTransformations() {
        return this.transformations != null;
    }

    public Builder buildUpon() {
        return new Builder();
    }
}
