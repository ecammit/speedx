package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.appcompat.C0246R;

class ThemeUtils {
    private static final int[] APPCOMPAT_CHECK_ATTRS = new int[]{C0246R.attr.colorPrimary};

    ThemeUtils() {
    }

    static void checkAppCompatTheme(Context context) {
        int i = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        if (!obtainStyledAttributes.hasValue(0)) {
            i = 1;
        }
        if (obtainStyledAttributes != null) {
            obtainStyledAttributes.recycle();
        }
        if (i != 0) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }
}