package com.example.readnews.ui.news;

import androidx.lifecycle.ViewModelProviders;

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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel mViewModel;
    private SwipeRecyclerView recyclerView;
    private NewsFragment.MyRecycleViewAdapter adapter;
    private List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String, Object>> mData = new ArrayList<HashMap<String, Object>>();
    private int pageSize = 10;
    String typeId;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        Bundle bundle = getArguments();
        typeId = bundle.getString("typeId", "509");
        Log.i("typeId",typeId);
        recyclerView = (SwipeRecyclerView) view.findViewById(R.id.swipeRecyclerView2);
        //set color
        recyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerView.getRecyclerView().setLayoutManager(new GridLayoutManager(getActivity(), 1));
        adapter=new NewsFragment.MyRecycleViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {

            @Override
            public void onRefresh() {
                Log.i("huhao","OnRefresh");
                if (getActivity() == null)
                    return;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fetchRData();
                    }
                }, 1000);

            }

            @Override
            public void onLoadMore() {
                Log.i("huhao","OnLoadMore");
                if (getActivity() == null)
                    return;
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
        //查询数据库中数据
        String url2 = new MyApplication().NewsSelectUrl;
        RequestParams params2 = new RequestParams(url2);
        params2.addQueryStringParameter("typeId",typeId);
        params2.addQueryStringParameter("offset", String.valueOf(mData.size()));
        params2.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("fetchMdata", result);
                list = JSON.parseObject(result,
                        new TypeReference<List<HashMap<String, Object>>>() {
                        });
                //mData.clear();
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

    public void fetchRData() {
//        //往数据库添加数据
        String url = new MyApplication().NewsInsertUrl;
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("typeId",typeId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("insertNews",result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("insertNewsError",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        //查询数据库中数据
        String url2 = new MyApplication().NewsSelectUrl;
        RequestParams params2 = new RequestParams(url2);
        params2.addQueryStringParameter("typeId",typeId);
        params2.addQueryStringParameter("offset", String.valueOf(mData.size()));
        params2.addQueryStringParameter("pagesize", String.valueOf(pageSize));
        x.http().get(params2, new Callback.CommonCallback<String>() {
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
                Log.i("selectNewsError",ex.toString());
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
    public void onResume() {
        super.onResume();
        Log.i("huhao","Resume");
//        fetchRData();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("huhao","onPause");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        // TODO: Use the ViewModel
    }


    class  MyRecycleViewAdapter extends RecyclerView.Adapter<NewsFragment.MyRecycleViewAdapter.ViewHolder>
    {
        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView newsPhoto;
            public TextView newsTitle;
            public TextView newsDesc;

            public ViewHolder(View convertView) {
                super(convertView);
                newsPhoto = (ImageView)convertView.findViewById(R.id.news_photo);
                newsTitle = (TextView)convertView.findViewById(R.id.news_title);
                newsDesc = (TextView)convertView.findViewById(R.id.news_desc);
            }
        }
        @NonNull
        @Override
        public NewsFragment.MyRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(getActivity()).inflate(R.layout.news_item,parent, false);
            return new NewsFragment.MyRecycleViewAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsFragment.MyRecycleViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            //picture
            Glide.with(getActivity()).load(mData.get(position).get("imgList").toString()).placeholder(R.mipmap.ic_launcher).into(holder.newsPhoto);
            holder.newsTitle.setText((String)mData.get(position).get("title"));
            holder.newsDesc.setText((String)mData.get(position).get("digest"));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insertNewsDetail(mData.get(position).get("newsId").toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }
    }

    private void insertNewsDetail(String newsId) {
        String url = new MyApplication().NewsDetailInsertUrl;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("newsId",newsId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("insert_newsdetail",result);
                if(result.equals("insert failed")){
                    Log.i("huhao","insert failed");
                }else{
                    NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                    Bundle bundle=new Bundle();
                    bundle.putString("newsId",newsId);
                    navController.navigate(R.id.nav_news_detail,bundle);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("newsdetail_error",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}

