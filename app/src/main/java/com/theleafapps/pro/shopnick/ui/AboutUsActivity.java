package com.theleafapps.pro.shopnick.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.theleafapps.pro.shopnick.R;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

//        Commons.getActivityTrail(this);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
                .setDescription("Shop nick\nOne Stop Online fashion destination in Mobile app\n" +
                        "\n" +
                        "Explore the latest fashion trends for men and women, and feel the mobility in you online shopping experience.\n")
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect with us")
                .addEmail("getintouch@theleafapps.com")
                .addWebsite("http://theleafapps.com/")
                .addFacebook("theleafapps")
                .addTwitter("theleafapps")
                .addPlayStore("com.theleafapps.pro")
                .addInstagram("theleafapps")
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(("copy_right"), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIcon(R.drawable.eight);
        copyRightsElement.setColor(ContextCompat.getColor(this, mehdi.sakout.aboutpage.R.color.about_item_icon_color));
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUsActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Tangho", "AboutUs activity >> onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        Log.d("Tangho", "AboutUs activity >> onPause Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Tangho", "AboutUs activity >> onDestroy Called");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("Tangho", "AboutUs activity >> onBackPressed Called");
    }

}
