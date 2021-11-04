package com.example.homework_kimhayeon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    CheckBox tv_num;
    TextView tv_name;
    ImageView iv_icon;
    TextView tv_price;

    public RecyclerViewHolder(View itemView, final RecyclerViewAdapter.OnItemClickEventListener itemClickListener) {
        super(itemView);
        tv_num = itemView.findViewById(R.id.tv_num);
        tv_name = itemView.findViewById(R.id.tv_name);
        iv_icon = itemView.findViewById(R.id.iv_icon);
        tv_price = itemView.findViewById(R.id.tv_price);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClick(position);
                }
            }
        });

    }
}