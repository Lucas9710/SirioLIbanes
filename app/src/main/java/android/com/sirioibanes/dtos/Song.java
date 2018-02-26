package android.com.sirioibanes.dtos;

import android.com.sirioibanes.adapters.holders.SongViewHolder;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class Song {
    private final String artista;
    private final String tema;
    private Long votos;

    public Song(HashMap<String, Object> map) {
        this.artista = (String) map.get("artista");
        this.tema = (String) map.get("tema");
        this.votos = (Long) map.get("votos");
    }

    public String getArtista() {
        return artista;
    }

    public String getTema() {
        return tema;
    }

    public int getVotos() {
        return votos.intValue();
    }

    public void vote(final int type) {
        if (type == SongViewHolder.VOTE_UP) {
            this.votos++;
        } else {
            this.votos--;
        }
    }
}
