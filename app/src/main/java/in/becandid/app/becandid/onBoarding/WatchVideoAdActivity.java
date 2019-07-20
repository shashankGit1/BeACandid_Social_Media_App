package in.becandid.app.becandid.onBoarding;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import in.becandid.app.becandid.R;
import in.becandid.app.becandid.ui.base.BaseActivity;
import in.becandid.app.becandid.ui.filter.NewFilterActivity;

public class WatchVideoAdActivity extends BaseActivity implements RewardedVideoAdListener {
 // testing   private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
   // earlier private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
   // earlier protected static final String APP_ID = "ca-app-pub-3940256099942544~3347511713";

    private static final long COUNTER_TIME = 10;
    private static final int GAME_OVER_REWARD = 1;


    private CountDownTimer countDownTimer;
    private boolean gameOver;
    private boolean gamePaused;
    private RewardedVideoAd rewardedVideoAd;
    private Button retryButton;
    private Button showVideoButton;
    private long timeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_video_ad);

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, getResources().getString(R.string.VIDEO_REWARD_LIVE_APP_ID));


        //   MobileAds.initialize(this, "ca-app-pub-4761500786576152~8215465788");

        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        // Create the "retry" button, which tries to show an interstitial between game plays.
        retryButton = (Button)findViewById(R.id.retry_button);
        retryButton.setVisibility(View.INVISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });


        showVideoButton = (Button)findViewById(R.id.show_video_button);
        showVideoButton.setVisibility(View.INVISIBLE);
        showVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardedVideo();
            }
        });



        startGame();



    }

    @Override
    public void onPause() {
        super.onPause();
        pauseGame();
        rewardedVideoAd.pause(this);
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!gameOver && gamePaused) {
            resumeGame();
        }
        rewardedVideoAd.resume(this);
    }

    private void pauseGame() {
        countDownTimer.cancel();
        gamePaused = true;
    }

    private void resumeGame() {
        createTimer(timeRemaining);
        gamePaused = false;
    }

    private void loadRewardedVideoAd() {
        if (!rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.loadAd(getResources().getString(R.string.VIDEO_REWARD_LIVE_AD_UNIT_ID), new AdRequest.Builder().build());
          //  rewardedVideoAd.loadAd("/6499/example/rewarded-video", new PublisherAdRequest.Builder().build());
        }



    }

    private void startGame() {
        // Hide the retry button, load the ad, and start the timer.
        retryButton.setVisibility(View.INVISIBLE);
        showVideoButton.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
        createTimer(COUNTER_TIME);
        gamePaused = false;
        gameOver = false;
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    private void createTimer(long time) {
        final TextView textView = findViewById(R.id.timer);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(time * 1000, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timeRemaining = ((millisUnitFinished / 1000) + 1);
                textView.setText("seconds remaining: " + timeRemaining);
            }

            @Override
            public void onFinish() {
                if (rewardedVideoAd.isLoaded()) {
                    showVideoButton.setVisibility(View.VISIBLE);
                }
                //textView.setText("You Lose!");
                retryButton.setVisibility(View.VISIBLE);
                gameOver = true;
            }
        };
        countDownTimer.start();
    }

    private void showRewardedVideo() {
        showVideoButton.setVisibility(View.INVISIBLE);
        if (rewardedVideoAd.isLoaded()) {
            rewardedVideoAd.show();
        } else {
            loadRewardedVideoAd();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
      //  Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();

    }

    @Override
    public void onRewardedVideoAdOpened() {
      //  Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoStarted() {
      //  Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdClosed() {
       // Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();


      //  startActivity(new Intent(this, NewFilterActivity.class));

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
      /*  Toast.makeText(WatchVideoAdActivity.this,
                String.format(" onRewarded! type: %s amount: %d", rewardItem.getType(),
                        rewardItem.getAmount()),
                Toast.LENGTH_SHORT).show(); */
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
      //  Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    //    Toast.makeText(WatchVideoAdActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoCompleted() {
        startActivity(new Intent(this, NewFilterActivity.class));

    }
}
