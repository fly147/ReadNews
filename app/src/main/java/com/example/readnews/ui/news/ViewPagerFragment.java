package com.example.readnews.ui.news;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readnews.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    private ViewPagerViewModel mViewModel;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles;
    private List<Fragment> fragments;

    public static ViewPagerFragment newInstance() {
        return new ViewPagerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        tableLayout = (TabLayout) view.findViewById(R.id.tabs2);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);

        fragments = new ArrayList<>();
        NewsTopFragment newsTopFragment = new NewsTopFragment();
        fragments.add(newsTopFragment);
        NewsDomesticFragment newsDomesticFragment = new NewsDomesticFragment();
        fragments.add(newsDomesticFragment);
        NewsForeignFragment newsForeignFragment = new NewsForeignFragment();
        fragments.add(newsForeignFragment);

        titles = new ArrayList<>();
        titles.add("推荐");
        titles.add("国内");
        titles.add("国外");

        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(fragmentViewPagerAdapter);
        tableLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewPagerViewModel.class);
        // TODO: Use the ViewModel
    }
    class FragmentViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private List<String> titles;

        public FragmentViewPagerAdapter(FragmentManager manager,List<Fragment> fragments,List<String> titles){
            super(manager);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}


