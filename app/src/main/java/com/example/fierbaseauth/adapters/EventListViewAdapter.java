package com.example.fierbaseauth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fierbaseauth.R;
import com.example.fierbaseauth.firebase.entities.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListViewAdapter extends RecyclerView.Adapter<EventListViewAdapter.ViewHolder> {

    private ArrayList<Event> events;
    private LayoutInflater inflater;
    private OnEventItemClickListener eventItemClickListener;

    public EventListViewAdapter(Context context, ArrayList<Event> events, OnEventItemClickListener eventItemClickListener){
        this.inflater = LayoutInflater.from(context);
        this.events = events;
        this.eventItemClickListener = eventItemClickListener;
    }

    @NonNull
    @Override
    public EventListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new ViewHolder(view, eventItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);

        holder.title.setText(event.title);
        holder.author.setText(event.createdBy);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, author;
        OnEventItemClickListener eventItemClickListener;
        public ViewHolder(@NonNull View itemView, OnEventItemClickListener eventItemClickListener) {
            super(itemView);
            this.eventItemClickListener = eventItemClickListener;
            title = itemView.findViewById(R.id.tv_title);
            author = itemView.findViewById(R.id.tv_byWho);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eventItemClickListener.onEventClick(view, events.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface OnEventItemClickListener{
        void onEventClick(View view, Event event, int itemPos);
    }
}
