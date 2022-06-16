package com.example.readnews.ui.article;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.example.readnews.MyApplication;
import com.example.readnews.R;
import com.example.readnews.SwipeRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArticleFragment extends Fragment {

    private ArticleViewModel mViewModel;
    private SwipeRecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    private int pageSize = 10;
    String url = new MyApplication().selectbypageurl;

    public static ArticleFragment newInstance() {
        return new ArticleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ArticleActivity.this, "点击编辑文章", Toast.LENGTH_SHORT).show();
                //跳转到编辑文章页面，返回来之后重新刷新列表
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.nav_add_article);
            }
        });
        recyclerView = (SwipeRecyclerView) view.findViewById(R.id.swipeRecyclerView);
        //set color
        recyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerView.getRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter=new MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchRData();
                    }
                }, 1000);

            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchMData();
                    }
                }, 1000);
            }
        });
        recyclerView.setRefreshing(true);
        return view;
    }
    public void fetchMData() {
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("offset", String.valueOf(mData.size()));
        params.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fetchMdata", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });

                //mData.clear();
                mData.addAll(list);
                System.out.print("mData: ");
                System.out.println(mData);
                recyclerView.complete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public void fetchRData() {
        RequestParams params = new RequestParams(url);
        //get
        params.addQueryStringParameter("offset", String.valueOf(0));
        params.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fetchRData", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                mData.clear();
                mData.addAll(list);
                recyclerView.complete();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        // TODO: Use the ViewModel

    }

    class  MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>
    {
        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView articlePicture;
            public TextView articleTitle;
            public TextView articleTime;

            public ViewHolder(View convertView) {
                super(convertView);
                articlePicture = (ImageView)convertView.findViewById(R.id.article_picture);
                articleTitle = (TextView)convertView.findViewById(R.id.article_title);
                articleTime = (TextView)convertView.findViewById(R.id.article_time);
            }
        }
        @NonNull
        @Override
        public MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.article_item,parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyRecycleViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            //picture
            Glide.with(getActivity()).load(new MyApplication().imagebaseurl+mData.get(position).get("articleImagePath").toString()).placeholder(R.mipmap.ic_launcher).into(holder.articlePicture);
            holder.articleTitle.setText((String)mData.get(position).get("articleTitle"));
            holder.articleTime.setText((String)mData.get(position).get("articleTime"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("id",mData.get(position).get("id").toString());
                    navController.navigate(R.id.nav_article_detail,bundle);

                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

}