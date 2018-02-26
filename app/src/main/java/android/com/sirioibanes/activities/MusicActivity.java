package android.com.sirioibanes.activities;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.MusicAdapter;
import android.com.sirioibanes.adapters.holders.SongViewHolder;
import android.com.sirioibanes.dtos.Event;
import android.com.sirioibanes.dtos.Song;
import android.com.sirioibanes.presenters.MusicPresenter;
import android.com.sirioibanes.utils.FeedbackUtils;
import android.com.sirioibanes.views.MusicView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ViewFlipper;

import java.util.List;

public class MusicActivity extends AbstractActivity implements MusicView, MusicAdapter.OnAction {

    private static final int STATE_EMPTY = 1;
    private static final int STATE_RESULTS = 2;
    private static final String EXTRA_EVENT = "extra-event";
    private MusicPresenter mPresenter;
    private MusicAdapter mAdapter = new MusicAdapter();
    private Event mEvent;

    public static Intent getIntent(@NonNull final Context context, @NonNull final Event event) {
        final Intent intent = new Intent(context, MusicActivity.class);
        intent.putExtra(EXTRA_EVENT, event);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        mEvent = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);

        if (mEvent == null) {
            throw new AssertionError("Use the static factory method MusicActivity.getIntent()");
        }

        mPresenter = new MusicPresenter(mEvent.key);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MusicActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MusicActivity.this,
                DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetachView();
    }

    @Override
    protected boolean shouldValidate() {
        return true;
    }

    @Override
    public Context getContext() {
        return MusicActivity.this;
    }

    @Override
    public void showSongs(@NonNull List<Song> songs) {
        setState(STATE_RESULTS);
        mAdapter.setItems(songs, this);
    }

    @Override
    public void onError() {
        FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView), "Ups algo salió mal");
    }

    @Override
    public void onEmptyResults() {
        setState(STATE_EMPTY);
    }

    private void setState(final int state) {
        ((ViewFlipper) findViewById(R.id.viewFlipper)).setDisplayedChild(state);
    }

    @Override
    public void onVote(@NonNull Song song, final @SongViewHolder.VoteType int type) {
        final SharedPreferences prefs = getSharedPreferences(mEvent.key, MODE_PRIVATE);
        final String songName = song.getTema().replace(" ", "_");
        final String artistName = song.getArtista() == null ? "Desconocido" : song.getArtista().replace(" ", "_");
        final String key = songName.concat("-").concat(artistName);

        if (prefs.getBoolean(key, false)) {
            FeedbackUtils.displaySnackbarError(findViewById(R.id.rootView), "Ya has votado este canción");
        } else {
            mPresenter.vote(song, type);
        }
    }

    @Override
    public void onVoteSuccess(@NonNull final Song song) {
        final SharedPreferences prefs = getSharedPreferences(mEvent.key, MODE_PRIVATE);
        final String songName = song.getTema().replace(" ", "_");
        final String artistName = song.getArtista() == null ? "Desconocido" : song.getArtista().replace(" ", "_");
        final String key = songName.concat("-").concat(artistName);

        prefs.edit().putBoolean(key, true).apply();
    }
}
