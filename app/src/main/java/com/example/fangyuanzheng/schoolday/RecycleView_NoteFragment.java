package com.example.fangyuanzheng.schoolday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RecycleView_NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecycleView_NoteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager myLayoutManager;
    NoteFirebaseAdapter myFirebaseRecylerAdapter;

  //  MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    static ClassData MonClassSchedule;
    static NoteData noteData;
    public interface CustomOnClickListener{
        public void onClicked(View v);
    }
    private RecycleView_NoteFragment.CustomOnClickListener customOnClickListener;

    public RecycleView_NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecycleView_NoteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecycleView_NoteFragment newInstance(String param1, String param2) {
        RecycleView_NoteFragment fragment = new RecycleView_NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        setRetainInstance(true);
        DatabaseReference childRef= FirebaseDatabase.getInstance().getReference().child("notedata").getRef();//weekDay
        View currentView= inflater.inflate(R.layout.fragment_recycle_view__note, container, false);
        myFirebaseRecylerAdapter=new NoteFirebaseAdapter(Notes.class,R.layout.cardview_note_fireadapter,NoteFirebaseAdapter.ClassScheduleViewHolder.class,childRef,getContext());

        noteData=new NoteData();

        recyclerView=(RecyclerView) currentView.findViewById(R.id.NoteCardList);
        recyclerView.setHasFixedSize(true);
        myLayoutManager=new LinearLayoutManager(getActivity());//currentView.getContext()
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(myFirebaseRecylerAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        floatingActionButton=(FloatingActionButton)currentView.findViewById(R.id.Floatbutton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                NewNoteFargment newNoteFargment =new NewNoteFargment();
                newNoteFargment.setEnterTransition(new Fade());
                newNoteFargment.setExitTransition(new Slide(Gravity.RIGHT));
                fragmentTransaction.replace(R.id.NoteActivityLayout_continer,newNoteFargment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
       if (noteData.getSize() == 0) {
            noteData.setAdapter(myFirebaseRecylerAdapter);
            noteData.setContext(getActivity());//getApplicationContext()-activity is used
            noteData.initializeDataFromCloud();
        }

        myFirebaseRecylerAdapter.setOnRecycleItemClickListener(new NoteFirebaseAdapter.RecycleItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                String imageTransitionName = "";
                String textTransitionName = "";
                String datetextTransitionName="";
                ImageView imageView=(ImageView) v.findViewById(R.id.Note_SharedImage);
                TextView dateText=(TextView) v.findViewById(R.id.Note_SharedDate);
                TextView titleText=(TextView) v.findViewById(R.id.Note_SharedTitle);
                NoteDetailFragment noteDetailFragment=new NoteDetailFragment();
                noteDetailFragment=noteDetailFragment.newInstance(position,(HashMap)noteData.getItem(position));
                noteDetailFragment=noteDetailFragment.newInstance(position,noteData.getItem(position));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    imageTransitionName = imageView.getTransitionName();
                    datetextTransitionName = dateText.getTransitionName();
                    textTransitionName=titleText.getTransitionName();
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addSharedElement(imageView, imageTransitionName);
                fragmentTransaction.addSharedElement(dateText,datetextTransitionName);
                fragmentTransaction.addSharedElement(titleText,textTransitionName);
                fragmentTransaction.replace(R.id.NoteActivityLayout_continer, noteDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onItemLongClick(View v, int position) {
                getActivity().startActionMode(new ActionBarCallBack(position));
            }

            @Override
            public void onOverflowMenuClick(View view, int postion) {

            }
        });
        return currentView;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu,menuInflater);
        //menu.add("test").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // if(menu.findItem(R.id.action_search)==null)
        //getActivity().getActionBar().setTitle("Class Schedule");
        menuInflater.inflate(R.menu.menu_note,menu);
        MenuItem AddClass =menu.findItem(R.id.action_AddNote);
        // Toast.makeText(getActivity(), "Add " + Pageposition, Toast.LENGTH_SHORT).show();
        if(menu.findItem(R.id.action_search)==null)
            menuInflater.inflate(R.menu.menu,menu);

        //menu.getItem(1).setVisible(false);
        //menu.getItem(2).setVisible(false);

        SearchView search =(SearchView) menu.findItem(R.id.action_search).getActionView();
        //search.setMaxWidth(200);
        search.onWindowFocusChanged(true);
        if(search!=null) {
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    int i = 0;
                  //  if (query.length()==0)return true;
                    String name=noteData.getItem(i).get("name").toString();
                    while (i<noteData.getSize()&&(query.compareToIgnoreCase(name)!=0)) {
                        i++;
                        if(i>=noteData.getSize())break;
                        name=noteData.getItem(i).get("name").toString();
                    }
                    if(i>=1&&i<noteData.getSize())
                        recyclerView.scrollToPosition(i);
                    else
                        Toast.makeText(getActivity(),"no Find",Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });

        }


    }

    class ActionBarCallBack implements ActionMode.Callback {
        int position;
        public ActionBarCallBack(int position) {
            this.position=position;
        }
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu){
            mode.getMenuInflater().inflate (R.menu.longpress_menu,menu ) ;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            HashMap movie =(HashMap)noteData.getItem(position);
            mode.setTitle((String)movie.get("name"));
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.action_delete:
                    Toast.makeText(getActivity(), "Delete " + position, Toast.LENGTH_SHORT).show();
                    HashMap Item=noteData.getItem(position);
                    noteData.removeItemFromServer(Item);
                    mode.finish();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
