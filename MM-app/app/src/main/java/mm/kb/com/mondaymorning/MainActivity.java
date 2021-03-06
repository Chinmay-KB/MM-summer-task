package mm.kb.com.mondaymorning;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Typeface typeface;
    Toolbar mToolbar;
    MenuInflater inflater;
    MenuItem mSearch;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    NavigationView navigation;
    LinearLayout viewCategoryNames;
    private String url = "http://mondaymorning.nitrkl.ac.in/api/post/get/featured";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Make the activity fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Setting up the appbar
        mToolbar = findViewById(R.id.toolbar);
        int resId=R.anim.layout_animation_fall_down;
        LayoutAnimationController animation= AnimationUtils.loadLayoutAnimation(this,resId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Adding tabs to the tabLayout
        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        //tabLayout.addTab(tabLayout.newTab().setText("THIS WEEK"), 0);
        //tabLayout.addTab(tabLayout.newTab().setText("CATEGORIES"), 1);
        setAppBarHeight();
        initInstances();
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //Iterating over all tabs to set custom view
        for(int i=0;i<tabLayout.getTabCount();i++)
        {
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }
    public void loginScreen(View view)
    {
        startActivity(new Intent(this,Login.class));
    }

    private void initInstances() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.app_name,R.string.action_search);
        drawerLayout.setDrawerListener(drawerToggle);

        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.health:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;
                    case R.id.halls:
                        //Do some thing here
                        // add navigation drawer item onclick method here
                        break;

                }
                return false;
            }
        });

    }

    public void buildCategoryScroll() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 5, 0);

        for (int i = 1; i <= 15; i++) {
            final Button btCategory = new Button(MyContext.getContext());
            btCategory.setText(String.valueOf(i));
            btCategory.setTextSize(16f);
            btCategory.setWidth(width/3);
            btCategory.setAllCaps(false);
            btCategory.setBackgroundColor(ContextCompat.getColor(MyContext.getContext(), R.color.categoriesButton));
            btCategory.setTextColor(ContextCompat.getColor(MyContext.getContext(), android.R.color.white));
            btCategory.setLayoutParams(layoutParams);
            btCategory.setTag(i);
            viewCategoryNames.addView(btCategory);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


 public void articleView(View view)
 {
     startActivity(new Intent(this,ArticleActivity.class));
 }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        mSearch = menu.findItem(R.id.ic_action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        return super.onCreateOptionsMenu(menu);

    }
    private void setAppBarHeight() {
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight() + dpToPx(44 + 56)));
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    class PagerAdapter extends FragmentPagerAdapter {
        LinearLayout viewCategoryNames;
        String tabTitles[] = new String[] { "THIS WEEK", "CATEGORIES"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;


        }

        @Override
        public int getCount() {
            return 2;
        }
         Button btCategory = new Button(MyContext.getContext());
        @Override
        public Fragment getItem(int position) {

            if(position==0)
             {     // viewCategoryNames.removeAllViewsInLayout();
                 //buildCategoryScrolls();
                    return new BlankFragment();
                }
             else
            {  //viewCategoryNames.removeAllViewsInLayout();
                //buildCategoryScrolls();
            return new BlankFragment();
            }
        }
        public void buildCategoryScroll() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 5, 0);

            for (int i = 1; i <= 3; i++) {

                btCategory.setText(String.valueOf(i));
                btCategory.setTextSize(16f);
                btCategory.setWidth(width/3);
                btCategory.setAllCaps(false);
                btCategory.setBackgroundColor(ContextCompat.getColor(MyContext.getContext(), R.color.categoriesButton));
                btCategory.setTextColor(ContextCompat.getColor(MyContext.getContext(), android.R.color.white));
                btCategory.setLayoutParams(layoutParams);
                btCategory.setTag(i);
                viewCategoryNames.addView(btCategory);
            }
        }
        public void buildCategoryScrolls() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 5, 0);

            for (int i = 1; i <= 6; i++) {
                //final Button btCategory = new Button(MyContext.getContext());
                btCategory.setText(String.valueOf(i));
                btCategory.setTextSize(16f);
                btCategory.setWidth(width/4);
                btCategory.setAllCaps(false);
                btCategory.setBackgroundColor(ContextCompat.getColor(MyContext.getContext(), R.color.categoriesButton));
                btCategory.setTextColor(ContextCompat.getColor(MyContext.getContext(), android.R.color.white));
                btCategory.setLayoutParams(layoutParams);
                btCategory.setTag(i);
                viewCategoryNames.addView(btCategory);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }

    }
}