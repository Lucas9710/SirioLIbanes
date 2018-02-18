package remindme.android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;
import java.util.List;

import remindme.android.com.sirioibanes.dtos.Event;

public interface HomeView {
    void showEvents(@NonNull List<AbstractMap<String, Object>> events);

    void showEmptyView();

    void showProgressView();

    Context getContext();
}
