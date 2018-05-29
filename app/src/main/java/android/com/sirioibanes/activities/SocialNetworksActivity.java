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
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class SocialNetworksActivity extends AbstractActivity {

    private View twitterContainer;
    private static final String EXTRA_LIST = "list";
    private View facebookContainer;
    private View instagramContainer;

    public static Intent getIntent(@NonNull final Context context, @NonNull final HashMap<String, HashMap> socialNetwork) {
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

        hideAllSections();

        // INTENTAMOS PRENDER EL BOTON DE TWITTER
        for (int i = 0; i < socialNetworks.keySet().size(); i++) {
            final String key = (String) socialNetworks.keySet().toArray()[i];
            final HashMap<String, String> socialNetwork = socialNetworks.get(key);

            if (key.equals("twitter")) {
                    twitterContainer.setVisibility(View.VISIBLE);
                    twitterContainer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            handleLink(socialNetwork.get("link"));
                        }
                    });

                    final TextView twitterView = findViewById(R.id.buttonTwitter);
                    twitterView.setText(socialNetwork.get("name"));
            }
        }
        for (int i = 0; i < socialNetworks.keySet().size(); i++) {
            final String key = (String) socialNetworks.keySet().toArray()[i];
            final HashMap<String, String> socialNetwork = socialNetworks.get(key);

            if (key.equals("facebook")) {
                facebookContainer.setVisibility(View.VISIBLE);
                facebookContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleLink(socialNetwork.get("link"));
                    }
                });

                final TextView twitterView = findViewById(R.id.buttonFacebook);
                twitterView.setText(socialNetwork.get("name"));
            }
        }
        for (int i = 0; i < socialNetworks.keySet().size(); i++) {
            final String key = (String) socialNetworks.keySet().toArray()[i];
            final HashMap<String, String> socialNetwork = socialNetworks.get(key);

            if (key.equals("instagram")) {
                instagramContainer.setVisibility(View.VISIBLE);
                instagramContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleLink(socialNetwork.get("link"));
                    }
                });

                final TextView twitterView = findViewById(R.id.buttonInstagram);
                twitterView.setText(socialNetwork.get("name"));
            }
        }
    }

    public void hideAllSections() {
        // ESTO PONE LOS TRES BOTONES OCULTOS
        twitterContainer = findViewById(R.id.containerTwitter);
        twitterContainer.setVisibility(View.GONE);

        facebookContainer = findViewById(R.id.containerFacebook);
        facebookContainer.setVisibility(View.GONE);

        instagramContainer = findViewById(R.id.containerInstagram);
        twitterContainer.setVisibility(View.GONE);

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
