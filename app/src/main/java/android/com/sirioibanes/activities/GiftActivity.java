package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Gift;
import android.com.sirioibanes.dtos.SocialNetwork;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class GiftActivity extends AbstractActivity {

        private static final String EXTRA_LIST = "list";
        private View redContainer;
        private View blueContainer;
        private View pinkContainer;
        private View greenContainer;
        private View grayContainer;
        private View purpleContainer;
        private HashMap<String, Gift> gifts;

        public static Intent getIntent(@NonNull final Context context, @NonNull final HashMap<String, HashMap> gifts) {
            final Intent intent = new Intent(context, GiftActivity.class);
            intent.putExtra(EXTRA_LIST, gifts);

            return intent;
        }

        @Override
        protected void onCreate(@Nullable final Bundle savedInstanceState) {

            // ACCIONES DEFAULT PREPARATORIAS
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_gift);
            if (getIntent().getExtras() == null) {
                throw new AssertionError("This Activity should be launched using it's factory method");
            }

            //ACCIONES NUESTRAS


            Log.v("LucasLogs", "Estoy por inicializar las variables");
            initializeVariables();  //inicializo mis variables para poder usarlas en los metodos siguientes
            Log.v("LucasLogs", "Estoy por ocultar todas las secciones");
            hideAllSections(); // oculto todas las redes sociales para que por default aparezcan apagadas
            Log.v("LucasLogs", "Estoy por prender una a a una las redes q correspondan");
            turnOnGiftsWhenNeeded(); //prender las redes sociales que corresponda
        }

        public void initializeVariables() {
            // ASIGNO A LAS VARIABLES EL DATO DE LA VISTA
            redContainer = findViewById(R.id.containerRojo);
            blueContainer = findViewById(R.id.containerAzul);
            pinkContainer = findViewById(R.id.containerRosa);
            grayContainer = findViewById(R.id.containerGris);
            greenContainer = findViewById(R.id.containerVerde);
            purpleContainer = findViewById(R.id.containerVioleta);
            gifts = (HashMap<String, Gift>) getIntent().getExtras().getSerializable(EXTRA_LIST);
        }

        public void hideAllSections() {
            // ESTO PONE LOS TRES BOTONES OCULTOS
            redContainer.setVisibility(View.GONE);
            blueContainer.setVisibility(View.GONE);
            pinkContainer.setVisibility(View.GONE);
            grayContainer.setVisibility(View.GONE);
            greenContainer.setVisibility(View.GONE);
            purpleContainer.setVisibility(View.GONE);
        }

        public void turnOnGiftsWhenNeeded() {


            // INTENTAMOS PRENDER EL BOTON DE TWITTER
            for (int i = 0; i < gifts.keySet().size(); i++) {
                final String key = (String) gifts.keySet().toArray()[i];
                final HashMap<String, String> gift = gifts.get(key);
                String link = gift.get("link");
                String name = gift.get("name");
                String amount = gift.get("amount");
                String imageUrl = gift.get("image_url");

                if (key.equals("red")) {
                    turnOnRedBox(link, name, amount, imageUrl);

                }

                if (key.equals("blue")) {
                    turnOnBlueBox(link, name, amount, imageUrl);
                }

                if (key.equals("pink")) {
                    turnOnPinkBox(link, name, amount, imageUrl);
                }

                if (key.equals("gray")) {
                    turnOnGrayBox(link, name, amount, imageUrl);
                }

                if (key.equals("green")) {
                    turnOnGreenBox(link, name, amount, imageUrl);
                }

                if (key.equals("purple")) {
                    turnOnPurpleBox(link, name, amount, imageUrl);
                }
            }
        }

        private void turnOnRedBox(final String link, String name, String amount, String imageUrl) {
            redContainer.setVisibility(View.VISIBLE);
            redContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_red);
            titleLabel.setText(name);

           final TextView amountLabel = findViewById(R.id.amount_red);
           amountLabel.setText(amount);

           if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_red);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView);
           }
        }

        private void turnOnBlueBox(final String link, String name, String amount, String imageUrl) {
            blueContainer.setVisibility(View.VISIBLE);
            blueContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_blue);
            titleLabel.setText(name);
            final TextView amountLabel = findViewById(R.id.amount_blue);
            amountLabel.setText(amount);

            if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_view_blue);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView); }
        }

        private void turnOnPinkBox(final String link, String name, String amount, String imageUrl) {
            pinkContainer.setVisibility(View.VISIBLE);
            pinkContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_pink);
            titleLabel.setText(name);
            final TextView amountLabel = findViewById(R.id.amount_pink);
            amountLabel.setText(amount);

            if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_pink);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView);  }
        }

        private void turnOnGrayBox(final String link, String name, String amount, String imageUrl) {
            grayContainer.setVisibility(View.VISIBLE);
            greenContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_gray);
            titleLabel.setText(name);
            final TextView amountLabel = findViewById(R.id.amount_gray);
            amountLabel.setText(amount);

            if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_gray);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView); }
        }

        private void turnOnGreenBox(final String link, String name, String amount, String imageUrl) {
            greenContainer.setVisibility(View.VISIBLE);
            greenContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_green);
            titleLabel.setText(name);
            final TextView amountLabel = findViewById(R.id.amount_green);
            amountLabel.setText(amount);

            if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_green);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView); }
        }

        private void turnOnPurpleBox(final String link, String name, String amount, String imageUrl) {
            purpleContainer.setVisibility(View.VISIBLE);
            purpleContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleLink(link);
                }
            });

            final TextView titleLabel = findViewById(R.id.title_purple);
            titleLabel.setText(name);
            final TextView amountLabel = findViewById(R.id.amount_purple);
            amountLabel.setText(amount);

            if (imageUrl != null) {

            final ImageView imageView = findViewById(R.id.image_purple);
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(200,200).noFade().into(imageView); }
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
