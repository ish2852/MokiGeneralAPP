package com.example.orderspot_general.service.Adepter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.MenuVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuRecommendationRecyclerViewAdepter extends RecyclerView.Adapter<MenuRecommendationRecyclerViewAdepter.ViewHolder> {
    List<MenuVO> items;

    public MenuRecommendationRecyclerViewAdepter(List<MenuVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MenuRecommendationRecyclerViewAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recommendation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuRecommendationRecyclerViewAdepter.ViewHolder viewHolder, final int position) {

        MenuVO menuVO = items.get(position);
        viewHolder.productName.setText(menuVO.getName());
        Picasso.get().load(Resources.getSystem().getString(R.string.s3_bucket_name) + menuVO.getImageName()).into(viewHolder.productImage);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<MenuVO> items) {
        this.items = items;
    }

    public List<MenuVO> getMenulist() {
        return items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public ImageView productImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
