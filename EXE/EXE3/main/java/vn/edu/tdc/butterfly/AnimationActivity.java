package vn.edu.tdc.butterfly;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    private static final String TAG = AnimationActivity.class.getSimpleName();

    private ImageView mImageView;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.logo);
    }

    public void timeout(int i){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                mImageView.clearAnimation();
            }
        }, i);
    }

    public void zoomin(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        mImageView.startAnimation(animation1);

        timeout(2000);
    }

    public void zoomout(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        mImageView.startAnimation(animation1);

        timeout(2000);
    }

    public void move(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        mImageView.startAnimation(animation1);

        timeout(2000);
    }

    public void blink(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animation1.setDuration(500);
        mImageView.startAnimation(animation1);
    }

    public void slideUp(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mImageView.startAnimation(animation1);

        timeout(2000);
    }

    public void fade(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        mImageView.startAnimation(animation1);
    }

    public void slideDown(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        mImageView.startAnimation(animation1);
    }
    public void rotate(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        mImageView.startAnimation(animation1);

        timeout(2000);
    }
    public void sequential(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sequential);
        mImageView.startAnimation(animation1);

        timeout(5000);
    }
    public void faderepeat(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.faderepeat);
        mImageView.startAnimation(animation1);

        timeout(3000);
    }
    public void inter(View view) {
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.inter);
        mImageView.startAnimation(animation1);

        timeout(3000);
    }

}
