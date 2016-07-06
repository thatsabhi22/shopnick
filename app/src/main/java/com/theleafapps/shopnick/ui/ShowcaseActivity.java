package com.theleafapps.shopnick.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.NavigationDrawerRecyclerAdapter;
import com.theleafapps.shopnick.adapters.ViewPagerAdapter;
import com.theleafapps.shopnick.models.Category;
import com.theleafapps.shopnick.models.SubCategory;
import com.theleafapps.shopnick.models.multiples.Categories;
import com.theleafapps.shopnick.models.multiples.SubCategories;
import com.theleafapps.shopnick.tasks.GetAllCategoriesTask;
import com.theleafapps.shopnick.tasks.GetAllSubCategoriesTask;
import com.theleafapps.shopnick.ui.fragments.OneFragment;
import com.theleafapps.shopnick.utils.Commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShowcaseActivity extends AppCompatActivity {

    List<Category> categoriesRec;
    List<SubCategory> subCategoriesRec;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private Toolbar toolbar;                                     // Declaring the Toolbar Object

    static RecyclerView mRecyclerView;                           // Declaring RecyclerView
    static RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    static RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    protected static DrawerLayout mDrawer;                       // Declaring DrawerLayout

    static ActionBarDrawerToggle mDrawerToggle;

    static String titles[] = {};
    static int icons[] = {R.drawable.eight,R.drawable.nine,R.drawable.eight,R.drawable.nine,R.drawable.eight};

    static String NAME = "Leaf Apps";
    static int PROFILE = R.drawable.eight;
    static String EMAIL = "theleafapps@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_showcase);

            ConnectivityManager cm =
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();

            if(isConnected){
                Log.d("Tangho","Network Connected");

                Intent intent   =   getIntent();
                int catId       =   intent.getIntExtra("categoryId",0);

                /*****
                 * Getting All Categories
                 ******/
                GetAllCategoriesTask getAllCategoriesTask = new GetAllCategoriesTask(this);

                boolean x = getAllCategoriesTask.execute().get();

                Categories cat = getAllCategoriesTask.categoriesReceived;

                if (cat != null && cat.categories.size() > 0) {
                    Toast.makeText(this, cat.categories.size() + " Categories have been received", Toast.LENGTH_SHORT).show();
                    categoriesRec = cat.categories;

                    getAllCategoriesTask = null;
                }


                /*****
                 * Getting All Sub Categories
                 ******/
                GetAllSubCategoriesTask getAllSubCategoriesTask = new GetAllSubCategoriesTask(this);
                boolean y = getAllSubCategoriesTask.execute().get();

                SubCategories subCat = getAllSubCategoriesTask.subCategoriesReceived;

                if (cat != null && cat.categories.size() > 0) {
//                Toast.makeText(this, cat.categories.size() + " Categories have been received", Toast.LENGTH_SHORT).show();
                    subCategoriesRec = subCat.subCategories;

                    /*****
                     * Storing All SubCategories corresponding to CategoryId in Common Hashmap
                     ******/

                    for(SubCategory sc : subCategoriesRec){

                        if(TextUtils.isEmpty(sc.image_url)) {
                            sc.image_url = "http://dummyimage.com/180x100/000/fff&text=" + sc.sub_category_name.replace(" ","");
                        }

                        if(Commons.catIdToSubCatMap.containsKey(sc.category_id)){
                            Commons.catIdToSubCatMap.get(sc.category_id).add(sc);
                        }
                        else{
                            List<SubCategory> list = new ArrayList<>();
                            list.add(sc);
                            Commons.catIdToSubCatMap.put(sc.category_id,list);
                        }
                    }
                }

                viewPager = (ViewPager) findViewById(R.id.viewpager);
                setupViewPager(viewPager);

                tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(viewPager);

                if(catId > 0)
                    viewPager.setCurrentItem(catId);

                setUpDrawer();

            }
            else{
                Log.d("Tangho","Network Disonnected");

            }




        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void setUpDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.navList); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new NavigationDrawerRecyclerAdapter(titles,icons,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);

        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);        // Drawer object Assigned to the view

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made

        mDrawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }

    // Catch the events related to the drawer to arrange views according to this
    // action if necessary...
    private DrawerLayout.DrawerListener mDrawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerStateChanged(int status) {

        }

        @Override
        public void onDrawerSlide(View view, float slideArg) {

        }

        @Override
        public void onDrawerOpened(View view) {
            Toast.makeText(getApplicationContext(),"Drawer Opened ... ",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDrawerClosed(View view) {
        }
    };

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if(categoriesRec!=null && categoriesRec.size() > 0){

            for(int i = 0;i<categoriesRec.size();i++) {

                int category_id = categoriesRec.get(i).category_id;
                String name = categoriesRec.get(i).category_name;
                Fragment abc = new OneFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("category_id",category_id);
                abc.setArguments(bundle);
                adapter.addFrag(abc,name);
            }
        }
        viewPager.setAdapter(adapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Batsman");
        listDataHeader.add("Bowler");
        listDataHeader.add("All rounder");
        listDataHeader.add("Wicket keeper");

        // Adding child data
        List<String> batsman = new ArrayList<String>();
        batsman.add("V. Kohli");
        batsman.add("G.J. Bailey");
        batsman.add("H.M. Amla");

        List<String> bowler = new ArrayList<String>();
        bowler.add("D.W. Steyn");
        bowler.add("J.M. Anderson");
        bowler.add("M.G. Johnson");

        List<String> all = new ArrayList<String>();
        all.add("R.A. Jadeja");
        all.add("Shakib Al Hasan");
        all.add("D.J. Bravo");

        List<String> wk = new ArrayList<String>();
        wk.add("A.B. de Villiers");
        wk.add("M.S. Dhoni");
        wk.add("K.C. Sangakkara");

        listDataChild.put(listDataHeader.get(0), batsman); // Header, Child data
        listDataChild.put(listDataHeader.get(1), bowler);
        listDataChild.put(listDataHeader.get(2), all);
        listDataChild.put(listDataHeader.get(2), wk);
    }
}

