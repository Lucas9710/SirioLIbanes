package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.EventPresenter;
import android.com.sirioibanes.views.EventView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.AbstractMap;

public class EventActivity extends AbstractActivity implements EventView {
    private static final String EXTRA_EVENT = "event";
    private static final int STATE_REGULAR = 1;
    private static final String PARAM_EVENT = "evento";
    private Event mEvent;
    private EventPresenter mPresenter;

    public static Intent getIntent(@NonNull final Context context, @NonNull final Event event) {
        final Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_EVENT, event);

        return intent;
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if ((getIntent().getExtras() != null && !getIntent().getExtras().containsKey(EXTRA_EVENT))
                && (getIntent().getData() == null
                || getIntent().getData().getQueryParameter(PARAM_EVENT) == null)) {
            throw new AssertionError("Use factory method for EventActivity");
        }


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(EXTRA_EVENT)) {

            mEvent = new Event((AbstractMap<String, Object>) getIntent()
                    .getExtras().get(EXTRA_EVENT));

            render(mEvent);
        } else if (getIntent().getData() != null
                && getIntent().getData().getQueryParameter(PARAM_EVENT) != null) {

            mPresenter = new EventPresenter(getIntent().getData().getQueryParameter(PARAM_EVENT));
        }
    }

    private void showRegularLayout() {
        ((ViewFlipper) findViewById(R.id.viewFlipper)).setDisplayedChild(STATE_REGULAR);
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    public void showEvent(@NonNull final AbstractMap<String, Object> event) {
        render(new Event(event));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
        }
    }


    private void render(final Event event) {
        Picasso.with(EventActivity.this).load(event.getPicture())
                .into((ImageView) findViewById(R.id.eventPicture), new Callback() {
                    @Override
                    public void onSuccess() {
                        showRegularLayout();
                    }

                    @Override
                    public void onError() {
                        // TODO: What?
                    }
                });
    }
}
