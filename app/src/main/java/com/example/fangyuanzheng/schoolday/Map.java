package com.example.fangyuanzheng.schoolday;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Map extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    googleMapFrag mapFrag=new googleMapFrag();
    private static DrawerLayout mDrawerLayout;
    TextView NavMyEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setupWindowAnimations();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email=user.getEmail();
        View headerView=(View) navigationView.getHeaderView(0);
        NavMyEmail=(TextView)headerView.findViewById(R.id.NavMyEmail);
        NavMyEmail.setText(email);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.googlecantainer,mapFrag)
                .commit();

        //Toolbar myToolBar=(Toolbar) findViewById(R.id.toolbar_map);
        //setSupportActionBar(myToolBar);
        //myToolBar.inflateMenu(R.menu.map_menu);
        //myToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()){}
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
        getWindow().setExitTransition(new Slide().setDuration(1000));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){
            case R.id.nav_home:
                intent=new Intent(this,MainActivity.class);
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
/*    public void callMap(View view){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.googlecantainer,mapFrag)
                .commit();

    }*/

}
