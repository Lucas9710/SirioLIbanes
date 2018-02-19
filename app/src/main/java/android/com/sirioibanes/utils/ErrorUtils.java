package android.com.sirioibanes.utils;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import android.com.sirioibanes.R;

public class ErrorUtils {

    public static void displaySnackbarError(@NonNull final View view, final String message) {
        final Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getContext().getResources()
                .getColor(android.R.color.holo_red_light));

        snackbar.show();
    }
}
