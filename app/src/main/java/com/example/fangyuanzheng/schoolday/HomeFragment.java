package com.example.fangyuanzheng.schoolday;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static  int ARG_Drawable = R.drawable.na;
    private static final  String ARG_DrawableString ="Drawable";
    private static final String ARG_Temp = "param2";
    private static final String ARG_Units = "paramUnit";
    private static final String ARG_Description = "param3";
    private static final String ARG_Location = "param4";

    // TODO: Rename and change types of parameters
    private String DrawableParam;
    private String TempParam;
    private String UnitsParam;
    private String DescriptionParam;
    private String LocationParam;

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    Drawable weatherIconDrawable;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int WeatherImageViewDrawable,String Temperature,String Unit, String Description,String Location ) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        ARG_Drawable=WeatherImageViewDrawable;
        args.putInt(ARG_DrawableString,WeatherImageViewDrawable);
        args.putString(ARG_Temp, Temperature);
        args.putString(ARG_Units,Unit);
        args.putString(ARG_Description, Description);
        args.putString(ARG_Location, Location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            DescriptionParam = getArguments().getString(ARG_DrawableString);
            TempParam = getArguments().getString(ARG_Temp);
            UnitsParam= getArguments().getString(ARG_Units);
            DescriptionParam = getArguments().getString(ARG_Description);
            LocationParam = getArguments().getString(ARG_Location);
            weatherIconDrawable=getResources().getDrawable(ARG_Drawable);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHomeView= inflater.inflate(R.layout.fragment_home, container, false);
        weatherIconImageView=(ImageView)fragmentHomeView.findViewById(R.id.weatherIconImageView);
        temperatureTextView=(TextView)fragmentHomeView.findViewById(R.id.temperatureTextView);
        conditionTextView=(TextView)fragmentHomeView.findViewById(R.id.conditionTextView);
        locationTextView=(TextView)fragmentHomeView.findViewById(R.id.locationTextView);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(TempParam+"\u00B0"+UnitsParam);
        conditionTextView.setText(DescriptionParam);
        locationTextView.setText("Syracuse, NY");

        return fragmentHomeView;
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
