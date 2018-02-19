package remindme.android.com.sirioibanes.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class DeeplinkHandlerActivity extends AbstractActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getData() != null) {
            Toast.makeText(this, getIntent().getData().getQueryParameter("evento"),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }
}
