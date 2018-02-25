package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.SocialNetwork;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class SocialNetworksActivity extends AbstractActivity {

    private static final String EXTRA_LIST = "list";

    public static Intent getIntent(@NonNull final Context context,
                                   @NonNull final HashMap<String, HashMap> socialNetwork) {
        final Intent intent = new Intent(context, SocialNetworksActivity.class);
        intent.putExtra(EXTRA_LIST, socialNetwork);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialnetworks);

        if (getIntent().getExtras() == null) {
            throw new AssertionError("This Activity should be launched using it's factory method");
        }

        final HashMap<String, SocialNetwork> socialNetworks = (HashMap<String, SocialNetwork>) getIntent()
                .getExtras().getSerializable(EXTRA_LIST);

        for (int i = 0; i < socialNetworks.keySet().size(); i++) {
            final String key = (String) socialNetworks.keySet().toArray()[i];
            final HashMap<String, String> socialNetwork = socialNetworks.get(key);

            switch (key) {
                case SocialNetwork.FACEBOOK:
                    final View fbButton = findViewById(R.id.containerFacebook);
                    fbButton.setVisibility(View.VISIBLE);
                    fbButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleLink(socialNetwork.get("link"));
                        }
                    });

                    final TextView facebookView = findViewById(R.id.buttonFacebook);
                    facebookView.setText(socialNetwork.get("name"));
                    break;

                case SocialNetwork.TWITTER:
                    final View twButton = findViewById(R.id.containerTwitter);
                    twButton.setVisibility(View.VISIBLE);
                    twButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleLink(socialNetwork.get("link"));
                        }
                    });

                    final TextView twitterView = findViewById(R.id.buttonTwitter);
                    twitterView.setText(socialNetwork.get("name"));
                    break;

                case SocialNetwork.INSTAGRAM:
                    final View instButton = findViewById(R.id.containerInstagram);
                    instButton.setVisibility(View.VISIBLE);
                    instButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleLink(socialNetwork.get("link"));
                        }
                    });

                    final TextView instagramView = findViewById(R.id.buttonInstagram);
                    instagramView.setText(socialNetwork.get("name"));
                    break;
            }
        }
    }

    private void handleLink(final String link) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
        } catch (ActivityNotFoundException e) {
            FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView), "El link es inválido");
        }
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }
}
