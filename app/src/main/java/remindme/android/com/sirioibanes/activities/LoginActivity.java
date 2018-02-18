package remindme.android.com.sirioibanes.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.presenters.LoginPresenter;
import remindme.android.com.sirioibanes.utils.ErrorUtils;
import remindme.android.com.sirioibanes.views.LoginView;

public class LoginActivity extends AbstractActivity implements LoginView {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final LoginPresenter presenter = new LoginPresenter(this);
        final AppCompatEditText emailView = findViewById(R.id.fieldEmail);
        final AppCompatEditText passwordView = findViewById(R.id.fieldPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                presenter.login(emailView.getText().toString(), passwordView.getText().toString());
            }
        });
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }

    @Override
    public void onEmptyFields() {
        ErrorUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_empty_fields));
    }

    @Override
    public void onLogin() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onLoginError() {
        ErrorUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_login));
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }
}
