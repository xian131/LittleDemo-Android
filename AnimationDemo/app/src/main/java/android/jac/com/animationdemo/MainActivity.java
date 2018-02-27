package android.jac.com.animationdemo;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mScaleAnimBtn, mAlphaAnimBtn, mRotateAnimBtn, mTranslateAnimBtn,
            mContinueAnimBtn, mContinueAnimBtn2, mFlashAnimBtn,
            mChangeAnimBtn, mLayoutAnimBtn, mFrameAnimBtn;
    private Animation loadAnimation;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mScaleAnimBtn = (Button) findViewById(R.id.scaleAnimBtn);
        mAlphaAnimBtn = (Button) findViewById(R.id.alphaAnimBtn);
        mRotateAnimBtn = (Button) findViewById(R.id.rotateAnimBtn);
        mTranslateAnimBtn = (Button) findViewById(R.id.translateAnimBtn);

        mContinueAnimBtn = (Button) findViewById(R.id.continueAnimBtn);
        mContinueAnimBtn2 = (Button) findViewById(R.id.continueAnimBtn2);
        mFlashAnimBtn = (Button) findViewById(R.id.flashAnimBtn);
        mChangeAnimBtn = (Button) findViewById(R.id.changeAnimBtn);
        mLayoutAnimBtn = (Button) findViewById(R.id.layoutAnimBtn);
        mFrameAnimBtn = (Button) findViewById(R.id.frameAnimBtn);

        image = (ImageView) findViewById(R.id.imageAnim);

        mScaleAnimBtn.setOnClickListener(this);
        mAlphaAnimBtn.setOnClickListener(this);
        mRotateAnimBtn.setOnClickListener(this);
        mTranslateAnimBtn.setOnClickListener(this);

        mContinueAnimBtn.setOnClickListener(this);
        mContinueAnimBtn2.setOnClickListener(this);
        mFlashAnimBtn.setOnClickListener(this);
        mChangeAnimBtn.setOnClickListener(this);
        mLayoutAnimBtn.setOnClickListener(this);
        mFrameAnimBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scaleAnimBtn:
//                Scale缩放动画
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
                image.startAnimation(loadAnimation);
                break;
            case R.id.alphaAnimBtn:
//                Alpha透明度动画
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha);
                image.startAnimation(loadAnimation);
                break;
            case R.id.rotateAnimBtn:
//                Rotate旋转动画
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
                image.startAnimation(loadAnimation);
                break;
            case R.id.translateAnimBtn:
//                Translate位移动画
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
                image.startAnimation(loadAnimation);
                break;
            case R.id.continueAnimBtn:
//                Translate+Rotate
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
                image.startAnimation(loadAnimation);
                final Animation loadAnimation2 = AnimationUtils.loadAnimation(this, R.anim.rotate);
                loadAnimation.setAnimationListener(new Animation.AnimationListener() {//动画监听器
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        image.startAnimation(loadAnimation2);//Translate结束后开始Rotate
                    }
                });
                break;
            case R.id.continueAnimBtn2:
//                Alpha+Alpha
                loadAnimation = AnimationUtils.loadAnimation(this, R.anim.continue_alpha);
                image.startAnimation(loadAnimation);
                break;
            case R.id.flashAnimBtn:
//                Alpha闪烁
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
                alphaAnimation.setDuration(100);//持续时间
                alphaAnimation.setRepeatCount(10);//重复次数10次
                alphaAnimation.setRepeatMode(Animation.REVERSE);//倒序重复REVERSE 正序重复RESTART
                image.startAnimation(alphaAnimation);
                break;
            case R.id.changeAnimBtn:
//                Activity切换动画
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                break;
            case R.id.layoutAnimBtn:
//                LayoutAnimation布局动画
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
            case R.id.frameAnimBtn:
//                FrameAnimation逐帧动画
                image.setImageResource(R.drawable.anim_list);
                AnimationDrawable ad = (AnimationDrawable) image.getDrawable();//帧动画对象
                ad.start();
                break;
            default:
                break;
        }
    }
}
