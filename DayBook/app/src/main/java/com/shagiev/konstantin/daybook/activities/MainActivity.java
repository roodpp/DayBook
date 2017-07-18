package com.shagiev.konstantin.daybook.activities;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.shagiev.konstantin.daybook.R;
import com.shagiev.konstantin.daybook.adapters.TabAdapter;
import com.shagiev.konstantin.daybook.alarm.AlarmHelper;
import com.shagiev.konstantin.daybook.alarm.MyApplication;
import com.shagiev.konstantin.daybook.database.DBHelper;
import com.shagiev.konstantin.daybook.fragments.CurrentTasksFragment;
import com.shagiev.konstantin.daybook.fragments.DoneTasksFragment;
import com.shagiev.konstantin.daybook.fragments.TasksFragment;
import com.shagiev.konstantin.daybook.fragments.dialog.AddingTaskDialogFragment;
import com.shagiev.konstantin.daybook.fragments.dialog.EditTaskDialogFragment;
import com.shagiev.konstantin.daybook.model.Task;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener,
        CurrentTasksFragment.OnDoneTaskListener, DoneTasksFragment.OnRestoreTaskListener, EditTaskDialogFragment.EditingTaskListener {


    private FragmentManager mFragmentManager;
    private TabAdapter mTabAdapter;
    private TasksFragment mCurrentTasksFragment;
    private TasksFragment mDoneTasksFragment;
    private SearchView mSearchView;

    public DBHelper mDBHelper;
    public static String[] PRIORITY_LEVELS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PRIORITY_LEVELS = getResources().getStringArray(R.array.priority_levels);

        mDBHelper = new DBHelper(getApplicationContext());

        mFragmentManager = getFragmentManager();

        AlarmHelper.getInstance().init(getApplicationContext());

        setUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
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

        mCurrentTasksFragment = (CurrentTasksFragment) mTabAdapter.getItem(TabAdapter.CURRENT_TASK_FRAGMENT_POSITION);

        mDoneTasksFragment = (DoneTasksFragment) mTabAdapter.getItem(TabAdapter.DONE_TASK_FRAGMENT_POSITION);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCurrentTasksFragment.findTasks(newText);
                mDoneTasksFragment.findTasks(newText);
                return false;
            }
        });

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
        mCurrentTasksFragment.addTask(task, true);
        Toast.makeText(this, this.getString(R.string.case_added), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, this.getString(R.string.case_not_added), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskDone(Task task) {
        mDoneTasksFragment.addTask(task, false);
    }

    @Override
    public void onTaskRestore(Task task) {
        mCurrentTasksFragment.addTask(task, false);
    }

    @Override
    public void onTaskEdited(Task task) {
        mCurrentTasksFragment.updateTask(task);
        mDBHelper.getDBManager().updateTask(task);
    }
}
