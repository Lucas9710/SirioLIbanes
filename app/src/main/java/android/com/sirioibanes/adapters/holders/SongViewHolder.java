package android.com.sirioibanes.adapters.holders;

import android.com.sirioibanes.R;
import android.com.sirioibanes.dtos.Song;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SongViewHolder extends RecyclerView.ViewHolder {

    public static final int VOTE_DOWN = 0;
    public static final int VOTE_UP = 1;

    @IntDef({VOTE_DOWN, VOTE_UP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VoteType {
    }

    public SongViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(@NonNull final Song song) {
        final String artist = song.getArtista() == null ? "Desconocido" : song.getArtista();
//
//        ((TextView) itemView.findViewById(R.id.artistView)).setText(artist);
//        ((TextView) itemView.findViewById(R.id.songView)).setText(song.getTema());
//        ((TextView) itemView.findViewById(R.id.counterView)).setText(String.valueOf(song.getVotos()));
    }
}
