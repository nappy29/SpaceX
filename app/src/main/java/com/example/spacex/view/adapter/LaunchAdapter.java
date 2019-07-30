package com.example.spacex.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacex.R;
import com.example.spacex.data.model.others.Launch;
import com.example.spacex.databinding.LaunchHeaderViewBinding;
import com.example.spacex.databinding.LaunchListItemBinding;
import com.example.spacex.databinding.RocketListItemBinding;
import com.example.spacex.view.ui.rocketdetails.RocketViewModel;
import com.saber.stickyheader.stickyView.StickHeaderRecyclerView;

public class LaunchAdapter extends StickHeaderRecyclerView<Launch, HeaderDataImpl> {

    String[] arrayofyears;
    int count = 0;
    String launch_year;

    public LaunchAdapter(String[] array){
        arrayofyears = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HeaderDataImpl.HEADER_TYPE_1:
                LaunchHeaderViewBinding binding = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.launch_header_view,
                                parent, false);

                return new HeaderViewHolder(binding);

            default:
                LaunchListItemBinding binding2 = DataBindingUtil
                        .inflate(LayoutInflater.from(parent.getContext()), R.layout.launch_list_item,
                                parent, false);
                return new ViewHolder(binding2);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).bindData(position);
        } else if (holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).bindData(position);
        }
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

        /*TextView tv = header.findViewById(R.id.tvHeader);
        tv.setText(String.valueOf(headerPosition / 5));*/
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        LaunchHeaderViewBinding binding;

        HeaderViewHolder(LaunchHeaderViewBinding bind) {
            super(bind.getRoot());
            binding = bind;
        }

        void bindData(int position) {

//            Log.d("Item position", String.valueOf(arrayofyears.length));
            binding.headerText.setText(arrayofyears[count++]);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final LaunchListItemBinding binding;

        ViewHolder(LaunchListItemBinding  bind) {
            super(bind.getRoot());
            binding = bind;
        }

        void bindData(int position) {
            binding.setLaunch(getDataInPosition(position));

        }
    }
}
