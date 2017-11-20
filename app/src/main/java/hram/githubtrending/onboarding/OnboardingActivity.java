package hram.githubtrending.onboarding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import hram.githubtrending.R;

/**
 * @author Evgeny Khramov
 */
public class OnboardingActivity extends FragmentActivity {

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, OnboardingActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_onboarding);
    }
}
