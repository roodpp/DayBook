package com.shagiev.konstantin.daybook.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.adapters.TabAdapter;
import com.shagiev.konstantin.daybook.fragments.CurrentTaskFragment;
import com.shagiev.konstantin.daybook.fragments.DoneTaskFragment;
import com.shagiev.konstantin.daybook.fragments.SplashFragment;
import com.shagiev.konstantin.daybook.fragments.TaskFragment;
import com.shagiev.konstantin.daybook.fragments.dialog.AddingTaskDialogFragment;
import com.shagiev.konstantin.daybook.helper.PreferencesHelper;
import com.shagiev.konstantin.daybook.model.Task;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener,
        CurrentTaskFragment.OnDoneTaskListener, DoneTaskFragment.OnRestoreTaskListener {


    private FragmentManager mFragmentManager;
    private PreferencesHelper mPreferencesHelper;
    private TabAdapter mTabAdapter;
    private TaskFragment mCurrentTaskFragment;
    private TaskFragment mDoneTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getFragmentManager();

        PreferencesHelper.getInstance().init(getApplicationContext());
        mPreferencesHelper = PreferencesHelper.getInstance();

        runSplash();

        setUI();
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

        if (id == R.id.action_splash) {
            item.setChecked(!item.isChecked());
            mPreferencesHelper.putBoolean(PreferencesHelper.SPLASH_IS_INVISIBLE, item.isChecked());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runSplash() {

        if (!mPreferencesHelper.getBoolean(PreferencesHelper.SPLASH_IS_INVISIBLE)) {
            SplashFragment splashFragment = new SplashFragment();

            mFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.current_task));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.done_task));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        mTabAdapter = new TabAdapter(mFragmentManager, 2);

        viewPager.setAdapter(mTabAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mCurrentTaskFragment = (CurrentTaskFragment) mTabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);

        mDoneTaskFragment = (DoneTaskFragment) mTabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(mFragmentManager, "AddingTaskDialogFragment");
            }
        });


    }

    @Override
    public void onTaskAdded(Task task) {
        mCurrentTaskFragment.addTask(task);
        Toast.makeText(this, "Дело добавлено", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Дело не добавлено", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskDone(Task task) {
        mDoneTaskFragment.addTask(task);
    }

    @Override
    public void onTaskRestore(Task task) {
        mCurrentTaskFragment.addTask(task);
    }
}
