package me.itangqi.buildingblocks.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.adapter.DailyListAdapter;
import me.itangqi.buildingblocks.api.ZhihuApi;
import me.itangqi.buildingblocks.model.Daily;
import me.itangqi.buildingblocks.model.DailyResult;
import me.itangqi.buildingblocks.utils.CommonUtils;
import me.itangqi.buildingblocks.utils.PrefUtils;
import me.itangqi.buildingblocks.widget.SimpleDividerItemDecoration;

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<Daily> mNewsList = new ArrayList<>();
    private List<Daily> mCacheDaliyList = new ArrayList<>();
    private DailyListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String date;
    AsyncHttpClient mClient = new AsyncHttpClient();
    AsyncHttpResponseHandler mResponseHandlerGetNews = new BaseJsonHttpResponseHandler<DailyResult>() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, DailyResult response) {
            mNewsList.clear();
            if (response.stories != null) {
                for (Daily item : response.stories) {
                    mNewsList.add(item);
                }
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                try {
                    CommonUtils.serializDaily(date, mNewsList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, DailyResult errorResponse) {
            Snackbar.make(getView(), R.string.snack_network_error, Snackbar.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected DailyResult parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
            Gson gson = new Gson();
            return gson.fromJson(rawJsonData, DailyResult.class);
        }
    };
    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        // TODO you can use bundle to transfer data
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            date = bundle.getString("date");
            if (PrefUtils.isEnableCache() && CommonUtils.hasSerializedObject(date)) {
                try {
                    mCacheDaliyList = CommonUtils.deserializDaily(date);
                    Log.d("readSerializedOB", "mCacheDailyList的数量->" + mCacheDaliyList.size());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            setRetainInstance(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);
        ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity()
        ));

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);
        List<Daily> toAddList = (mNewsList.size() == 0)
                ? mCacheDaliyList : mNewsList;
        if (mNewsList.size() != 0) {
            mCacheDaliyList.clear();
        }
        mAdapter = new DailyListAdapter(getActivity(), toAddList);
        mRecyclerView.setAdapter(mAdapter);
        onRefresh();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onRefresh() {
        String url = ZhihuApi.getDailyNews(date);
        mClient.get(getActivity(), url, mResponseHandlerGetNews);
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
