package remindme.android.com.sirioibanes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.presenters.RegisterPresenter;
import remindme.android.com.sirioibanes.utils.ErrorUtils;
import remindme.android.com.sirioibanes.views.RegisterView;

public class RegisterActivity extends AbstractActivity implements RegisterView {

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mPresenter = new RegisterPresenter(this);

        final AppCompatEditText emailView = findViewById(R.id.fieldEmail);
        final AppCompatEditText nameView = findViewById(R.id.fieldName);
        final AppCompatEditText passwordView = findViewById(R.id.fieldPassword);
        final AppCompatEditText phoneView = findViewById(R.id.fieldPhone);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.registerUser(nameView.getText().toString(),
                        emailView.getText().toString(), passwordView.getText().toString(),
                        phoneView.getText().toString());
            }
        });
    }

    @Override
    protected boolean shouldValidate() {
        return false;
    }

    @Override
    public void onRegisterError() {
        ErrorUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_signup));
    }

    @Override
    public void onRegisterSuccess() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onEmptyError() {
        ErrorUtils.displaySnackbarError(findViewById(R.id.rootView),
                getString(R.string.error_message_empty_fields));
    }
}
