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
    private final TextView cancionLabel;
    private final TextView userLabel;
    private final TextView votosLabel;

    @IntDef({VOTE_DOWN, VOTE_UP})
    @Retention(RetentionPolicy.SOURCE)


    //METODOS
    public @interface VoteType {
    }

    public SongViewHolder(View itemView) {
        //este metodo se ejecuta al inicializar de la celda
        super(itemView);
        artistLabel = (TextView) itemView.findViewById(R.id.artistLabel);
        cancionLabel = (TextView) itemView.findViewById(R.id.songLabel);
        userLabel = (TextView) itemView.findViewById(R.id.userLabel);
        votosLabel = (TextView) itemView.findViewById(R.id.voteLabel);
    }

    public void onBind(@NonNull final Song song) {
        //este metodo se ejecuta cuando queremos impactar la informacion de una cancion


        //ARTISTA
        String artistText;
        if (song.artista == null) {
            artistText = "Artista desconocido";
        } else {
            artistText = song.artista;
        }
        artistLabel.setText(artistText);

        //CANCION
        String cancionText;
        if (song.tema == null) {
            cancionText = "Cancion Desconocido";
        } else {
            cancionText = song.tema;
        }
        cancionLabel.setText(cancionText);

        //USER
        String userText;
        if (song.user == null) {
            userText = "Usuario Desconocido";
        } else {
            userText = song.user;
        }
        userLabel.setText(userText);

        //VOTE
        String voteText;
        if (song.votos == null) {
            voteText = "0";
        } else {
            String strLong = Long.toString(song.votos);
            //song.votos = 1 -> NO FUNCIONA
            //strLong = "1" -> SI FUNCIONA
            voteText = strLong;
        }
        votosLabel.setText(voteText);

    }
}


