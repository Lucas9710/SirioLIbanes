package android.com.sirioibanes.presenters;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.com.sirioibanes.dtos.User;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.LoginView;

public class LoginPresenter {

    private final LoginView mView;

    public LoginPresenter(@NonNull final LoginView view) {
        mView = view;
    }

    public void login(@NonNull final String email, @NonNull final String password) {
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            mView.onEmptyFields();
            return;
        }

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AuthenticationManager.getInstance().onLogin(mView.getContext(), email,
                            new AuthenticationManager.LoginListener() {
                                @Override
                                public void onLogin(@NonNull final User user) {
                                    mView.onLogin();
                                }
                            });
                } else {
                    mView.onLoginError();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                mView.onLoginError();
            }
        });
    }
}
