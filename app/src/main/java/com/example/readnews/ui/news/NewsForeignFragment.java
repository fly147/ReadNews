package com.example.readnews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnews.R;

import java.util.ArrayList;
import java.util.List;

public class NewsForeignFragment extends Fragment {

    private NewsForeignViewModel newsForeignViewModel;
    RecyclerView recyclerView;
    List<News> newsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_foreign, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsForeignViewModel = ViewModelProviders.of(this).get(NewsForeignViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        initPersonData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局
        // recyclerView.setLayoutManager(new GridLayoutManager(this,2));//线性宫格显示，类似gridview
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//线性宫格显示类似瀑布流
        recyclerView.setAdapter(new MyRecycleViewAdapter(newsList,getActivity()));

    }

    private void initPersonData() {
        newsList = new ArrayList<>();
        newsList.add(new News(getString(R.string.news_one_title), getString(R.string.news_one_desc), R.mipmap.news_one));
        newsList.add(new News(getString(R.string.news_two_title), getString(R.string.news_two_desc), R.mipmap.news_two));
        newsList.add(new News(getString(R.string.news_three_title), getString(R.string.news_three_desc), R.mipmap.news_three));
        newsList.add(new News(getString(R.string.news_four_title), getString(R.string.news_four_desc), R.mipmap.news_four));
    }


}
