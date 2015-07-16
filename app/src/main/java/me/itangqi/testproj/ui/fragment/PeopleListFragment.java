package me.itangqi.testproj.ui.fragment;

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
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.mrengineer13.snackbar.SnackBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.testproj.R;
import me.itangqi.testproj.adapter.PeopleListAdapter;
import me.itangqi.testproj.bean.User;
import me.itangqi.testproj.data.GsonRequest;
import me.itangqi.testproj.data.RequestManager;
import me.itangqi.testproj.utils.ZhuanLanApi;

public class PeopleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    //    private RecyclerView mRecyclerView;
    private List<User> peopleList = new ArrayList<>();
    private PeopleListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Bind(R.id.cardList)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.feed_floating_actionButton)
    FloatingActionButton mFloatingActionButton;

    @OnClick(R.id.feed_floating_actionButton)
    void feed_multiple_actions() {
        new SnackBar.Builder(getActivity())
                .withMessage("This library is awesome!") // OR
                .withActionMessage("Action") // OR
                .show();
    }

    public static PeopleListFragment newInstance() {
        PeopleListFragment fragment = new PeopleListFragment();
        // you can use bundle to transfer data
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
        View view = inflater.inflate(R.layout.fragment_people_list, container, false);
        ButterKnife.bind(this, view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.primary);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // specify an adapter
        mAdapter = new PeopleListAdapter(peopleList);
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
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
