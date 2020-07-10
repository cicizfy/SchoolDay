package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/16/2017.
 */
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
public class NoteFirebaseAdapter extends FirebaseRecyclerAdapter<Notes, NoteFirebaseAdapter.ClassScheduleViewHolder>  {
    private Context mContext;
    static RecycleItemClickListener itemClickListener;
    private int lastPosition=-1;

    public interface RecycleItemClickListener{
        public void onItemClick(View v,int position);
        public void onItemLongClick(View v,int position);
        public void onOverflowMenuClick(View view, final int postion);
    }
    public NoteFirebaseAdapter(Class<Notes> modelClass, int modelLayout,
                                    Class<ClassScheduleViewHolder> holder, Query ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;

    }
    public void setOnRecycleItemClickListener(final RecycleItemClickListener onItemClickListener){
        this.itemClickListener= onItemClickListener;
    }
    @Override
    protected void populateViewHolder(ClassScheduleViewHolder viewHolder, Notes model, int position) {
        //String [] color={"#d7ffc132","#f8c5b293","#ecc3e156","#e2fffce6"};
        //int bg=0;
        //if (position>=4){
          //  bg=position%4;}else bg=position;
       // viewHolder.cardView.setCardBackgroundColor(Color.parseColor("#78f3f6cb"));
        viewHolder.DateTextView.setText(model.getDate());
        setAnimation(viewHolder.itemView,position);

        String tiltle=model.getName();
        if (tiltle.length()>25)
            tiltle= tiltle.substring(0,25)+"...";
        viewHolder.TitleTimeTextView.setText(tiltle);
        String content=model.getNoteContent();
        if (content.length()>230)
       content= content.substring(0,230)+"...";
        viewHolder.IntroductionTextView.setText(content);
        //viewHolder.ClassLocationTextView.setText(model.getClasslocation());
    }
    private void setAnimation(View viewToAnimate, int position){
        if(position>lastPosition)
        {
            Animation animation= AnimationUtils.loadAnimation(mContext,android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition=position;
        }
    }

    public static class ClassScheduleViewHolder extends RecyclerView.ViewHolder  {
        public CardView cardView;
        public TextView DateTextView;
        public TextView TitleTimeTextView;
        public TextView IntroductionTextView;
        public TextView ClassLocationTextView;
        ImageView dotsOption;
        public ClassScheduleViewHolder(View itemView) {
            super(itemView);
            cardView = (android.support.v7.widget.CardView) itemView.findViewById(R.id.CardView);
            DateTextView=(TextView)itemView.findViewById(R.id.Note_SharedDate);
            TitleTimeTextView=(TextView)itemView.findViewById(R.id.Note_SharedTitle);
            IntroductionTextView=(TextView)itemView.findViewById(R.id.Note_SharedIntroduction);

            /*dotsOption=(ImageView) itemView.findViewById(R.id.dotsImage);

            dotsOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        // if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        itemClickListener.onOverflowMenuClick(v, getAdapterPosition());
                        // }
                    }
                }
            });*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            itemClickListener.onItemClick(v, getAdapterPosition());
                        }
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