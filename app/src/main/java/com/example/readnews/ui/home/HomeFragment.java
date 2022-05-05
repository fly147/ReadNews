package com.example.readnews.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readnews.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recyclerView;
    List<News> newsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        initPersonData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局
        // recyclerView.setLayoutManager(new GridLayoutManager(this,2));//线性宫格显示，类似gridview
        // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));//线性宫格显示类似瀑布流
        recyclerView.setAdapter(new MyRecycleViewAdapter());

    }

    private void initPersonData() {
        newsList = new ArrayList<>();
        newsList.add(new News(getString(R.string.news_one_title), getString(R.string.news_one_desc), R.mipmap.news_one));
        newsList.add(new News(getString(R.string.news_two_title), getString(R.string.news_two_desc), R.mipmap.news_two));
        newsList.add(new News(getString(R.string.news_three_title), getString(R.string.news_three_desc), R.mipmap.news_three));
        newsList.add(new News(getString(R.string.news_four_title), getString(R.string.news_four_desc), R.mipmap.news_four));
    }


    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {


        public class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView news_photo;
            TextView news_title;
            TextView news_desc;


            public ViewHolder(View convertView) {
                super(convertView);
                cardView = (CardView) convertView.findViewById(R.id.card_view);
                news_photo = (ImageView) convertView.findViewById(R.id.news_photo);
                news_title = (TextView) convertView.findViewById(R.id.news_title);
                news_desc = (TextView) convertView.findViewById(R.id.news_desc);
                //设置TextView背景为半透明
                news_title.setBackgroundColor(Color.argb(20, 0, 0, 0));
            }
        }

        @NonNull
        @Override
        public MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.news_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.ViewHolder holder, final int position) {
            holder.news_photo.setImageResource(newsList.get(position).getPhotoId());
            holder.news_title.setText(newsList.get(position).getTitle());
            holder.news_desc.setText(newsList.get(position).getDesc());
        }


        @Override
        public int getItemCount() {
            return newsList.size();
        }
    }

}
