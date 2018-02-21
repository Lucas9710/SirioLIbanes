package android.com.sirioibanes.presenters;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.User;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.RegisterView;

public class RegisterPresenter {
    private final FirebaseAuth mAuth;
    private final RegisterView mView;
    private final DatabaseReference myRef;

    public RegisterPresenter(@NonNull final RegisterView registerView) {
        mAuth = FirebaseAuth.getInstance();
        mView = registerView;
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_USERS);
    }

    public void registerUser(@NonNull final String name, @NonNull final String email,
                             @NonNull final String password, @NonNull final String phone) {

        if (name.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()
                || phone.trim().isEmpty()) {
            mView.onEmptyError();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull final Exception e) {
                        mView.onRegisterError();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String nickName = email.replace("@", "")
                                    .replace("-", "").replace("_", "")
                                    + new Random().nextInt(999);

                            final User user = new User(task.getResult().getUser().getUid(), name, nickName, email, phone, null);
                            myRef.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull final Task<Void> task) {
                                    mView.onRegisterSuccess();
                                }
                            });

                            AuthenticationManager.getInstance().onRegister(user);

                        } else {
                            mView.onRegisterError();
                        }
                    }
                });
    }
}
