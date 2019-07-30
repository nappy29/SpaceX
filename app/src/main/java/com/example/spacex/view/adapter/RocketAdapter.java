package com.example.spacex.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.spacex.R;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.databinding.RocketListItemBinding;
import com.example.spacex.view.callback.RocketClickCallback;

import java.util.List;
import java.util.Objects;

public class RocketAdapter extends RecyclerView.Adapter<RocketAdapter.RocketViewHolder> {

    List<? extends Rocket> rocketList;

    @Nullable
    private final RocketClickCallback rocketClickCallback;

    public RocketAdapter(@Nullable RocketClickCallback rocketClickCallback) {
        this.rocketClickCallback = rocketClickCallback;
    }

    public void setRocketList(final List<? extends Rocket> rocketList) {
        if (this.rocketList == null) {
            this.rocketList = rocketList;
            notifyItemRangeInserted(0, rocketList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RocketAdapter.this.rocketList.size();
                }

                @Override
                public int getNewListSize() {
                    return rocketList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RocketAdapter.this.rocketList.get(oldItemPosition).rocketName
                            .equals(rocketList.get(newItemPosition).rocketName);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Rocket rocket = rocketList.get(newItemPosition);
                    Rocket old = rocketList.get(oldItemPosition);
                    return rocket.rocketName.equals(old.rocketName)
                            && Objects.equals(rocket.rocketDescription, old.rocketDescription);
                }
            });
            this.rocketList = rocketList;
            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public RocketViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RocketListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.rocket_list_item,
                        viewGroup, false);

        binding.setCallback(rocketClickCallback);

        return new RocketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RocketAdapter.RocketViewHolder rocketViewHolder, int position) {

//        rocketViewHolder.binding.setRocket(rocketList.get(position));
//        rocketViewHolder.binding.executePendingBindings();

          rocketViewHolder.bind(rocketList.get(position));

    }

    @Override
    public int getItemCount() {
        return rocketList == null ? 0 : rocketList.size();
    }

    static class RocketViewHolder extends RecyclerView.ViewHolder {

        final RocketListItemBinding binding;

        public RocketViewHolder(RocketListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Rocket rocket){
            binding.setRocket(rocket);
            binding.executePendingBindings();

        }
    }
}