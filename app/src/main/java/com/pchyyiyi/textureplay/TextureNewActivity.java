package com.pchyyiyi.textureplay;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.pchyyiyi.textureplay.model.UserAdvertInfo;
import com.pchyyiyi.textureplay.util.AndroidLog;
import com.pchyyiyi.textureplay.util.AndroidUtil;
import com.pchyyiyi.textureplay.widget.NiceTextureView;

import java.text.DecimalFormat;


public class TextureNewActivity extends AppCompatActivity {

    private static final String TAG = "TextureNewActivity";

    public static final String BUNDLE_KEY_ADVERTINFO = "ADVERTINFO";
    public static final String BUNDLE_KEY_AMOUNT = "AMOUNT";

    Activity mContext;

    /**
     * 关闭
     */
    private AppCompatButton closeBtn;
    /**
     * 红包金额
     */
    private AppCompatTextView redEnveAmountTV;
    /**
     * 视频广告：
     */
    private FrameLayout showVideoLay;
    private NiceTextureView showTextureView;
    private Surface mSurface;

    private LinearLayout showProgressView;
    private AppCompatTextView startOrPauseBtn;

    /**
     * 广告描述语
     */
    private AppCompatTextView showContentView;
    /**
     * 确定
     */
    private AppCompatButton sureBtn;


    final int MAX_TIME_INDEX = 20;
    int timerIndex = MAX_TIME_INDEX;
    Runnable authTimer = new Runnable() {
        @Override
        public void run() {
            timerIndex--;
            if (timerIndex == 0) {
                sureBtn.setText("领取红包");
                sureBtn.setEnabled(true);
                timerIndex = MAX_TIME_INDEX;
                mBaseHandler.removeCallbacks(authTimer);
                return;
            } else
                sureBtn.setEnabled(false);
            sureBtn.setText(String.format("%d秒", timerIndex));
            mBaseHandler.postDelayed(this, 1000);
        }
    };

    private Handler mBaseHandler = new Handler(){
        public void handleMessage(Message msg){
        }
    };
    private UserAdvertInfo mUserAdvertInfo;
    private float mAmountfloat;

    /**
     * 倒计时事件根据红包金额大小定
     */
    private int mCountdownTimer = MAX_TIME_INDEX;
    private String mAmount;

