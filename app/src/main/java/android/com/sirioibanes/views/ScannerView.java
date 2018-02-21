package android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;

public interface ScannerView {
    void showEvent(@NonNull AbstractMap<String, Object> event);

    void onInvalidEvent();

    Context getContext();

    void onEventAssociationError();
}
