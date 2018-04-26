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

//ARRIBA DE ESTAN LAS DEPENDENCIAS DE ESTA CLASE

public class SongViewHolder extends RecyclerView.ViewHolder {

    //VARIABLES
    public static final int VOTE_DOWN = 0;
    public static final int VOTE_UP = 1;
    private final TextView artistLabel;

    @IntDef({VOTE_DOWN, VOTE_UP})
    @Retention(RetentionPolicy.SOURCE)


    //METODOS
    public @interface VoteType {
    }

    public SongViewHolder(View itemView) {
        //este metodo se ejecuta al inicializar de la celda
        super(itemView);
        artistLabel = (TextView) itemView.findViewById(R.id.artistLabel);
    }

    public void onBind(@NonNull final Song song) {
        //este metodo se ejecuta cuando queremos impactar la informacion de una cancion
        String artistText = "Artista desconocido";
        if (song.artista != null) {
            artistText = song.artista;
        }
        artistLabel.setText(artistText);
    }
}
