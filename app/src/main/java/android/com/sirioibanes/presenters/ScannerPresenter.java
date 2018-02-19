package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.views.ScannerView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.AbstractMap;

public class ScannerPresenter {

    private ScannerView mView;

    public ScannerPresenter() {
    }

    public void onAttachView(@NonNull final ScannerView view) {
        mView = view;
    }

    public void getEvent(@NonNull final String code) {

        final DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference(DBConstants.TABLE_EVENTS).child(getNormalizedCode(code));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final AbstractMap<String, Object> event = (AbstractMap<String, Object>) dataSnapshot.getValue();
                if (event == null) {
                    mView.onInvalidEvent();
                } else {
                    mView.showEvent(event);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        });

    }

    private String getNormalizedCode(final String code) {
        return Normalizer.normalize(code, Normalizer.Form.NFD)
                .replaceAll("[^a-zA-Z]", "");
    }
}
