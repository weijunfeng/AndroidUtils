package weijunfeng.com.androidutils.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import weijunfeng.com.androidutils.L;
import weijunfeng.com.androidutils.R;

/**
 * 广告页界面
 */
public class AdActivity extends Activity
{
    private static final String TAG = "AdActivity";
    
    private int[] imageId;
    
    private List<View> adImages;
    
    private ViewPager adViewPage;
    
    private LinearLayout adPointContainer;
    
    private View floatingPoint;
    
    private int ponitWidth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity_layout);
        bindView();
        initImageData();
        initView();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(adImages);
        adViewPage.setAdapter(viewPagerAdapter);
        adViewPage.addOnPageChangeListener(new adOnPageChangeListener());
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        L.i(TAG, "activity is ondestory");
    }
    
    private void initView()
    {
        int imageSize = adImages.size();
        for (int i = 0; i < imageSize; i++)
        {
            View fixedPonit = new View(this);
            fixedPonit.setBackgroundResource(R.drawable.ad_ponit_gray);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 10), 
                DensityUtil.dip2px(this, 10));
            if (i > 0)
            {
                params.leftMargin = DensityUtil.dip2px(this, 10);
            }
            fixedPonit.setLayoutParams(params);
            adPointContainer.addView(fixedPonit);
        }
        if (adPointContainer.getChildCount() > 0)
        {
            adPointContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener()
            {
                @Override
                public void onGlobalLayout()
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    {
                        
                        adPointContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    else
                    {
                        adPointContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                    ponitWidth = adPointContainer.getChildCount() == 1 ? adPointContainer.getChildAt(0).getLeft() : 
                        adPointContainer.getChildAt(1).getLeft() - adPointContainer.getChildAt(0).getLeft();
                }
            });
        }
    }
    
    private void initImageData()
    {
        imageId = new int[]{R.drawable.newfeature_0, R.drawable.newfeature_1, R.drawable.newfeature_2};
        adImages = new ArrayList<View>();
        for (int imageView : imageId)
        {
            ImageView imageView1 = new ImageView(this);
            imageView1.setBackgroundResource(imageView);
            adImages.add(imageView1);
        }
    }
    
    private void bindView()
    {
        adViewPage = (ViewPager)findViewById(R.id.ad_viewpage);
        adPointContainer = (LinearLayout)findViewById(R.id.ad_point_container);
        floatingPoint = new View(this);
        floatingPoint.setLayoutParams(new RelativeLayout.LayoutParams(DensityUtil.dip2px(this, 10), DensityUtil
            .dip2px(this, 10)));
        floatingPoint.setBackgroundResource(R.drawable.ad_ponit_red);
        ((RelativeLayout)adPointContainer.getParent()).addView(floatingPoint);
        
    }
    
    private class adOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            System.out.println("weijunfeng position" + position + " positionOffset:" + positionOffset + " " +
                "positionOffsetPixels:" + positionOffsetPixels);
            int floatingPonitl = (int)(ponitWidth * positionOffset + ponitWidth * position);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)floatingPoint.getLayoutParams();
            params.leftMargin = floatingPonitl;
            floatingPoint.setLayoutParams(params);
            
        }
        
        @Override
        public void onPageSelected(int position)
        {
            
        }
        
        @Override
        public void onPageScrollStateChanged(int state)
        {
            
        }
    }
    
    private class ViewPagerAdapter extends PagerAdapter
    {
        List<View> views;
        
        ViewPagerAdapter(List<View> views)
        {
            this.views = views;
        }
        
        @Override
        public int getCount()
        {
            return views.size();
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = views.get(position);
            container.addView(view);
            return view;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View)object);
        }
        
        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
    }
}
