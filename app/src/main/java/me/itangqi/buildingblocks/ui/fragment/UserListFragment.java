package me.itangqi.buildingblocks.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;
import me.itangqi.buildingblocks.adapter.UserListAdapter;
import me.itangqi.buildingblocks.model.User;
import me.itangqi.buildingblocks.utils.GsonRequest;
import me.itangqi.buildingblocks.utils.RequestManager;
import me.itangqi.buildingblocks.utils.ZhuanLanApi;

public class UserListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<User> mUserleList = new ArrayList<>();
    private UserListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    @OnClick(R.id.fab)
    void fab_actions() {
        // TODO do what you want

    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);

        mFloatingActionButton.attachToRecyclerView(mRecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // specify an adapter
        mAdapter = new UserListAdapter(mUserleList);
        mRecyclerView.setAdapter(mAdapter);

        String[] ids = getActivity().getResources().getStringArray(R.array.people_ids);

        for (String id : ids) {
            GsonRequest<User> request = ZhuanLanApi.getUserInfoRequest(id);
            request.setSuccessListener(new Response.Listener<User>() {
                @Override
                public void onResponse(User response) {
                    mRecyclerView.setVisibility(View.VISIBLE);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onRefresh() {
        // TODO do really refresh
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
