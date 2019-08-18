package com.example.spacex.view.ui.rocketdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.spacex.R;
import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.databinding.FragmentDetailsBinding;
import com.example.spacex.di.Injectable;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.rx.AppSchedulerProvider;
import com.example.spacex.view.adapter.HeaderDataImpl;
import com.example.spacex.view.adapter.LaunchAdapter;
import com.saber.stickyheader.stickyView.StickHeaderItemDecoration;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class RocketDetailsFragment extends Fragment implements Injectable {

    private static final String KEY_Rocket_Value = "rocket_value";

    private FragmentDetailsBinding binding;

    protected RocketViewModel rocketViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    ApiHelperInterface apiHelperInterface;

    @Inject
    AppSchedulerProvider appSchedulerProvider;
    private LiveData<List<Launch>> launchListObservable;
    private LaunchAdapter launchAdapter;
    private String rocket_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return (View) binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        rocketViewModel = ViewModelProviders.of(this, viewModelFactory).get(RocketViewModel.class);

        rocket_id = ((Rocket) getArguments().getSerializable(KEY_Rocket_Value)).getRocketId();

        Log.d("Rocket Id", rocket_id);

        launchListObservable = rocketViewModel.fetchLaunchList(apiHelperInterface, appSchedulerProvider, getActivity(), rocket_id);

    }

    private void setUpAdapter(LaunchAdapter launchAdapter) {


        if(rocketViewModel.getGroupOfList().size() > 0)
            for (List<Launch> group : rocketViewModel.getGroupOfList()) {
                HeaderDataImpl headerData = new HeaderDataImpl(HeaderDataImpl.HEADER_TYPE_1, R.layout.launch_header_view);
                launchAdapter.setHeaderAndData(group, headerData);
            }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rocketViewModel.setRocket((Rocket) getArguments().getSerializable(KEY_Rocket_Value));

        setUpToolbar();

        binding.setVm(rocketViewModel);
        binding.setIsLoading(true);

        observeViewModel(rocketViewModel);
    }

    private void observeViewModel(final RocketViewModel viewModel) {
        // Observe project data
        launchListObservable.observe(this, launches -> {
            if(launches != null){

                // verify if there is a list of launches available
                if (rocketViewModel.getGroupOfList().size() > 0){

                    binding.linechart.setVisibility(View.VISIBLE);

                    rocketViewModel.getValueLines().setColor(getResources().getColor(R.color.colorAccent));

                    binding.linechart.addSeries(rocketViewModel.getValueLines());
                    binding.linechart.startAnimation();

                    launchAdapter = new LaunchAdapter(rocketViewModel.getArrayOfYearsFromGroups());

                    setUpAdapter(launchAdapter);

                    binding.launchList.setAdapter(launchAdapter);
                    binding.launchList.addItemDecoration(new StickHeaderItemDecoration(launchAdapter));
                }
                else
                    binding.noResultsText.setVisibility(View.VISIBLE);

                binding.setIsLoading(false);

            }

        });
    }

    public static RocketDetailsFragment forRocket(Rocket rocket) {
        RocketDetailsFragment fragment = new RocketDetailsFragment();
        Bundle args = new Bundle();

        args.putSerializable(KEY_Rocket_Value, rocket);
        fragment.setArguments(args);

        return fragment;
    }

    public void setUpToolbar(){

        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.collapsibleToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        binding.toolBar.setNavigationOnClickListener(view -> getActivity().onBackPressed());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

    }

}
