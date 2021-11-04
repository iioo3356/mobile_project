package com.example.homework_kimhayeon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    public interface OnItemClickEventListener{
        void onItemClick(int position);
    }
    private ArrayList<ViewItem> itemList;
    private ArrayList<ViewItem> removeList = new ArrayList<>();
    private OnItemClickEventListener itemClickListener = new OnItemClickEventListener() {
        @Override
        public void onItemClick(int position) {
            notifyItemChanged(checkedPosition, null);
            checkedPosition = position;
            notifyItemChanged(position, null);
        }
    };
    private int checkedPosition = -1;
    public RecyclerViewAdapter(ArrayList<ViewItem> items) {
        itemList = items;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_item, viewGroup, false);
        return new RecyclerViewHolder(view, itemClickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int position) {
        final ViewItem item = itemList.get(position);
        final int pos = position;
        viewHolder.tv_num.setChecked(itemList.get(position).getSelected());
        viewHolder.tv_num.setTag(itemList.get(position));
        viewHolder.tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                ViewItem vi = (ViewItem) cb.getTag();
                vi.setSelected(cb.isChecked());
                itemList.get(pos).setSelected(cb.isChecked());
                if (cb.isChecked()){
                    addRemoveList((ViewItem) cb.getTag());
                }
                else{
                    deleteRemoveList((ViewItem) cb.getTag());
                }
            }
        });
        viewHolder.tv_name.setText(item.getName());
        viewHolder.tv_num.setText(Integer.toString(item.getNum()));
        viewHolder.tv_price.setText(Integer.toString(item.getPrice()) + " 원");
        viewHolder.iv_icon.setImageURI(item.srcUri);
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public ArrayList<ViewItem> getRemoveItems() {
        return removeList;
    }

    public void setRemoveList(ArrayList<ViewItem> items){
        removeList = items;
    }

    public void addRemoveList(ViewItem item){
        removeList.add(item);
    }
    public void deleteRemoveList(ViewItem item){
        removeList.remove(item);
    }

    public ViewItem getSelected(){
        if (checkedPosition > -1){
            return itemList.get(checkedPosition);
        }
        return null;
    }
    public void clearSelected(){
        checkedPosition = -1;
    }
}
