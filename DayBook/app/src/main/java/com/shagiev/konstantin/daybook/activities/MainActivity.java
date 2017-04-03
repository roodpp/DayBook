package com.shagiev.konstantin.daybook.activities;

import android.app.FragmentManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.fragments.SplashFragment;
import com.shagiev.konstantin.daybook.helper.PreferencesHelper;

public class MainActivity extends AppCompatActivity {


    private FragmentManager mFragmentManager;
    private PreferencesHelper mPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();

        PreferencesHelper.getInstance().init(getApplicationContext());
        mPreferencesHelper = PreferencesHelper.getInstance();

        runSplash();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(mPreferencesHelper.getBoolean(PreferencesHelper.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_splash){
            item.setChecked(!item.isChecked());
            mPreferencesHelper.putBoolean(PreferencesHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runSplash(){

        if(!mPreferencesHelper.getBoolean(PreferencesHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            mFragmentManager.beginTransaction()
                    .replace(R.id.container, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
