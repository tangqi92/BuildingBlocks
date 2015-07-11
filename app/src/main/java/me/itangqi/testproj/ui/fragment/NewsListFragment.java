package me.itangqi.testproj.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.itangqi.testproj.R;
import me.itangqi.testproj.adapter.NewsListAdapter;
import me.itangqi.testproj.bean.DailyNews;

public class NewsListFragment extends Fragment {
    private List<DailyNews> newsList = new ArrayList<>();
    private NewsListAdapter mNewsAdapter;

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
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mNewsAdapter = new NewsListAdapter(newsList);
        mRecyclerView.setAdapter(mNewsAdapter);

        return view;
    }

    public void initData() {
        DailyNews dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 1");


        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 2");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 3");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 4");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 5");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 6");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 7");
        newsList.add(dailyNews);

        dailyNews = new DailyNews();
        dailyNews.setDailyTitle("Tile 8");
        newsList.add(dailyNews);
    }
}
