package com.example.fangyuanzheng.schoolday;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.TabLayout;
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
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClassScheduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static DrawerLayout mDrawerLayout;
    ViewPager viewPager;
    TextView NavMyEmail;
    recycleViewClassScheduleFragment recycleViewClassScheduleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setupWindowAnimations();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Class Schedule");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ;
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = "";
        if (user.getEmail() != null) {
            email = user.getEmail();
        }
        View headerView = (View) navigationView.getHeaderView(0);
        NavMyEmail = (TextView) headerView.findViewById(R.id.NavMyEmail);
        NavMyEmail.setText(email);
        viewPager = (ViewPager) findViewById(R.id.ClassScheduleViewPager);
        // viewPager.getCurrentItem();
        PageListener pageListener = new PageListener();
        viewPager.addOnPageChangeListener(pageListener);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedPosition = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalizedPosition / 2 + 0.5f);
                page.setScaleY(normalizedPosition / 2 + 0.5f);
            }
        });
        // viewPager.setCurrentItem(2,true);
        //savedInstanceState.putInt("currentpage",pageListener.getCurrentPage());
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(), ClassScheduleActivity.this));
        TabLayout tableLayout = (TabLayout) findViewById(R.id.WeekDayTab);
        tableLayout.setupWithViewPager(viewPager);
        //tableLayout.getSelectedTabPosition();
        /* FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recycleViewClassScheduleFragment=new recycleViewClassScheduleFragment();
        fragmentTransaction.add(R.id.classSchedleLayout_continer, recycleViewClassScheduleFragment);*/
        // fragmentTransaction.addToBackStack(null);
        //   fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.nav_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_classSchedule:
                intent = new Intent(this, ClassScheduleActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_Notes:
                intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_Around:
                intent = new Intent(this, Map.class);
                startActivity(intent);
                return true;

        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_AddClass) {
            Toast.makeText(this, "Add " + viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            AddClassFrgament addClassFrgament = new AddClassFrgament();
            addClassFrgament.setEnterTransition(new Fade());
            addClassFrgament.setExitTransition(new Slide(Gravity.RIGHT));
            fragmentTransaction.replace(R.id.classSchedleLayout_continer, addClassFrgament);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //Intent intent = new Intent(this,Activity_Drawable.class);
            //if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            //startActivity(intent);
            // overridePendingTransition(R.anim.hyperspace_jump, android.R.anim.fade_out);
            // ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,(View)intent,"testAnimation")
            //}
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class PageListener extends ViewPager.SimpleOnPageChangeListener {
        private int currentPage;
        private String tabTiltles[] = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            recycleViewClassScheduleFragment recycleViewClassScheduleFragment = new recycleViewClassScheduleFragment();
            recycleViewClassScheduleFragment.newInstance(tabTiltles[position], position);
        }


        public final int getCurrentPage() {
            return currentPage;
        }
    }
}