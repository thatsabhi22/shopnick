package com.theleafapps.shopnick.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.adapters.ViewPagerAdapter;
import com.theleafapps.shopnick.models.Category;
import com.theleafapps.shopnick.models.SubCategory;
import com.theleafapps.shopnick.models.multiples.Categories;
import com.theleafapps.shopnick.models.multiples.SubCategories;
import com.theleafapps.shopnick.tasks.AddSubCategoryTask;
import com.theleafapps.shopnick.tasks.GetAllCategoriesTask;
import com.theleafapps.shopnick.tasks.GetAllSubCategoriesTask;
import com.theleafapps.shopnick.ui.fragments.OneFragment;
import com.theleafapps.shopnick.utils.Commons;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class ShowcaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    List<Category> categoriesRec;
    List<SubCategory> subCategoriesRec;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_showcase);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

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
                Toast.makeText(this, cat.categories.size() + " Categories have been received", Toast.LENGTH_SHORT).show();
                subCategoriesRec = subCat.subCategories;

                /*****
                 * Storing All SubCategories corresponding to CategoryId in Common Hashmap
                 ******/

                for(SubCategory sc : subCategoriesRec){
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
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

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
}
