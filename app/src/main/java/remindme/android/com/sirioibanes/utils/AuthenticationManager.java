package remindme.android.com.sirioibanes.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;

import remindme.android.com.sirioibanes.database.DBConstants;
import remindme.android.com.sirioibanes.dtos.User;

public class AuthenticationManager {

    public interface LoginListener {
        void onLogin(@NonNull User user);
    }

    private static final String PREF_USER = "pref-user";
    private static AuthenticationManager INSTANCE;
    private final FirebaseAuth mAuth;
    private final DatabaseReference myRef;
    private User mUser;

    private AuthenticationManager() {
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_USERS);
    }

    public static AuthenticationManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationManager();
        }

        return INSTANCE;
    }

    public boolean isLoggedIn() {
        return mAuth.getCurrentUser() != null;
    }

    public void onLogin(@NonNull final Context context, @NonNull final String email,
                        @Nullable final LoginListener listener) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final AbstractMap<String, Object> user = (AbstractMap<String, Object>) snapshot.getValue();
                    if ((user.get("email")).equals(email)) {
                        mUser = new User((String) user.get("nombre"), (String) user.get("nickname"), (String) user.get("email"),
                                (String) user.get("telefono"), (AbstractMap<String, Boolean>) user.get("eventos"));

                        SharedPreferencesUtils.getInstance(context).save(PREF_USER, mUser);

                        if (listener != null) {
                            listener.onLogin(mUser);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                // TODO: SOMETHING?
            }
        });
    }


    public User getUser(@NonNull final Context context) {
        if (mUser == null) {
            mUser = SharedPreferencesUtils.getInstance(context).read(PREF_USER, User.class);
            onLogin(context, mUser.getEmail(), null);
        }

        return mUser;
    }

    public void onRegister(@NonNull final User user) {
        mUser = user;
    }
}
