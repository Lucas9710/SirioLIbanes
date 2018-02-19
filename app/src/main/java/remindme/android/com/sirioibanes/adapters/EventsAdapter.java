package remindme.android.com.sirioibanes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.adapters.holders.EventViewHolder;
import remindme.android.com.sirioibanes.dtos.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final List<AbstractMap<String, Object>> events = new ArrayList<>();
    private EventClickListener mListener;

    public EventsAdapter(@NonNull final EventClickListener clickListener) {
        mListener = clickListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new EventViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false));
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.holder_event;
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, final int position) {
        holder.onBind(events.get(holder.getAdapterPosition()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AbstractMap<String, Object> eventObject = events.get(holder.getAdapterPosition());
                final Event event = new Event(eventObject);

                mListener.onClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setItems(@NonNull final List<AbstractMap<String, Object>> items) {
        this.events.clear();
        this.events.addAll(items);

        notifyDataSetChanged();
    }

    public interface EventClickListener {
        void onClick(@NonNull Event event);
    }
}
