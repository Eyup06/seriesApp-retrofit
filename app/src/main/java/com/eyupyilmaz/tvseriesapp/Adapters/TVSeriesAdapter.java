package com.eyupyilmaz.tvseriesapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eyupyilmaz.tvseriesapp.Listeners.TVSeriesListener;
import com.eyupyilmaz.tvseriesapp.R;
import com.eyupyilmaz.tvseriesapp.databinding.TvShowRowBinding;
import com.eyupyilmaz.tvseriesapp.models.TVSeries;

import java.util.List;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.TVSeriesViewHolder> {

    private List<TVSeries> tvSeries;
    private LayoutInflater layoutInflater;
    private TVSeriesListener tvSeriesListener;

    public TVSeriesAdapter(List<TVSeries> tvSeries, TVSeriesListener tvSeriesListener) {
        this.tvSeries = tvSeries;
        this.tvSeriesListener = tvSeriesListener;
    }

    @NonNull
    @Override
    public TVSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        TvShowRowBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.tv_show_row,
                parent,
                false
        );
        return new TVSeriesViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVSeriesViewHolder holder, int position) {
        holder.bindTVSeries(tvSeries.get(position));
    }

    @Override
    public int getItemCount() {
        return tvSeries.size();
    }

    class TVSeriesViewHolder extends RecyclerView.ViewHolder{

        private TvShowRowBinding tvShowRowBinding;

        public TVSeriesViewHolder(TvShowRowBinding tvShowRowBinding){
            super(tvShowRowBinding.getRoot());
            this.tvShowRowBinding = tvShowRowBinding;
        }

        public void bindTVSeries(TVSeries tvSeries){
            tvShowRowBinding.setTvShow(tvSeries);
            tvShowRowBinding.executePendingBindings();
            tvShowRowBinding.getRoot().setOnClickListener(view ->
                    tvSeriesListener.onTVSeriesClicked(tvSeries));
        }
    }
}
