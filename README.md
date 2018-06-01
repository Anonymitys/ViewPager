# ViewPager
实现了ViewPager的轮播效果，可以自定义轮播过程中每张图片的持续时间<br><br>
<img width="360" height="640" src="https://github.com/Anonymitys/ViewPager/raw/master/app/gif_folder/gif.gif" />
## Gradle
    dependencies {
       ...
       implementation 'com.ecnu:viewpagercircle:2.0.0'
    }
## Usage
### xml
     <ecnu.viewpagercircle.PagerView
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
### java
    private int[] imageDrawable=new int[]{R.drawable.cherry,R.drawable.grape,R.drawable.orange,R.drawable.pear,
                                             R.drawable.pineapple,R.drawable.strawberry};
    pagerView=(PagerView)findViewById(R.id.pager);
    pagerView.setDuration(5000);//setDuration()要在setImageView()前面调用
    pagerView.setImageView(imageDrawable);

## Limitation
* 快速滑动过程中，遇到首尾page时会有卡顿
* 未实现点击事件
## ChangeLog
* 2.0.0 bug修复
* 1.0.0 initial release
