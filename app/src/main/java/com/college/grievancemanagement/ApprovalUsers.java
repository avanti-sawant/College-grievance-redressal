package com.college.grievancemanagement;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.college.fragment.FacultyFragment;
import com.college.fragment.ParentFragment;
import com.college.fragment.StudentFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ApprovalUsers extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private ParentFragment parentFragment;
    private StudentFragment studentFragment;
    private FacultyFragment facultyFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_users);
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        viewPager=(ViewPager)findViewById(R.id.view_pager);
        getSupportActionBar().setTitle("Approval Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout.addTab(tabLayout.newTab().setText("Parent"));
        tabLayout.addTab(tabLayout.newTab().setText("Student"));
        tabLayout.addTab(tabLayout.newTab().setText("Faculty"));
        parentFragment= new ParentFragment();
        studentFragment = new StudentFragment();
        facultyFragment = new FacultyFragment();
        tabLayout.setupWithViewPager(viewPager);
        //create viewpager adapter
        //here we will create inner class for adapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        //add fragments and set the adapter
        viewPagerAdapter.addFragment(parentFragment,"Parent");
        viewPagerAdapter.addFragment(studentFragment,"Student");
        viewPagerAdapter.addFragment(facultyFragment,"Faculty");

        viewPager.setAdapter(viewPagerAdapter);

//        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        //add fragment to the viewpager
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        //to setup title of the tab layout
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*Intent intent=new Intent(ApprovalUsers.this,AdminMenu.class);
                startActivity(intent);*/
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}