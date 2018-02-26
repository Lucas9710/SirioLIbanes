package android.com.sirioibanes.presenters;

import android.com.sirioibanes.adapters.holders.SongViewHolder;
import android.com.sirioibanes.database.DBConstants;
import android.com.sirioibanes.dtos.Song;
import android.com.sirioibanes.views.MusicView;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MusicPresenter {

    private final DatabaseReference mRef;
    private final String mEventKey;
    private MusicView mView;
    private ArrayList<Song> mSongs= new ArrayList<>();
    private final ArrayList<Song> mSongs= new ArrayList<>();
    private Song futureNewSong;
    private Song futureVoteSong;
    private int voteType;

    public MusicPresenter(@NonNull final String eventKey) {
        mEventKey = eventKey;
        mRef = FirebaseDatabase.getInstance().getReference(DBConstants.TABLE_MUSIC);
    }

    private void getSongs() {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (mView == null) {
                    return;
                }

                final List<HashMap<String, Object>> list =
                        ((List<HashMap<String, Object>>) dataSnapshot.child(mEventKey).getValue());

                if (list == null) {
                    mView.onEmptyResults();
                    return;
                }

                mSongs.clear();



                for (int i = 0; i < list.size(); i++) {
                    final Song song = new Song(list.get(i));
                    mSongs.add(song);
                }

                Collections.sort(mSongs, new Comparator<Song>() {
                    @Override
                    public int compare(Song lhs, Song rhs) {
                        return lhs.getVotos() > rhs.getVotos() ? 1 : 0;
                    }
                });

                if (futureNewSong != null) {
                    actualNewSong();
                    futureNewSong = null;
                }

                if (futureVoteSong != null) {
                    actualVoteSong();
                    futureVoteSong = null;
                }


                if (!mSongs.isEmpty()) {
                    mView.showSongs(mSongs);
                } else {
                    mView.onEmptyResults();
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                mView.onError();
            }
        });
    }

    public void newSong(@NonNull final Song song) {
        futureNewSong = song;
        getSongs();
    }

    public void actualNewSong () {
        mSongs.add(futureNewSong);

        Collections.sort(mSongs, new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                return lhs.getVotos() > rhs.getVotos() ? 1 : 0;
            }
        });

        mRef.child(mEventKey).setValue(mSongs);
    }

    public void vote(@NonNull final Song song, final @SongViewHolder.VoteType int type) {
        futureVoteSong = song;
        getSongs();
    }

    public void actualVoteSong () {
        futureVoteSong.vote(voteType);
        mSongs.set(mSongs.indexOf(futureVoteSong), futureVoteSong);

        Collections.sort(mSongs, new Comparator<Song>() {
            @Override
            public int compare(Song lhs, Song rhs) {
                return lhs.getVotos() > rhs.getVotos() ? 1 : 0;
            }
        });

        mRef.child(mEventKey).setValue(mSongs);
    }

    public void onAttachView(@NonNull final MusicView musicView) {
        mView = musicView;
        getSongs();
    }

    public void onDetachView() {
        mView = null;
    }
}
