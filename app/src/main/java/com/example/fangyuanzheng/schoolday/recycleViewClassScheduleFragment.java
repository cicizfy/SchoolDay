package com.example.fangyuanzheng.schoolday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.
 * Use the {@link recycleViewClassScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recycleViewClassScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String weekDay;
    private String mParam2;
    private static int Pageposition;
    static  int realtime=0;
    static  String day;
    private RecyclerView recyclerView;
    private LinearLayoutManager myLayoutManager;
    MyFirebaseRecylerAdapter.RecycleItemClickListener handleNavigationListener;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    static ClassData MonClassSchedule,TuesClassSchedule,WedClassSchedule,ThurClassSchedule,FriClassSchedule;
    static  MovieData movieData;
    public interface CustomOnClickListener{
        public void onClicked(View v);
    }
    private recycleViewClassScheduleFragment.CustomOnClickListener customOnClickListener;
    public recycleViewClassScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     *
     * @return A new instance of fragment recycleViewClassScheduleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recycleViewClassScheduleFragment newInstance(String weekDay, int p) {
        recycleViewClassScheduleFragment fragment = new recycleViewClassScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, weekDay);
        if (p>=0){
        Pageposition=p;}
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weekDay = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        setRetainInstance(true);
        DatabaseReference childRef= FirebaseDatabase.getInstance().getReference().child(weekDay).getRef();//weekDay
        View currentView= inflater.inflate(R.layout.fragment_recycle_view_class_schedule, container, false);
       Query childRefQuery=childRef.orderByChild("starttime");
        myFirebaseRecylerAdapter=new MyFirebaseRecylerAdapter(Classes.class,R.layout.cardview_class_firebase_adapter,MyFirebaseRecylerAdapter.ClassScheduleViewHolder.class,childRefQuery,getContext());

       if(Pageposition>-1)
        MonClassSchedule=new ClassData("Monday");
        TuesClassSchedule=new ClassData("Tuesday");
        WedClassSchedule=new ClassData("Wednesday");
        ThurClassSchedule=new ClassData("Thursday");
        FriClassSchedule=new ClassData("Friday");
        movieData=new MovieData();
        recyclerView=(RecyclerView) currentView.findViewById(R.id.ClassCardList);
        recyclerView.setHasFixedSize(true);
        myLayoutManager=new LinearLayoutManager(getActivity());//currentView.getContext()
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(myFirebaseRecylerAdapter);
        if (MonClassSchedule.getSize() == 0) {
            MonClassSchedule.setAdapter(myFirebaseRecylerAdapter);
            MonClassSchedule.setContext(getActivity());//getApplicationContext()-activity is used
            MonClassSchedule.initializeDataFromCloud();
        }
        if (TuesClassSchedule.getSize() == 0) {
            TuesClassSchedule.setAdapter(myFirebaseRecylerAdapter);
            TuesClassSchedule.setContext(getActivity());//getApplicationContext()-activity is used
            TuesClassSchedule.initializeDataFromCloud();
        }
        if (WedClassSchedule.getSize() == 0) {
            WedClassSchedule.setAdapter(myFirebaseRecylerAdapter);
            WedClassSchedule.setContext(getActivity());//getApplicationContext()-activity is used
            WedClassSchedule.initializeDataFromCloud();
        }
        if (ThurClassSchedule.getSize() == 0) {

            ThurClassSchedule.setAdapter(myFirebaseRecylerAdapter);
            ThurClassSchedule.setContext(getActivity());//getApplicationContext()-activity is used
            ThurClassSchedule.initializeDataFromCloud();
        }
        if (FriClassSchedule.getSize() == 0) {
            FriClassSchedule.setAdapter(myFirebaseRecylerAdapter);
            FriClassSchedule.setContext(getActivity());//getApplicationContext()-activity is used
            FriClassSchedule.initializeDataFromCloud();
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myFirebaseRecylerAdapter.setOnRecycleItemClickListener(new MyFirebaseRecylerAdapter.RecycleItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }

            @Override
            public void onOverflowMenuClick(View view, final int postion) {
                PopupMenu popupMenu=new PopupMenu(getActivity(),view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                        case R.id.action_delete:
                        Toast.makeText(getActivity(), "Delete " + Pageposition, Toast.LENGTH_SHORT).show();
                                HashMap deleteclass;
                               if (Pageposition==0) {
                                   sort(MonClassSchedule);
                                   if (MonClassSchedule.classesList.size()>0){

                                   deleteclass = (HashMap) ((HashMap) MonClassSchedule.getItem(postion)).clone();
                                      MonClassSchedule.removeItemFromServer(deleteclass);
                                     myFirebaseRecylerAdapter.notifyItemRemoved(postion);}
                                   return true;
                               }
                              else if (Pageposition==1) {
                                   sort(TuesClassSchedule);
                                   if (TuesClassSchedule.classesList.size()>0){
                                   deleteclass = (HashMap) ((HashMap) TuesClassSchedule.getItem(postion)).clone();
                                       TuesClassSchedule.removeItemFromServer(deleteclass);
                                       myFirebaseRecylerAdapter.notifyItemRemoved(postion);}
                                       return true;
                               }
                              else if (Pageposition==2) {
                                   sort(WedClassSchedule);
                                   if (WedClassSchedule.classesList.size()>0){
                                   deleteclass = (HashMap) ((HashMap) WedClassSchedule.getItem(postion)).clone();
                                       WedClassSchedule.removeItemFromServer(deleteclass);
                                       myFirebaseRecylerAdapter.notifyItemRemoved(postion);}
                                   return true;
                              }
                               else if (Pageposition==3) {
                                   sort(ThurClassSchedule);
                                   if (ThurClassSchedule.classesList.size()>0){
                                   deleteclass = (HashMap) ((HashMap) ThurClassSchedule.getItem(postion)).clone();
                                       ThurClassSchedule.removeItemFromServer(deleteclass);
                                       myFirebaseRecylerAdapter.notifyItemRemoved(postion);}
                                       return true;
                               }
                               else if (Pageposition==4) {
                                   sort(FriClassSchedule);
                                   if (FriClassSchedule.classesList.size()>0) {
                                       deleteclass = (HashMap) ((HashMap) FriClassSchedule.getItem(postion)).clone();
                                       FriClassSchedule.removeItemFromServer(deleteclass);
                                       myFirebaseRecylerAdapter.notifyItemRemoved(postion);}
                                       return true;
                               }
                            return false;
                     }

                        return false;
                    }

                });
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.dopts_menu,popupMenu.getMenu());
                popupMenu.show();
            }
        });

        return currentView;
    }
   public void sort(ClassData classdata){
       Collections.sort(classdata.getMoviesList(), new Comparator<Map<String, ?>>() {
           @Override
           public int compare(Map<String, ?> o1, Map<String, ?> o2) {
               // int n = Integer.parseInt(String.valueOf(o1.get("year")));
               //int m = Integer.parseInt(String.valueOf(o2.get("year")));
               String n=o1.get("starttime").toString();
               return n.compareTo(o2.get("starttime").toString());
           }

       });
   }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu,menuInflater);
        //menu.add("test").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // if(menu.findItem(R.id.action_search)==null)
        //getActivity().getActionBar().setTitle("Class Schedule");
        menuInflater.inflate(R.menu.note_activity_menu,menu);
        MenuItem AddClass =menu.findItem(R.id.action_AddClass);
       // Toast.makeText(getActivity(), "Add " + Pageposition, Toast.LENGTH_SHORT).show();
        //menu.getItem(0).setVisible(false);
        //menu.getItem(2).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
