package com.example.fangyuanzheng.schoolday;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link AddClassFrgament#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddClassFrgament extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText CourseEditTextView;
   static EditText StartTimeEditTextView;
    static String startTimeString;
    static String endTimeString;
    static String courseString;
    static String classclocationstring;
    static String [] weekday=new String[5];
    static EditText EndTimeEditTextView;
    EditText ClassLocationEditTextView;
    CheckBox Mon;
    CheckBox Tue;
    CheckBox Wed;
    CheckBox Thu;
    CheckBox Fri;
    Button SubmitButton;
    MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;
    static ClassData classData=new ClassData();
    public AddClassFrgament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddClassFrgament.
     */
    // TODO: Rename and change types and number of parameters
    public static AddClassFrgament newInstance(String param1, String param2) {
        AddClassFrgament fragment = new AddClassFrgament();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View CurrentView=inflater.inflate(R.layout.fragment_add_class,container, false);
        CourseEditTextView=(EditText)CurrentView.findViewById(R.id.Course_EditText);
        ClassLocationEditTextView=(EditText)CurrentView.findViewById(R.id.classLocation_EditText);
        StartTimeEditTextView=(EditText)CurrentView.findViewById(R.id.StartTime_EditText);
        StartTimeEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new TimePickerFragment();
                dialogFragment.show(getFragmentManager(),"timePicker");


            }
        });
        EndTimeEditTextView=(EditText)CurrentView.findViewById(R.id.EndTime_EditText);
        EndTimeEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new TimePicker2Fragment();
                dialogFragment.show(getFragmentManager(),"timePicker");
            }
        });
        Mon=(CheckBox)CurrentView.findViewById(R.id.Monday_CheckBox);
        Tue=(CheckBox)CurrentView.findViewById(R.id.Tuesday_CheckBox);
        Wed=(CheckBox)CurrentView.findViewById(R.id.Wed_CheckBox);
        Thu=(CheckBox)CurrentView.findViewById(R.id.Thur_CheckBox);
        Fri=(CheckBox)CurrentView.findViewById(R.id.Fri_CheckBox);
        SubmitButton=(Button)CurrentView.findViewById(R.id.SubmitButton);
       SubmitButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               courseString= CourseEditTextView.getText().toString();
               startTimeString=StartTimeEditTextView.getText().toString();
               endTimeString=EndTimeEditTextView.getText().toString();
               classclocationstring=ClassLocationEditTextView.getText().toString();
               if(!Mon.isChecked()&&!Tue.isChecked()&&!Wed.isChecked()&&!Thu.isChecked()&&!Fri.isChecked()){
                   Snackbar snackbar = Snackbar.make(Mon, "Please Select WeekDay", Snackbar.LENGTH_SHORT);
                   snackbar.show();
                   return;
               }
               if (courseString.isEmpty()){
                   Snackbar snackbar = Snackbar.make(CourseEditTextView, "Course can't be empty!", Snackbar.LENGTH_SHORT);
                   snackbar.show();
                   return;
               }
               if(startTimeString.isEmpty()||endTimeString.isEmpty()){
                   Snackbar snackbar = Snackbar.make(StartTimeEditTextView, "StartTime and EndTime can't be empty!", Snackbar.LENGTH_SHORT);
                   snackbar.show();
                   return;
               }
               HashMap newClass=new HashMap();
               newClass.put("id",courseString);
               newClass.put("name",courseString);
               newClass.put("starttime",startTimeString);
               newClass.put("endtime",endTimeString);
               newClass.put("classlocation",classclocationstring);
               if (Mon.isChecked()) {
                   weekday[0] = "Monday";
                   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Monday").getRef();
                   classData.addItemToServer(newClass,databaseReference);
               }
               if (Tue.isChecked()) {
                   weekday[1] = "Tuesday";
                   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Tuesday").getRef();
                   classData.addItemToServer(newClass,databaseReference);
               }
               if (Wed.isChecked()) {
                   weekday[2] = "Wednesday";
                   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Wednesday").getRef();
                   classData.addItemToServer(newClass,databaseReference);
               }
               if (Thu.isChecked()) {
                   weekday[3] = "Thursday";
                   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Thursday").getRef();
                   classData.addItemToServer(newClass,databaseReference);
               }
               if (Fri.isChecked()) {
                   weekday[4] = "Friday";
                   DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Friday").getRef();
                   classData.addItemToServer(newClass,databaseReference);
               }
               Snackbar snackbar = Snackbar.make(CourseEditTextView, courseString+" is added", Snackbar.LENGTH_SHORT);
               snackbar.show();
                return;
           }
       });

        return CurrentView;
    }


    // TODO: Rename method, update argument and hook method into UI event

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
           StartTimeEditTextView.setTextSize(18);
            StartTimeEditTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            StartTimeEditTextView.setText(hourOfDay+":"+minute);
           // timePicked=hourOfDay+":"+minute;
            startTimeString=hourOfDay+":"+minute;
        }
    }
    public static class TimePicker2Fragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
           EndTimeEditTextView.setTextSize(18);
            EndTimeEditTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            EndTimeEditTextView.setText(hourOfDay+":"+minute);
          //  timePicked=hourOfDay+":"+minute;
            endTimeString=hourOfDay+":"+minute;
        }
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
