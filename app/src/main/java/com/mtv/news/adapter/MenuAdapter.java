package com.mtv.news.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtv.news.R;
import com.mtv.news.entity.Category;
import com.mtv.news.interfaces.AdapterListener;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter {

    private AdapterListener listener;

    private List<Category> categoryList;

    public MenuAdapter(List<Category> categoryList, AdapterListener listener){
        this.listener = listener;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.item_menu,null);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MenuViewHolder menuViewHolder = (MenuViewHolder) holder;
        final Category category = categoryList.get(position);

        String title = category.getTitle();
        menuViewHolder.tvMenu.setText(title);

        if(category.isSelected()){
            menuViewHolder.rlItemMenu.setBackgroundResource(R.color.colorPrimary);
        }else {
            menuViewHolder.rlItemMenu.setBackgroundResource(android.R.color.white);
        }

        menuViewHolder.rlItemMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClickListener(category,position,menuViewHolder);
                }
            }
        });

    }

    private class MenuViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlItemMenu;
        TextView tvMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            rlItemMenu = (RelativeLayout) itemView.findViewById(R.id.rl_item_menu);
            tvMenu = (TextView) itemView.findViewById(R.id.tv_menu);
        }
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
