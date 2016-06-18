package com.theleafapps.shopnick.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.theleafapps.shopnick.R;
import com.theleafapps.shopnick.utils.MySingleton;

import java.util.ArrayList;

/**
 * Created by aviator on 18/05/16.
 */
public class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;
        private ArrayList<String> images;
        LinearLayout _thumbnails;
        ViewPager _pager;
        private ImageLoader mImageLoader;

        public GalleryPagerAdapter(Context context,ViewPager pager,LinearLayout thumbnails) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _pager = pager;
            _thumbnails = thumbnails;
            mImageLoader = MySingleton.getInstance(context).getImageLoader();

            images = new ArrayList<>();
            images.add("http://dummyimage.com/280x280/000/fff&text=one");
            images.add("http://dummyimage.com/280x280/000/fff&text=two");
            images.add("http://dummyimage.com/280x280/000/fff&text=three");
            images.add("http://dummyimage.com/280x280/000/fff&text=four");
            images.add("http://dummyimage.com/280x280/000/fff&text=five");
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((RelativeLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

//             You could also set like so to remove borders
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Tangho", "Thumbnail clicked");

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final NetworkImageView imageView =
                    (NetworkImageView) itemView.findViewById(R.id.productDetailMultipleImages);

            imageView.setImageUrl(images.get(position),mImageLoader);

            // Asynchronously load the image and set the thumbnail and pager view
//            Glide.with(_context)
//                    .load(_images.get(position))
//                    .asBitmap()
//                    .into(new SimpleTarget<Bitmap>() {
//                        @Override
//                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                            imageView.setImage(ImageSource.bitmap(bitmap));
//                            thumbView.setImageBitmap(bitmap);
//                        }
//                    });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
 }

