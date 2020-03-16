package com.example.orderspot_general.service.Adepter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.MenuVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MenuListRecyclerViewAdepter extends RecyclerView.Adapter<MenuListRecyclerViewAdepter.ViewHolder> {
    List<MenuVO> items;

    public MenuListRecyclerViewAdepter(List<MenuVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public MenuListRecyclerViewAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuListRecyclerViewAdepter.ViewHolder viewHolder, final int position) {

        MenuVO menuVO = items.get(position);
        viewHolder.productName.setText(menuVO.getName());

        if (menuVO.getPrice() != 0) {
            viewHolder.productPrice.setText(Integer.toString(menuVO.getPrice()));
        }else{
            viewHolder.productPrice.setVisibility(View.GONE);
        }
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
        public TextView productPrice;
        public ImageView productImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    MenuVO menuVO = items.get(position);

                    if (menuVO.getAmount() == 0) {
                        menuVO.setAmount(1);
                        itemView.setAlpha(0.8f);
                    } else {
                        menuVO.setAmount(0);
                        itemView.setAlpha(1f);
                    }
                }
            });

        }
    }
}
