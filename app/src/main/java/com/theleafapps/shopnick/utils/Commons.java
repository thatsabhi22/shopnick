package com.theleafapps.shopnick.utils;

import com.theleafapps.shopnick.models.SubCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by aviator on 23/04/16.
 */
public class Commons {

    public static Map<Integer,List<SubCategory>> catIdToSubCatMap = new HashMap<>();

}

//            String[] namesArray = {"Hand Bags","Sunglasses","Watches","BackPacks", "Strolleys","Clutches","Jewellery","Rings","Bangles and Bracelets"};
//
//            SubCategories subcats = new SubCategories();
//            for(int i = 0;i<namesArray.length;i++){
//                SubCategory subCategory         =   new SubCategory();
//                subCategory.category_id         =   4;
//
//                subCategory.sub_category_id     =   i + 25;
//                subCategory.sub_category_name   =   namesArray[i];
//                subCategory.sequence            =   i+1;
//                subCategory.sub_category_desc   =   namesArray[i];
//
//                subcats.subCategories.add(subCategory);
//            }
//
//            AddSubCategoryTask addSubCategoryTask = new AddSubCategoryTask(this,subcats);
//            addSubCategoryTask.execute();