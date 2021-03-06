package com.example.fangyuanzheng.schoolday;

/**
 * Created by Fangyuan Zheng on 4/11/2017.
 */
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class ClassData {
    List<Map<String,?>> classesList;
    DatabaseReference mRef;
    Query mRefQuery;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    Context mContext;
    public void setAdapter(MyFirebaseRecylerAdapter mAdapter) {
        myFirebaseRecylerAdapter = mAdapter;
    }
    public void removeItemFromServer(Map<String,?> classes) {
        if (classes != null) {
            String id = (String) classes.get("id");
           // mRefQuery=mRef.orderByChild("starttime");
            //mRef=mRefQuery.getRef();
            mRef.child(id).removeValue();
        }
    }
    public void addItemToServer(Map<String,?> classes,DatabaseReference databaseReference){
        if(classes!=null){
            String id = (String) classes.get("id");
            databaseReference.child(id).setValue(classes);
        }
    }
    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}
    public List<Map<String, ?>> getMoviesList() {
        return classesList;
    }

    public int getSize(){
        return classesList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < classesList.size()){
            return (HashMap) classesList.get(i);
        } else return null;
    }
    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<classesList.size();i++){
            HashMap movie = (HashMap)classesList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            classesList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }


    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<classesList.size();i++){
            HashMap movie = (HashMap)classesList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        classesList.add(insertPosition,item);


        // Toast.makeText(mContext, "Item added:" + id, Toast.LENGTH_SHORT).show();

    }
    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<classesList.size();i++){
            HashMap movie = (HashMap)classesList.get(i);
            String mid = (String)movie.get("id");
            if(mid.equals(id)){
                classesList.remove(i);
                classesList.add(i,item);
                Log.d("My Test: NotifyChanged",id);

                break;
            }
        }


    }
    public void initializeDataFromCloud() {
        classesList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildAdded", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(movie);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Log.d("MyTest: OnChildChanged", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(movie);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Log.d("MyTest: OnChildRemoved", dataSnapshot.toString());
                HashMap<String,String> movie = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(movie);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public ClassData(){

        classesList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Monday").getRef();

        myFirebaseRecylerAdapter = null;
        mContext = null;

    }
    public ClassData(String weekday){
        classesList = new ArrayList<Map<String,?>>();

        mRef = FirebaseDatabase.getInstance().getReference().child(weekday).getRef();

        myFirebaseRecylerAdapter = null;
        mContext = null;
    }

    public int findFirst(String query){

        for(int i=0;i<classesList.size();i++){
            HashMap hm = (HashMap)getMoviesList().get(i);
            if(((String)hm.get("name")).toLowerCase().contains(query.toLowerCase())){
                return i;
            }
        }
        return 0;

    }
}
