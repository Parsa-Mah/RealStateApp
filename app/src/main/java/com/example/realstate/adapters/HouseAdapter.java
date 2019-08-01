package com.example.realstate.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.realstate.models.House;

import java.util.ArrayList;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {
    private List<House> houseList;
    private Context context;
    private RecyclerView recyclerView;
    private AppCompatTextView emptyTextView;
    private int selectedPosition = -1;
    private static final int VIEW_TYPE_EMPTY, VIEW_TYPE_OCCUPIED , DELAY;

    static {
        VIEW_TYPE_EMPTY = 1;
        VIEW_TYPE_OCCUPIED = 2;
        DELAY = 100;
    }

    public HouseAdapter(@NonNull List<House> houseList) {
        if (houseList == null) {
            this.houseList = new ArrayList<>();
        } else {
            this.houseList = houseList;
        }
    }


    @NonNull
    @Override
    public HouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_row, parent, false);
        context = parent.getContext();
        Activity activity = (Activity) context;
        recyclerView = activity.findViewById(R.id.rc_location);
        emptyTextView = activity.findViewById(R.id.tv_empty);
        return new HouseViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_EMPTY) {
            showEmptyView();
        } else if (viewType == VIEW_TYPE_OCCUPIED) {
            showRecyclerView();
            holder.bind(houseList.get(position));
            resetItemViewProperty(holder);
            itemViewSetOnClickListener(holder, position);

        }

    }

    private void itemViewSetOnClickListener(@NonNull HouseViewHolder holder, int position) {
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
            intent.putExtra("loc", houseList.get(position));
            context.startActivity(intent);
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                selectedPosition = -1;
                notifyDataSetChanged();
            }, DELAY);

        });
    }

    private void resetItemViewProperty(@NonNull HouseViewHolder holder) {
        holder.itemView.setEnabled(true);
        holder.itemView.setClickable(true);
        holder.itemView.setBackgroundColor(context.getColor(R.color.primary_text));
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        recyclerView.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return houseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (houseList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_OCCUPIED;
        }
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView title, description;
        private AppCompatImageView avatar;

        HouseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_description);
            avatar = itemView.findViewById(R.id.imgv_avatar);
        }

        public void bind(House house) {
            title.setText(house.getTitle());
            description.setText(house.getDescription());
            avatar.setImageResource(Integer.parseInt(house.getAvatarPath()));


        }
    }
}