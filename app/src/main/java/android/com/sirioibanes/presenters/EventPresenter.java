package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.views.EventView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class EventPresenter {

    private final String mEventKey;
    private EventView mView;
    private final DatabaseReference myRef;

    public EventPresenter(@NonNull final String eventKey) {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_EVENTS);
        mEventKey = eventKey;
    }

    private void getEvents(final String eventKey) {
        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<AbstractMap<String, Object>> events = new ArrayList<>();

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final AbstractMap<String, Object> event = (AbstractMap<String, Object>) postSnapshot.getValue();
                    if (postSnapshot.getKey().equalsIgnoreCase(eventKey)) {
                        mView.showEvent(event);
                    }
                }

            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);
    }

    public void onAttachView(@NonNull final EventView view) {
        if (mEventKey != null) {
            getEvents(mEventKey);
        }

        mView = view;
    }
}
