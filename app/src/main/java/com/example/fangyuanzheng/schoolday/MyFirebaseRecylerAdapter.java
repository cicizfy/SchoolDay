package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/11/2017.
 */
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Classes, MyFirebaseRecylerAdapter.ClassScheduleViewHolder>  {
    private Context mContext;
    static RecycleItemClickListener itemClickListener;

    public interface RecycleItemClickListener{
        public void onItemClick(View v,int position);
        public void onItemLongClick(View v,int position);
        public void onOverflowMenuClick(View view, final int postion);
    }
    public MyFirebaseRecylerAdapter(Class<Classes> modelClass, int modelLayout,
                                    Class<ClassScheduleViewHolder> holder, Query ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;

    }
    public void setOnRecycleItemClickListener(final RecycleItemClickListener onItemClickListener){
        this.itemClickListener= onItemClickListener;
    }
    @Override
    protected void populateViewHolder(ClassScheduleViewHolder viewHolder, Classes model, int position) {
        String [] color={"#d7ffc132","#f8c5b293","#ecc3e156","#e2fffce6"};
        int bg=0;
        if (position>=4){
        bg=position%4;}else bg=position;
        viewHolder.cardView.setCardBackgroundColor(Color.parseColor(color[bg]));
        viewHolder.ClassNameTextView.setText(model.getId());
        viewHolder.ClassStartTimeTextView.setText(model.getClassStartTime());
        viewHolder.ClassEndTimeTextView.setText(model.getClassEndTime());
        viewHolder.ClassLocationTextView.setText(model.getClasslocation());
    }

    public static class ClassScheduleViewHolder extends RecyclerView.ViewHolder  {
        public CardView cardView;
        public TextView ClassNameTextView;
        public TextView ClassStartTimeTextView;
        public TextView ClassEndTimeTextView;
        public TextView ClassLocationTextView;
        ImageView dotsOption;
        public ClassScheduleViewHolder(View itemView) {
            super(itemView);
            cardView = (android.support.v7.widget.CardView) itemView.findViewById(R.id.CardView);
            ClassNameTextView=(TextView)itemView.findViewById(R.id.ClassNameTextView);
           ClassStartTimeTextView=(TextView)itemView.findViewById(R.id.ClassStartTimeTextView);
            ClassEndTimeTextView=(TextView)itemView.findViewById(R.id.ClassEndTimeTextView);
            ClassLocationTextView=(TextView)itemView.findViewById(R.id.ClassLocationTextView);
            dotsOption=(ImageView) itemView.findViewById(R.id.dotsImage);

            dotsOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        // if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        itemClickListener.onOverflowMenuClick(v, getAdapterPosition());
                        // }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemLongClick(v, getAdapterPosition());
                        }
                    }
                    return true;
                }
            });
        }
    }
}
