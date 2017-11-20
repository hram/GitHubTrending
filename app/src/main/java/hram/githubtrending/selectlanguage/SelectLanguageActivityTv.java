package hram.githubtrending.selectlanguage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import hram.githubtrending.R;

/**
 * @author Evgeny Khramov
 */
public class SelectLanguageActivityTv extends Activity {

    public static void start(@NonNull Context context) {
        context.startActivity(new Intent(context, SelectLanguageActivityTv.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_select_language);
    }
}
