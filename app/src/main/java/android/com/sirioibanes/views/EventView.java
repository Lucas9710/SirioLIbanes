package android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;

public interface EventView {
    void showEvent(AbstractMap<String, Object> event);

    Context getContext();

    void onAssistanceConfirmed(@NonNull String status);

    void onAssistanceError();
}
