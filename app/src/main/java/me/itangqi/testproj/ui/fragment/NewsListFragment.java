package me.itangqi.testproj.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.adapter.NewsListAdapter;
import me.itangqi.testproj.bean.User;
import me.itangqi.testproj.data.GsonRequest;
import me.itangqi.testproj.data.RequestManager;
import me.itangqi.testproj.utils.ZhuanLanApi;
import me.itangqi.testproj.view.circularprogress.CircularLoadingView;

public class NewsListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private List<User> newsList = new ArrayList<>();
    private NewsListAdapter mAdapter;
    private CircularLoadingView mLoadingView;


    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mLoadingView = (CircularLoadingView) view.findViewById(R.id.v_loading);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new NewsListAdapter(newsList);
        mRecyclerView.setAdapter(mAdapter);

        String[] ids = getActivity().getResources().getStringArray(R.array.people_ids);

        for (String id : ids) {
            GsonRequest<User> request = ZhuanLanApi.getUserInfoRequest(id);
            request.setSuccessListener(new Response.Listener<User>() {
                @Override
                public void onResponse(User response) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mLoadingView.setVisibility(View.GONE);
                    mAdapter.add(response);
                }
            });
            RequestManager.addRequest(request, this);
        }
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
        RequestManager.getRequestQueue().cancelAll(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RequestManager.getRequestQueue().cancelAll(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}
