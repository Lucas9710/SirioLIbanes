package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.EventsAdapter;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.HomePresenter;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.HomeView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewFlipper;

import java.util.AbstractMap;
import java.util.List;

public class HomeActivity extends AbstractActivity implements HomeView, EventsAdapter.EventClickListener {

    private static final int STATE_PROGRESS = 0;
    private static final int STATE_EMPTY = 1;
    private static final int STATE_LIST = 2;
    private EventsAdapter mEventsAdapter;
    private ViewFlipper mViewFlipper;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPresenter = new HomePresenter();
        mEventsAdapter = new EventsAdapter(this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        mViewFlipper = findViewById(R.id.viewFlipper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mEventsAdapter);

        findViewById(R.id.buttonScanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(HomeActivity.this, ScannerActivity.class));
            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AuthenticationManager.getInstance().logout(HomeActivity.this);
                startActivity(new Intent(HomeActivity.this, SplitterActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    public void showEvents(@NonNull final List<AbstractMap<String, Object>> events) {
        setState(STATE_LIST);
        mEventsAdapter.setItems(events);
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
    public Context getContext() {
        return HomeActivity.this;
    }

    private void setState(final int state) {
        mViewFlipper.setDisplayedChild(state);
    }

    @Override
    public void onClick(@NonNull final Event event) {
        startActivity(EventActivity.getIntent(HomeActivity.this, event));
    }
}
