package android.com.sirioibanes.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.AbstractMap;
import java.util.List;

public interface AssignmentView {
    void showFriends(@NonNull List<AbstractMap<String, Object>> events);

    void showEmptyView();

    void showProgressView();

    Context getContext();
}
