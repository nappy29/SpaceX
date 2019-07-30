package com.example.spacex.view.ui.rocketlist;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacex.R;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.databinding.FragmentRocketListBinding;
import com.example.spacex.di.Injectable;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.rx.SchedulerProvider;
import com.example.spacex.view.adapter.RocketAdapter;
import com.example.spacex.view.callback.RocketClickCallback;
import com.example.spacex.view.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class RocketListFragment extends Fragment implements Injectable {

    public static final String TAG = "RocketListFragment";

    private LiveData<List<Rocket>> rocketListObservable;

    @Inject
    ApiHelperInterface apiHelperInterface;

    @Inject
    SchedulerProvider schedulerProvider;

    private RocketAdapter rocketAdapter;
    private FragmentRocketListBinding binding;

    protected RocketListViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        AndroidSupportInjection.inject(this);
//        super.onCreate(savedInstanceState);
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rocket_list, container, false);

        rocketAdapter = new RocketAdapter(rocketClickCallback);
//        binding.rocketList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rocketList.setAdapter(rocketAdapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RocketListViewModel.class);

        rocketListObservable = viewModel.fetchRocketList(apiHelperInterface, schedulerProvider, getActivity());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.imgBut.setOnClickListener(view -> {
            binding.setIsLoading(true);
            binding.loadingProjects.setText("Loading RocketList");
            binding.imgBut.setVisibility(View.GONE);
            rocketListObservable = viewModel.fetchRocketList(apiHelperInterface, schedulerProvider, getActivity());

//            observeViewModel(viewModel);
        });

        observeViewModel(viewModel);
    }

    private void observeViewModel(RocketListViewModel viewModel) {
        // Update the list when the data changes
        if(viewModel != null){

            viewModel.getRocketListObservable().observe(this, rockets -> {
                if (rockets != null) {

                    binding.setIsLoading(false);
                    binding.imgBut.setVisibility(View.INVISIBLE);
                    rocketAdapter.setRocketList(rockets);
                }
            });


            viewModel.errorObservable.observe(this, error -> {
                        binding.setIsLoading(true);
                        binding.loadingProjects.setText(error);
                        binding.imgBut.setVisibility(View.VISIBLE);
                    }
            );
        }

    }

    private final RocketClickCallback rocketClickCallback = new RocketClickCallback() {
        @Override
        public void onClick(Rocket rocket) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(rocket);
            }
        }
    };

}
