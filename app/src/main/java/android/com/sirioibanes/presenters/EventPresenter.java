package android.com.sirioibanes.presenters;

import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.utils.AuthenticationManager;
import android.com.sirioibanes.views.EventView;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;

public class EventPresenter {

    public static final String ASSISTANCE_CONFIRM = "confirmado";
    public static final String ASSISTANCE_MAYBE = "quizas";
    public static final String ASSISTANCE_NEGATIVE = "cancelado";

    private final String mEventKey;
    private EventView mView;
    private final DatabaseReference myRef;

    public EventPresenter(@NonNull final String eventKey) {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_EVENTS);
        mEventKey = eventKey;
    }

    public EventPresenter(Event event) {
        mEventKey = event.get
    }

    private void getEvents(final String eventKey) {
        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                if (mView != null) {
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        final AbstractMap<String, Object> event = (AbstractMap<String, Object>) postSnapshot.getValue();
                        if (postSnapshot.getKey().equalsIgnoreCase(eventKey)) {
                            mView.showEvent(event);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);
    }

    public void confirmAssistance(@NonNull final String status) {
        myRef.child(mEventKey).push().setValue(AuthenticationManager.getInstance()
                .getUser(mView.getContext()).getNickname(), status).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mView.onAssistanceConfirmed(status);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onAssistanceError();
            }
        });
    }

    public void onAttachView(@NonNull final EventView view) {
        if (mEventKey != null) {
            getEvents(mEventKey);
        }

        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
