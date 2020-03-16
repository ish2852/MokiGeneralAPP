package com.example.orderspot_general.service.Adepter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderspot_general.R;
import com.example.orderspot_general.domain.MenuVO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderListRecyclerViewAdepter extends RecyclerView.Adapter<OrderListRecyclerViewAdepter.ViewHolder> {
    List<MenuVO> items;

    public OrderListRecyclerViewAdepter(List<MenuVO> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderListRecyclerViewAdepter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListRecyclerViewAdepter.ViewHolder viewHolder, final int position) {

        MenuVO menuVO = items.get(position);
        viewHolder.productName.setText(menuVO.getName());
        viewHolder.productPrice.setText(Integer.toString(menuVO.getPrice()));
        viewHolder.productAmountTextView.setText(Integer.toString(menuVO.getAmount()));
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
        public TextView productAmountTextView;
        public Button minusButton;
        public Button plusButton;
        public ImageView productImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productAmountTextView = itemView.findViewById(R.id.productAmountTextView);
            minusButton = itemView.findViewById(R.id.minusButton);
            plusButton = itemView.findViewById(R.id.plusButton);
            productImage = itemView.findViewById(R.id.productImage);

            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuVO menuVO = items.get(getAdapterPosition());
                    int amount = menuVO.getAmount();
                    if (amount > 0) {
                        amount--;
                        menuVO.setAmount(amount);
                        productAmountTextView.setText("수량 : " + amount);
                    }
                }
            });

            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuVO menuVO = items.get(getAdapterPosition());
                    int amount = menuVO.getAmount() + 1;
                    menuVO.setAmount(amount);
                    productAmountTextView.setText("수량 : " + amount);
                }
            });

        }
    }
}