    MediaPlayer mMediaPlayer;
    /**
     * 视频需要特殊处理
     */
    private boolean mIsVideoShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texture_new);
        mContext = this;
        assignViews();
        initData();
    }


    protected void assignViews() {
        /**
         * 关闭
         */
        closeBtn = (AppCompatButton)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidLog.i(TAG, "click closeBtn");
                showToast("点击了暂不领取按钮");
                AndroidUtil.goBack(TextureNewActivity.this);
            }
        });
        /**
         * 红包金额
         */
        redEnveAmountTV = (AppCompatTextView)this.findViewById(R.id.redEnveAmountTV);
        /**
         * 视频广告：
         */
        showVideoLay = (FrameLayout)this.findViewById(R.id.showVideoLay);
        showTextureView = (NiceTextureView)this.findViewById(R.id.showVideoView);
        showProgressView = (LinearLayout) this.findViewById(R.id.showProgressView);
        startOrPauseBtn = (AppCompatTextView)this.findViewById(R.id.startOrPauseBtn);
        startOrPauseBtn.setVisibility(View.GONE);
        /**
         * 广告描述语
         */
        showContentView = (AppCompatTextView)this.findViewById(R.id.showContentView);
        /**
         * 确定
         */
        sureBtn = (AppCompatButton)this.findViewById(R.id.sureBtn);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidLog.i(TAG, "click sureBtn");
                showToast("点击了领取按钮");
                AndroidUtil.goBack(TextureNewActivity.this);
            }
        });

        showVideoLay.setVisibility(View.GONE);
    }

    protected void initData() {
        initViewData();
        processAdvertShow();
    }


    /**
     * 对控件数据进行赋值
     */
    private void initViewData(){
        AndroidLog.i(TAG, "initViewData");

        Bundle mBundle = getIntent().getExtras();
        if(mBundle == null || !mBundle.containsKey(BUNDLE_KEY_ADVERTINFO)
                || !mBundle.containsKey(BUNDLE_KEY_AMOUNT)){
            return;
        }

        mAmountfloat = mBundle.getFloat(BUNDLE_KEY_AMOUNT);
        mUserAdvertInfo = (UserAdvertInfo) mBundle.getSerializable(BUNDLE_KEY_ADVERTINFO);

        this.mAmount = formatDoubleForDia(mAmountfloat);
        this.mCountdownTimer = 9;

        sureBtn.setEnabled(false);
        if(mBaseHandler!=null) {
            timerIndex = mCountdownTimer;
            mBaseHandler.post(authTimer);
        }

        if(mUserAdvertInfo == null){
            showVideoLay.setVisibility(View.GONE);

            redEnveAmountTV.setText("¥"+mAmount);
            showContentView.setText("欢迎使用义为思，一款看广告领红包的APP");
            return;
        }
        AndroidLog.i(TAG, "mUserAdvertInfo - "+mUserAdvertInfo.toString());

        String content = mUserAdvertInfo.getAdContent();
        String videoUrl = mUserAdvertInfo.getVideoUrl();
        showContentView.setText(TextUtils.isEmpty(content)?"":content);
        //2 视频 //1 图片 //0 文字
        if(mUserAdvertInfo.getAclass()==2){//视频
            mIsVideoShow = true;
            showVideoLay.setVisibility(View.VISIBLE);
            redEnveAmountTV.setText("¥"+mAmount);
        }else {//图片或文字
            mIsVideoShow = false;
            showVideoLay.setVisibility(View.GONE);
            redEnveAmountTV.setText("¥"+mAmount);
        }
    }

    /**
     * 根据控件数据对视频信息进行加载初始化
     */
    private void processAdvertShow(){

        showTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                AndroidLog.i(TAG, "onSurfaceTextureAvailable width - "+width+"  height - "+height);
                // SurfaceTexture准备就绪
                mSurface = new Surface(surface);
                mBaseHandler.post(mPlayRun);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // SurfaceTexture缓冲大小变化
                AndroidLog.i(TAG, "onSurfaceTextureSizeChanged width - "+width+"  height - "+height);
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                // SurfaceTexture即将被销毁
                AndroidLog.i(TAG, "onSurfaceTextureDestroyed");
                mSurface=null;

                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                    mMediaPlayer.stop();
                    AndroidLog.i(TAG, "onSurfaceTextureDestroyed mMediaPlayer release");
                    mMediaPlayer.reset();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }

                surfaceTexture.release();
                surfaceTexture=null;
                mBaseHandler.removeCallbacks(mPlayRun);
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // SurfaceTexture通过updateImage更新
                AndroidLog.i(TAG, "onSurfaceTextureUpdated");
            }//https://blog.csdn.net/wenxiang423/article/details/82662113
        });

        showTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext.isFinishing()){
                    return;
                }

                if(showTextureView == null){
                    return;
                }
                if(mMediaPlayer == null){
                    return;
                }

                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    if(startOrPauseBtn!=null && startOrPauseBtn.getVisibility()!=View.VISIBLE){
                        startOrPauseBtn.setVisibility(View.VISIBLE);
                    }
                }else{
                    mMediaPlayer.start();
                    if(startOrPauseBtn!=null && startOrPauseBtn.getVisibility()==View.VISIBLE){
                        startOrPauseBtn.setVisibility(View.GONE);
                    }
                }
            }
        });
    }


    Runnable mPlayRun =new Runnable() {
        @Override
        public void run() {
            try {
                AndroidLog.i(TAG, "mPlayRun ------ start");
                if(mSurface == null || mUserAdvertInfo == null){
                    AndroidLog.i(TAG, "mPlayRun ------ null");
                    return;
                }
                AndroidLog.i(TAG, "mPlayRun ------ new MediaPlayer()");
                mMediaPlayer = new MediaPlayer();
                String videoUrl = mUserAdvertInfo.getVideoUrl();

                mMediaPlayer.setSurface(mSurface);
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mContext, Uri.parse(videoUrl));
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer arg0) {
                        AndroidLog.i(TAG, "onPrepared");
                        mMediaPlayer.start();
                        showProgressView.setVisibility(View.GONE);
                    }
                });
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        AndroidLog.i(TAG, "onCompletion");
                        mMediaPlayer.start();
                        showProgressView.setVisibility(View.GONE);
                    }
                });

                mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        AndroidLog.i(TAG, "onVideoSizeChanged width" + width+"  height - "+height);
//                        int mVideoHeight = mMediaPlayer.getVideoHeight();
//                        int mVideoWidth = mMediaPlayer.getVideoWidth();
//                        AndroidLog.i(TAG, "onVideoSizeChanged mVideoWidth"
//                                + mVideoWidth+"  mVideoHeight - "+mVideoHeight);
                        showTextureView.adaptVideoSize(width, height);
                    }
                });//https://blog.csdn.net/qq_24295537/article/details/53997098

                mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        AndroidLog.i(TAG, "onBufferingUpdate percent" + percent);
                    }
                });

                mMediaPlayer.prepareAsync();
                AndroidLog.i(TAG, "mPlayRun ------ end");
            } catch (Exception e) {
                e.printStackTrace();
                AndroidLog.i(TAG, "mPlayRun ------ Exception");
            }
        }
    };

    private void showToast(String str){
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setText(str);
        toast.show();
    }

    /**
     * 将传入的数据保留两位小数
     * 格式如：15.04
     *
     * @param amount
     * @return
     */
    private String formatDoubleForDia(double amount) {
        DecimalFormat formater = new DecimalFormat("###,##0.00");
        String result = formater.format(amount*0.9);
        return result;
    }

}