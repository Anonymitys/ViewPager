package ecnu.viewpagercircle;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PagerView extends RelativeLayout {
    private Context mContext;
    private ViewPager viewPager;
    private List<View> imageViewList=new ArrayList<>();
    private LinearLayout layout;
    private PagerAdapter adapter;
    private Timer timer;
    private int currentItem = 0;
    private boolean isLoop=true;
    private int time=3000;
    private OnClickListener listener;
    private int mTouchSlop;

    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123){
                viewPager.setCurrentItem(currentItem++);
            }
        }
    };
    public interface OnClickListener{
         public void OnClick(int position);
    }
    public PagerView(Context context) {
        super(context);
    }

    public PagerView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        LayoutInflater.from(context).inflate(R.layout.pager_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        initViewPager();
        //initImageView();
        //initCycler();

    }

    private void initViewPager() {
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                View view = imageViewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                if (currentItem == 0) {
                    layout.getChildAt(0).setSelected(false);
                    layout.getChildAt(imageViewList.size()-3).setSelected(true);
                } else if (currentItem == imageViewList.size() - 1) {
                    layout.getChildAt(imageViewList.size()-3).setSelected(false);
                    layout.getChildAt(0).setSelected(true);
                } else {
                    dotSelected(currentItem - 1);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case 0:
                        if (currentItem == 0) {
                            viewPager.setCurrentItem(imageViewList.size() - 2, false);

                        } else if (currentItem == imageViewList.size() - 1) {
                            viewPager.setCurrentItem(1, false);

                        }
                }
            }
        });
        viewPager.setOnTouchListener(new OnTouchListener() {
            int flag=0;
            float x=0,y=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        isLoop=false;
                        x=event.getX();
                        y=event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float deltaX=Math.abs(event.getX()-x);
                        if(deltaX<mTouchSlop){
                            flag=0;
                        }else {
                            flag=-1;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        isLoop=true;
                        if(flag==0){
                            listener.OnClick(viewPager.getCurrentItem());
                        }
                        break;
                }
                return false;
            }
        });
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);
    }

    private void initLayout() {
        layout = (LinearLayout)findViewById(R.id.dot_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18, 18);
        params.setMargins(10, 0, 10, 0);

        for (int i = 0; i < imageViewList.size() - 2; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setImageDrawable(mContext.getDrawable(R.drawable.dot_drawable));
            layout.addView(imageView);
        }
        // layout.getChildAt(0).setSelected(true);
    }

    private void dotSelected(int position) {
        for (int i = 0; i < imageViewList.size() - 2; i++) {
            layout.getChildAt(i).setSelected(false);
            if (i == position) {
                layout.getChildAt(i).setSelected(true);
            }

        }
    }

    private void initCycler() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLoop){
                    handler.sendEmptyMessage(0x123);
                }

            }
        }, 0,time);
    }
    public void setImageView(int[] imageDrawable){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        ImageView imageView0 = new ImageView(mContext);
        imageView0.setLayoutParams(params);
        imageView0.setImageDrawable(mContext.getDrawable(imageDrawable[imageDrawable.length-1]));
        imageView0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewList.add(imageView0);
        for(int i=0;i<imageDrawable.length;i++){

            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setImageDrawable(mContext.getDrawable(imageDrawable[i]));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(imageView);
        }
        ImageView imageView1 = new ImageView(mContext);
        imageView1.setLayoutParams(params);
        imageView1.setImageDrawable(mContext.getDrawable(imageDrawable[0]));
        imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewList.add(imageView1);
        initLayout();
        initCycler();
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(1,false);
    }
    public void setDuration(int time){
        this.time=time;
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener=listener;
    }
}
