package io.rong.imageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import io.rong.imageloader.core.assist.ViewScaleType;
import io.rong.imageloader.utils.C1523L;
import java.lang.reflect.Field;

public class ImageViewAware extends ViewAware {
    public ImageViewAware(ImageView imageView) {
        super(imageView);
    }

    public ImageViewAware(ImageView imageView, boolean z) {
        super(imageView, z);
    }

    public int getWidth() {
        int width = super.getWidth();
        if (width <= 0) {
            ImageView imageView = (ImageView) this.viewRef.get();
            if (imageView != null) {
                return getImageViewFieldValue(imageView, "mMaxWidth");
            }
        }
        return width;
    }

    public int getHeight() {
        int height = super.getHeight();
        if (height <= 0) {
            ImageView imageView = (ImageView) this.viewRef.get();
            if (imageView != null) {
                return getImageViewFieldValue(imageView, "mMaxHeight");
            }
        }
        return height;
    }

    public ViewScaleType getScaleType() {
        ImageView imageView = (ImageView) this.viewRef.get();
        if (imageView != null) {
            return ViewScaleType.fromImageView(imageView);
        }
        return super.getScaleType();
    }

    public ImageView getWrappedView() {
        return (ImageView) super.getWrappedView();
    }

    protected void setImageDrawableInto(Drawable drawable, View view) {
        ((ImageView) view).setImageDrawable(drawable);
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }
    }

    protected void setImageBitmapInto(Bitmap bitmap, View view) {
        ((ImageView) view).setImageBitmap(bitmap);
    }

    private static int getImageViewFieldValue(Object obj, String str) {
        try {
            Field declaredField = ImageView.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            int intValue = ((Integer) declaredField.get(obj)).intValue();
            if (intValue > 0 && intValue < Integer.MAX_VALUE) {
                return intValue;
            }
        } catch (Throwable e) {
            C1523L.e(e);
        }
        return 0;
    }
}
