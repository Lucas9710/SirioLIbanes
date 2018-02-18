package remindme.android.com.sirioibanes.adapters.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.AbstractMap;

import remindme.android.com.sirioibanes.R;
import remindme.android.com.sirioibanes.dtos.Event;

public class EventViewHolder extends RecyclerView.ViewHolder {
    private final TextView titleView;
    private final TextView descriptionView;

    public EventViewHolder(@NonNull final View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.title);
        descriptionView = itemView.findViewById(R.id.description);
    }

    public void onBind(@NonNull final AbstractMap<String, Object> event) {
        titleView.setText((String) event.get("titulo"));
        descriptionView.setText((String) event.get("descripcion"));
    }
}
