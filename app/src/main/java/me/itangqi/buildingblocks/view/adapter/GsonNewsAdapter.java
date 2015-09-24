package me.itangqi.buildingblocks.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.itangqi.buildingblocks.model.entity.DailyGson;

/**
 * Created by Troy on 2015/9/24.
 */
public class GsonNewsAdapter extends RecyclerView.Adapter<GsonNewsAdapter.ItemHolder> {

    public static final int P = 0;
    public static final int IMG = 1;
    public static final int STRONG = 2;
    public static final int BLOCKQUOTE = 3;

    private LinkedHashMap<String, String> mArticle;


    public GsonNewsAdapter(LinkedHashMap<String, String> article) {
        this.mArticle = article;
        List<String> mValues = (List<String>) article.values();
        Set<String> mKeys = article.keySet();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mArticle.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
