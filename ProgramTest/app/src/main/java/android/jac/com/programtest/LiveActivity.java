package android.jac.com.programtest;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by jac on 2018/2/26 0026.
 */

public class LiveActivity extends AppCompatActivity {
    private static final String TAG = LiveActivity.class.getSimpleName();
    private String mUrl, mTitle;
    private TextView mBackButton, mTitleText, mSysTime;
    private VideoView mVideoView;
    private static final int RETRY_TIMES = 5;
    private static final int AUTO_HIDE_TIME = 5000;

    private int mCount = 0;
    private RelativeLayout mLoadLayout;
    private RelativeLayout mRootView, mTopLayout;
    private LinearLayout mBottomLayout;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ImageView mPlayButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        Log.i(TAG, ">>onCreate mTitle=" + mTitle + ",mUrl=" + mUrl);
        initView();
        initPlayer();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initPlayer() {
        Vitamio.isInitialized(getApplicationContext());//初始化播放组件
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mPlayButton = (ImageView) findViewById(R.id.iv_play);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mVideoView.isPlaying()) {
                    mVideoView.stopPlayback();
                    mPlayButton.setImageResource(R.drawable.pausing);
                } else {
                    mVideoView.setVideoURI(Uri.parse(mUrl));
                    mVideoView.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(io.vov.vitamio.MediaPlayer mp) {
                            mVideoView.start();
                        }
                    });
                    mPlayButton.setImageResource(R.drawable.playing);
                }
            }
        });
        mVideoView.setVideoURI(Uri.parse(mUrl));
        mVideoView.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(io.vov.vitamio.MediaPlayer mp) {
                mVideoView.start();
            }
        });
        mVideoView.setOnErrorListener(new io.vov.vitamio.MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(io.vov.vitamio.MediaPlayer mp, int what, int extra) {
                if (mCount > RETRY_TIMES) {
                    new AlertDialog.Builder(LiveActivity.this)
                            .setMessage("Error")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LiveActivity.this.finish();
                                }
                            }).setCancelable(false).show();
                } else {
                    mVideoView.stopPlayback();
                    mVideoView.setVideoURI(Uri.parse(mUrl));
                }
                mCount++;
                return false;
            }
        });
        mVideoView.setOnInfoListener(new io.vov.vitamio.MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(io.vov.vitamio.MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:  //开始缓冲
                        mLoadLayout.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:  //缓冲结束
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
//                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        mLoadLayout.setVisibility(View.GONE);
                        break;
                }
                if (mVideoView.isPlaying())
                    mLoadLayout.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void initView() {
        mBackButton = (TextView) findViewById(R.id.tv_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleText = (TextView) findViewById(R.id.tv_title);
        mTitleText.setText(mTitle);
        mSysTime = (TextView) findViewById(R.id.tv_systime);
        mSysTime.setText(getCurrentTime());
        mLoadLayout = (RelativeLayout) findViewById(R.id.pb_container);
        mRootView = (RelativeLayout) findViewById(R.id.activity_live);
        mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomLayout.getVisibility() == View.VISIBLE
                        || mTopLayout.getVisibility() == View.VISIBLE) {
                    mBottomLayout.setVisibility(View.GONE);
                    mTopLayout.setVisibility(View.GONE);
                    return;
                }
                if (mVideoView.isPlaying()) {
                    mPlayButton.setImageResource(R.drawable.playing);
                } else {
                    mPlayButton.setImageResource(R.drawable.pausing);
                }
                mBottomLayout.setVisibility(View.VISIBLE);
                mTopLayout.setVisibility(View.VISIBLE);
                //没有操作的时候，5秒自动消失
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBottomLayout.setVisibility(View.GONE);
                        mTopLayout.setVisibility(View.GONE);
                    }
                }, AUTO_HIDE_TIME);
            }
        });
    }

    private String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String time = sdf.format(c.getTime());
        return time;
    }
}
