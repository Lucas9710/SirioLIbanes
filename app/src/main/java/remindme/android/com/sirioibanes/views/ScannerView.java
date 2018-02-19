package remindme.android.com.sirioibanes.views;

import android.support.annotation.NonNull;

import java.util.AbstractMap;

public interface ScannerView {
    void showEvent(@NonNull AbstractMap<String, Object> event);
}
