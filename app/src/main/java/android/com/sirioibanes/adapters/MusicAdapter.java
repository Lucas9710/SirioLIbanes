package android.com.sirioibanes.adapters;

import android.com.sirioibanes.R;
import android.com.sirioibanes.adapters.holders.SongViewHolder;
import android.com.sirioibanes.dtos.Song;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private final List<Song> mSongs = new ArrayList<>();
    private OnAction mListener;

    public interface OnAction {
        void onVote(@NonNull final Song song, final @SongViewHolder.VoteType int type);
    }

    @Override
    public SongViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new SongViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final SongViewHolder holder, final int position) {
        holder.onBind(mSongs.get(holder.getAdapterPosition()));

        holder.itemView.findViewById(R.id.buttonVoteUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mListener.onVote(mSongs.get(holder.getAdapterPosition()), SongViewHolder.VOTE_UP);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void setItems(@NonNull final List<Song> songs, @NonNull final OnAction listener) {
        mListener = listener;
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.holder_song;
    }
}
