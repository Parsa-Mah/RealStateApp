package com.example.realstate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.realstate.R;
import com.example.realstate.ShowPropertyActivity;
import com.example.realstate.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> locationList;
    private Context context;
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTextView;
    private int selectedPosition = -1;
    private static final int VIEW_TYPE_EMPTY, VIEW_TYPE_OCCUPIED;

    static {
        VIEW_TYPE_EMPTY = 1;
        VIEW_TYPE_OCCUPIED = 2;
    }

    public LocationAdapter(@NonNull List<Location> locationList) {
        if (locationList == null) {
            this.locationList = new ArrayList<>();
        } else {
            this.locationList = locationList;
        }
    }


    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);
        context = parent.getContext();
        Activity activity = (Activity) context;
        recyclerView = activity.findViewById(R.id.rc_location);
        emptyTextView = activity.findViewById(R.id.tv_empty);
        return new LocationViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else if (viewType == VIEW_TYPE_OCCUPIED) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            holder.bind(locationList.get(position));
            holder.itemView.setEnabled(true);
            holder.itemView.setClickable(true);
            holder.itemView.setBackgroundColor(context.getColor(R.color.primary_text));
            holder.itemView.setOnClickListener(v -> {
                holder.itemView.setEnabled(false);
                holder.itemView.setClickable(false);
                selectedPosition = position;
                if (selectedPosition == position) {
                    holder.itemView.setBackgroundColor(context.getColor(R.color.colorAccent));

                } else {
                    holder.itemView.setBackgroundColor(context.getColor(R.color.primary_text));
                }
                Intent intent = new Intent(context , ShowPropertyActivity.class);
                intent.putExtra("loc", locationList.get(position));
                context.startActivity(intent);
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    selectedPosition = -1;
                    notifyDataSetChanged();
                }, 100);

            });

        }

    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (locationList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_OCCUPIED;
        }
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView title, description;
        private AppCompatImageView avatar;

        LocationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            avatar = itemView.findViewById(R.id.imgv_avatar);
        }

        public void bind(Location location) {
            title.setText(location.getTitle());
            description.setText(location.getDescription());
            avatar.setImageResource(Integer.parseInt(location.getAvatarPath()));


        }
    }
}
