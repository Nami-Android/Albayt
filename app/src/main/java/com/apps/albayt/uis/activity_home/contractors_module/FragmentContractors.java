package com.apps.albayt.uis.activity_home.contractors_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.ContractorAdapter;
import com.apps.albayt.adapter.SpinnerCityAdapter;
import com.apps.albayt.databinding.FragmentContractorsBinding;
import com.apps.albayt.model.CityModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.mvvm.FragmentContractorsMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_contractor_profile.ContractorProfileActivity;
import com.apps.albayt.uis.activity_home.HomeActivity;


public class FragmentContractors extends BaseFragment {
    private GeneralMvvm generalMvvm;
    private HomeActivity activity;
    private FragmentContractorsBinding binding;
    private FragmentContractorsMvvm mvvm;
    private SpinnerCityAdapter adapter;
    private ContractorAdapter contractorAdapter;
    private String selectedCityId=null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static FragmentContractors newInstance() {
        return new FragmentContractors();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contractors, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentContractorsMvvm.class);
        adapter = new SpinnerCityAdapter(activity);
        binding.spinner.setAdapter(adapter);
        contractorAdapter = new ContractorAdapter(activity, this, getLang());
        binding.recViewLayout.recView.setHasFixedSize(true);
        binding.recViewLayout.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewLayout.recView.setItemViewCacheSize(20);
        binding.recViewLayout.recView.setLayoutManager(new GridLayoutManager(activity, 2));
        contractorAdapter.setHasStableIds(true);
        binding.recViewLayout.recView.setAdapter(contractorAdapter);
        binding.recViewLayout.tvNoData.setText(R.string.no_contractors);

        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);

            } else {
                binding.progBar.setVisibility(View.GONE);

            }
        });
        mvvm.getOnCityDataSuccess().observe(activity, cities -> {
            if (adapter!=null){
                adapter.updateList(cities);
            }
        });
        mvvm.getOnDataSuccess().observe(activity, contractors -> {
            Log.e("size",contractors.size()+"");
            if (contractors.size() > 0) {
                if (contractorAdapter!=null){
                    contractorAdapter.updateList(contractors);
                    binding.recViewLayout.tvNoData.setVisibility(View.GONE);
                }
            }else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCityId =((CityModel) adapterView.getSelectedItem()).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.search.setOnClickListener(view -> {
            String query = binding.edtSearch.getText().toString().trim();
            if (selectedCityId!=null){

                mvvm.search(selectedCityId,query.isEmpty()?null:query,getLang());
            }
        });

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(()->{
            mvvm.search(selectedCityId,binding.edtSearch.getText().toString().trim(),getLang());
        });
        mvvm.getCities(getLang());


    }


    public void navigateToContractorProfile(UserModel.Data data) {
        Intent intent = new Intent(activity, ContractorProfileActivity.class);
        intent.putExtra("data",data.getId());
        startActivity(intent);
    }
}