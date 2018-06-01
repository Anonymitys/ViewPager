package ecnu.viewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ecnu.viewpagercircle.PagerView;

public class MainActivity extends AppCompatActivity {
    private PagerView pagerView;
    private int[] imageDrawable=new int[]{R.drawable.cherry,R.drawable.grape,R.drawable.orange,R.drawable.pear,R.drawable.pineapple,
    R.drawable.strawberry};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagerView=(PagerView)findViewById(R.id.pager);
        pagerView.setDuration(2000);
        pagerView.setImageView(imageDrawable);
        pagerView.setOnClickListener(new PagerView.OnClickListener() {
            @Override
            public void OnClick(int position) {
                Toast.makeText(MainActivity.this,"position"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
