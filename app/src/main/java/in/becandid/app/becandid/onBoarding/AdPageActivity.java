package in.becandid.app.becandid.onBoarding;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;
import java.util.List;

import in.becandid.app.becandid.R;

public class AdPageActivity extends AhoyOnboarderActivity {
    private RewardedVideoAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // live ad mob ID   mRewardedVideoAd.loadAd("ca-app-pub-1116209838416672~3025851301", new AdRequest.Builder().build());
        //  mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544~3347511713", new AdRequest.Builder().build());


        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Premium Feature", "This is sneak peak search of existing users inside the app. All privacy of the users can be toggled in the Settings Page.", R.drawable.backpack);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("How it Works ?", "You visit this page and watch video Ad to unlock Premium Search Feature. ", R.drawable.chalk);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Inside Premium", "Connect with like minded people and exchange your travel stories based on Age and Gender", R.drawable.chat);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            //page.setTitleTextSize(dpToPixels(12, this));
            //page.setDescriptionTextSize(dpToPixels(8, this));
            //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
        }

        setFinishButtonTitle("Finish");
        showNavigationControls(true);
        setGradientBackground();

        setOnboardPages(pages);
    }

    @Override
    protected void setUp() {

    }

    @Override
    public void onFinishButtonPressed() {

        Intent intent = new Intent(this, WatchVideoAdActivity.class);
        startActivity(intent);
    }


}
