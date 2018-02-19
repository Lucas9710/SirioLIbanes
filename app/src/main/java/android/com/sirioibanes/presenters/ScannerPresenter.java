package android.com.sirioibanes.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.views.ScannerView;

public class ScannerPresenter {

    private ScannerView mView;

    public ScannerPresenter() {
    }

    public void onAttachView(@NonNull final ScannerView view) {
        mView = view;
    }

    public void getEvent(@NonNull final String code) {
        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_EVENTS).child(code);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final AbstractMap<String, Object> event = (AbstractMap<String, Object>) dataSnapshot.getValue();
                mView.showEvent(event);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        });
    }
}
