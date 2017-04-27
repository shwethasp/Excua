package com.varsim.myexcua.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.varsim.myexcua.R;
import com.varsim.myexcua.adapter.PagerAdapters;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.varsim.myexcua.R.id.tabLayout;

public class EventsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    //This is our tablayout
    private TabLayout mTabLayout;
    //This is our view pager
    private ViewPager mViewPager;
    PagerAdapter adapter;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventsActivity.this, CreateEventActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Initializing viewPaager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the tablayout
        mTabLayout = (TabLayout) findViewById(tabLayout);
        //Adding the tabs using addtab() method
        mTabLayout.addTab(mTabLayout.newTab().setText("Today"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tomorrow"));
        mTabLayout.addTab(mTabLayout.newTab().setText("All"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //setting adapter to pager
        mTabLayout.setupWithViewPager(mViewPager);

        //creating pager adapter
        adapter = new PagerAdapters(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
/*

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getItemPosition(i));
        }
*/

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("Posi pageSele", "" + position);
                if (position == 1) {
                    mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            mViewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Adding onTabselectedListener to swipe views
        mTabLayout.setOnTabSelectedListener(this);
    }

    private TabLayout.Tab createTabForTittle(String title) {
        if (mTabLayout == null) {
            mTabLayout = (TabLayout) findViewById(tabLayout);
        }
        TabLayout.Tab tab = mTabLayout.newTab();
        tab.setText(title);
        return tab;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(getApplicationContext(), "You clicked on Home", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_settings) {
            Toast.makeText(getApplicationContext(), "You clicked on Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_aboutus) {
            Toast.makeText(getApplicationContext(), "You clicked on About us", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_help) {
            Toast.makeText(getApplicationContext(), "You clicked on Help", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signout) {
            Intent i = new Intent(EventsActivity.this, LoginActivity.class);
            i.putExtra("finish", true);
            //to cleanup all the activities
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            /*overridePendingTransition(R.anim.
                            activity_slide_left_out,
                    R.anim.activity_slide_left_in);*/
        /*} else if (id == R.id.nav_send) {
*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
