package remindme.android.com.sirioibanes.presenters;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import remindme.android.com.sirioibanes.database.DBConstants;
import remindme.android.com.sirioibanes.utils.AuthenticationManager;
import remindme.android.com.sirioibanes.views.HomeView;

public class HomePresenter {

    private HomeView mView;
    private final DatabaseReference myRef;

    public HomePresenter() {
        myRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_EVENTS);
        getEvents();
    }

    private void getEvents() {
        final ValueEventListener eventListener = new ValueEventListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<AbstractMap<String, Object>> events = new ArrayList<>();

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final AbstractMap<String, Object> event = (AbstractMap<String, Object>) postSnapshot.getValue();
                    if (((AbstractMap<String, Object>) event.get("invitados"))
                            .containsKey(AuthenticationManager.getInstance()
                                    .getUser(mView.getContext()).getNickName()))
                    events.add(event);
                }

                if (events.isEmpty()) {
                    mView.showEmptyView();
                } else {
                    mView.showEvents(events);
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {

            }
        };

        myRef.addValueEventListener(eventListener);
    }

    public void onAttachView(@NonNull final HomeView view) {
        if (mView == null) {
            getEvents();
        }

        mView = view;
    }
}
