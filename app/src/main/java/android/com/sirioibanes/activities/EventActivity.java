package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.presenters.EventPresenter;
import android.com.sirioibanes.utils.ErrorUtils;
import android.com.sirioibanes.utils.IntentUtils;
import android.com.sirioibanes.views.EventView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
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

            mPresenter = new EventPresenter(mEvent);

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
    public Context getContext() {
        return EventActivity.this;
    }

    @Override
    public void onAssistanceConfirmed(@NonNull String status) {
        switch (status) {
            case EventPresenter.ASSISTANCE_CONFIRM:
                findViewById(R.id.buttonAssistConfirm).setBackgroundColor(getContext().getResources().getColor(R.color.green));
                break;
            case EventPresenter.ASSISTANCE_MAYBE:
                findViewById(R.id.buttonAssistMaybe).setBackgroundColor(getContext().getResources().getColor(R.color.amber));
                break;
            case EventPresenter.ASSISTANCE_NEGATIVE:
                findViewById(R.id.buttonAssistNegative).setBackgroundColor(getContext().getResources().getColor(R.color.red));
                break;
        }
    }

    @Override
    public void onAssistanceError() {
        ErrorUtils.displaySnackbarError(findViewById(R.id.rootView), "¡Ups! Algo ha salido mal. Vuelve a intentarlo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
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
                        Toast.makeText(EventActivity.this, "No hemos podido cargar los datos del evento.",
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

        findViewById(R.id.buttonMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getAddress()));
                if (IntentUtils.isActivityAvailable(EventActivity.this, mapIntent)) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(EventActivity.this, "La dirección del evento es inválida",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.buttonSocialNetworks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent intent = SocialNetworksActivity.getIntent(EventActivity.this,
                        event.getSocialNetworks());
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonMusic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivity(new Intent(EventActivity.this, MusicActivity.class));
            }
        });

        findViewById(R.id.buttonAssistNegative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmAssistance(EventPresenter.ASSISTANCE_NEGATIVE);
            }
        });

        findViewById(R.id.buttonAssistConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmAssistance(EventPresenter.ASSISTANCE_CONFIRM);
            }
        });

        findViewById(R.id.buttonAssistMaybe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.confirmAssistance(EventPresenter.ASSISTANCE_MAYBE);
            }
        });
    }
}
