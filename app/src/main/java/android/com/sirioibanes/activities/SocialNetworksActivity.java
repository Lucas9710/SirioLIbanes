package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SocialNetworksActivity extends AbstractActivity{

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialnetworks);
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }
}
