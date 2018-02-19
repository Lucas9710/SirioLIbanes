package remindme.android.com.sirioibanes.activities;

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

import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.dtos.Event;

public class EventActivity extends AbstractActivity {
    private static final String EXTRA_EVENT = "event";
    private static final int STATE_REGULAR = 1;

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

        if (!getIntent().getExtras().containsKey(EXTRA_EVENT)) {
            throw new AssertionError("Use factory method for EventActivity");
        }

        final Event event = new Event((AbstractMap<String, Object>) getIntent()
                .getExtras().get(EXTRA_EVENT));

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

    private void showRegularLayout() {
        ((ViewFlipper) findViewById(R.id.viewFlipper)).setDisplayedChild(STATE_REGULAR);
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }
}
