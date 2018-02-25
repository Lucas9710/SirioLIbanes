package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.EventsAdapter;
import android.com.sirioibanes.presenters.AssignmentPresenter;
import android.com.sirioibanes.presenters.HomePresenter;
import android.com.sirioibanes.views.AssignmentView;
import android.com.sirioibanes.views.HomeView;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.AbstractMap;
import java.util.List;

public class AssignmentActivity extends AbstractActivity implements AssignmentView {

    private static final int STATE_PROGRESS = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_LIST = 2;
    private AssignmentPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AssignmentPresenter();
        setContentView(R.layout.activity_assignment);
    }

    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    public void showEmptyView() {
        setState(STATE_EMPTY);
    }

    @Override
    public void showProgressView() {
        setState(STATE_PROGRESS);
    }

    @Override
    public void showFriends(@NonNull final List<AbstractMap<String, Object>> events) {
        setState(STATE_LIST);
    }


    public Context getContext() {
        return AssignmentActivity.this;
    }


    @Override
    protected boolean shouldValidate() {
        return false;
    }


    private void setState(final int state) {
        //cambiar estado
    }

}
