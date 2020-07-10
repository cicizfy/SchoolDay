package com.example.fangyuanzheng.schoolday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;

import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.transition.Fade;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.ui.BaseHelper;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.example.fangyuanzheng.schoolday.data.Channel;
import com.example.fangyuanzheng.schoolday.data.Item;
import com.example.fangyuanzheng.schoolday.service.WeatherServiceCallback;
import com.example.fangyuanzheng.schoolday.service.YahooWeatherService;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, WeatherServiceCallback {
    private static DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    String msg ="SchoolDay : ";
    private YahooWeatherService service;
    private ProgressDialog dialog;
    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    static private TextView TimetextView;
    private  TextView DateTextView;
    private  TextView NavMyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        weatherIconImageView=(ImageView)findViewById(R.id.weatherIconImageView);
        temperatureTextView=(TextView)findViewById(R.id.temperatureTextView);
        conditionTextView=(TextView)findViewById(R.id.conditionTextView);
        locationTextView=(TextView)findViewById(R.id.locationTextView);

        TimetextView =(TextView)findViewById(R.id.TimetextView);
        DateTextView=(TextView)findViewById(R.id.DateTextView);

      //  String  currentTime= DateFormat.getTimeInstance().format(new Date());
        Thread timerthread=null;

        Runnable runnable=new CountDownRunner();
        timerthread=new Thread(runnable);
        timerthread.start();
        String currentDate=DateFormat.getDateInstance().format(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEEE");
        String WeekDay=simpleDateFormat.format(new Date());
       // TimetextView.setText(currentTime);
        DateTextView.setText(currentDate);



        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email="";
        if (user.getEmail()!=null){
            email=user.getEmail();}
        View headerView=(View) navigationView.getHeaderView(0);
        NavMyEmail=(TextView)headerView.findViewById(R.id.NavMyEmail);
        NavMyEmail.setText(email);
        service=new YahooWeatherService(this);
      // dialog=new ProgressDialog(this);
        //dialog.setMessage("loading...");
        //dialog.show();
        service.refreshWeather("Syracuse, NY");
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);



    }
    @Override
    protected void onStart(){
        super .onStart();
        Log.d(msg,"onStart() event");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(msg,"onResume() event");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.d(msg,"onPause() event");
    }

    @Override
    protected  void onStop(){
        super.onStop();
        Log.d(msg,"onStop() event");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(msg,"onDestroy() event");
    }
    @Override
    public void onBackPressed(){
        DrawerLayout mDrawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
    return true;

    }
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){
            case R.id.nav_home:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                 intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                return true;
            case  R.id.nav_classSchedule:
                 intent=new Intent(this,ClassScheduleActivity.class);
                startActivity(intent);
                return true;
            case  R.id.nav_Notes:
                intent=new Intent(this,NoteActivity.class);
                startActivity(intent);
                return true;
            case  R.id.nav_Around:
                intent=new Intent(this,Map.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    @Override
    public void serviceSuccess(Channel channel) {
       //dialog.hide();
        Item item = channel.getItem();
       // int resourceID = getResources().getIdentifier("drawable/ic_"+channel.getItem().getCondition().getCode(), null, getPackageName());
      int resourceId=getResources().getIdentifier("drawable/ic"+ item.getCondition().getCode(),null,getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable=getResources().getDrawable(resourceId);
        String Temp= String.valueOf(item.getCondition().getTemperature());
        Log.d(msg,Temp);
        String Units=channel.getUnits().getTemperature();
        Log.d(msg,Units);
        String Description=item.getCondition().getDescription();

        weatherIconImageView.setImageDrawable(weatherIconDrawable);

        temperatureTextView.setText(item.getCondition().getTemperature()+"\u00B0"+channel.getUnits().getTemperature());
        conditionTextView.setText(item.getCondition().getDescription());
        locationTextView.setText("Syracuse, NY");
    }

    @Override
    public void serviceFailure(Exception exception) {
       // dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                   /* Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = hours + ":" + minutes + ":" + seconds;*/
                    String  currentTime= DateFormat.getTimeInstance().format(new Date());
                    TimetextView.setText(currentTime);
                }catch (Exception e) {
                    Log.e(msg,e.getMessage());
                }
            }
        });
    }
    private class CountDownRunner implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try{
                    doWork();
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }catch (Exception e){
                    Log.e(msg,e.getMessage());
                }
            }
        }
    }
}
