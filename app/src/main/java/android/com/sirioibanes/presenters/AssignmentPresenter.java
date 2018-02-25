package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.AssignmentView;
import android.com.sirioibanes.views.HomeView;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by federicobustosfierro on 2/22/18.
 */

public class AssignmentPresenter {

    private AssignmentView mView;
    private final DatabaseReference myRef;

    public AssignmentPresenter() {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_ASSIGNMENTS);
    }

    private void getFriends() {


        Log.d("ASSIGN_TAG","getting friends");

        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<AbstractMap<String, Object>> events = new ArrayList<>();

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                   Log.d("ASSIGN_TAG",postSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);
    }

    public void onAttachView(@NonNull final AssignmentView view) {
        getFriends();
        mView = view;
    }
}
