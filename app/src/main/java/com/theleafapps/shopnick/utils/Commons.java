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

//
//    String[] namesArray = {
//            "Swiss Polo Shirt",
//            "Lotto Polo Shirt",
//            "American Swan Polo Shirt"};
//
//    String[] prod_desc = {
//            "Hit the streets in an uber cool style and vogue this season while wearing this unmatched outfit. Made for the man of the hour, this outfit adds more charm to the personality of the wearer."
//            ,"This season ride high on style wearing this uber smart outfit from the house of Lotto. Subtle and stylish in design, this outfit will certainly add more to your charming personality. Made using quality material, this outfit assures breathability and is skin friendly as well."
//            ,"Cool and dapper trousers for an energetic carefree and free spirited person that epitomizes active on-the-go fashion. This pair of trousers is splashed in youthful hues and indeed an easy way to effortlessly style yourself"
//    };
//
//    int[] mrp = {699,749,899};
//    int[] ofp = {399,399,499};
//
//    Products products = new Products();
//for(int i = 0;i<namesArray.length;i++){
//        Product product                 =   new Product();
//        product.product_id              =   i+4;
//        product.sub_category_id         =   8;
//        product.product_desc            =   prod_desc[i];
//        product.product_name            =   namesArray[i];
//        product.unit_mrp                =   mrp[i];
//        product.unit_offerprice         =   ofp[i];
//        product.image_url               =   "http://dummyimage.com/130x110/000/fff&text=" + product.product_name;
//
//        products.products.add(product);
//        }
//
//        AddProductTask addProductTask = new AddProductTask(this,products);
//        addProductTask.execute();

//            AddVariantTask addVariantTask = new AddVariantTask(this,products);
//            addVariantTask.execute();
//
//            Variants variants = new Variants();
//            for(int i = 0;i<namesArray.length;i++){
//                Variant variant                 =   new Variant();
//                variant.product_id              =   product.product_id;
//                variant.variant_id              =   i+1;
//                variant.variant_name            =   "";
//                variant.available               =   false;
//
//                variants.variants.add(variant);
//            }