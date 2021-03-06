package com.facebook.stetho.inspector.elements.android;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import com.facebook.stetho.common.Accumulator;
import com.facebook.stetho.inspector.elements.AbstractChainedDescriptor;
import com.facebook.stetho.inspector.elements.Descriptor.Host;

final class DialogDescriptor extends AbstractChainedDescriptor<Dialog> implements HighlightableDescriptor {
    DialogDescriptor() {
    }

    protected void onGetChildren(Dialog dialog, Accumulator<Object> accumulator) {
        Window window = dialog.getWindow();
        if (window != null) {
            accumulator.store(window);
        }
    }

    public View getViewForHighlighting(Object obj) {
        Host host = getHost();
        if (host instanceof AndroidDescriptorHost) {
            return ((AndroidDescriptorHost) host).getHighlightingView(((Dialog) obj).getWindow());
        }
        return null;
    }
}
