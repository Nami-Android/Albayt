package com.apps.albayt.uis.activity_home.address_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.AddressAdapter;
import com.apps.albayt.databinding.FragmentMyAddressesBinding;
import com.apps.albayt.model.AddressModel;
import com.apps.albayt.mvvm.FragmentMyAddressesMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;


public class FragmentMyAddresses extends BaseFragment {
    private HomeActivity activity;
    private FragmentMyAddressesMvvm mvvm;
    private FragmentMyAddressesBinding binding;
    private GeneralMvvm generalMvvm;
    private AddressAdapter addressAdapter;
    private int selectedAddressPos = -1;
    private String action = "";

    public static FragmentMyAddresses newInstance() {
        return new FragmentMyAddresses();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_addresses, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentMyAddressesMvvm.class);

        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        View view = activity.setUpToolbar(binding.toolbar, getString(R.string.delivery_addresses), R.color.white, R.color.black, R.drawable.small_rounded_grey4, false);
        view.setOnClickListener(v -> {
            generalMvvm.onHomeBackNavigate().setValue(true);

        });

        generalMvvm.getOnAddressAdded().observe(activity, addressModel -> {
            if (mvvm.getOnDataSuccess().getValue() != null) {
                mvvm.getOnDataSuccess().getValue().add(0, addressModel);
                if (addressAdapter != null) {
                    addressAdapter.notifyItemInserted(0);
                }
            }
        });
        generalMvvm.getOnAddressUpdated().observe(activity, addressModel -> {
            mvvm.getAddresses(getUserModel().getData().getId());

        });

        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.getOnDataSuccess().observe(activity, list -> {
            if (addressAdapter != null) {
                addressAdapter.updateList(list);
            }
        });

        mvvm.getOnDeletedSuccess().observe(activity,pos->{
            if (addressAdapter!=null&&mvvm.getOnDataSuccess().getValue()!=null&&mvvm.getOnDataSuccess().getValue().size()>0){
                mvvm.getAddresses(getUserModel().getData().getId());
            }
        });
        binding.llNewAddress.setOnClickListener(v -> {
            generalMvvm.getAddAddressFragmentAction().setValue("add");
            generalMvvm.onHomeNavigate().setValue(5);
        });


        addressAdapter = new AddressAdapter(activity, this);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(addressAdapter);
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getAddresses(getUserModel().getData().getId()));
        if (getUserModel() != null) {
            mvvm.getAddresses(getUserModel().getData().getId());
        }
    }


    public void setItemAddress(AddressModel addressModel, int adapterPosition) {
        if (generalMvvm.getMyAddressFragmentAction().getValue() != null && generalMvvm.getMyAddressFragmentAction().getValue().equals("forOrder")) {
            generalMvvm.getOnAddressSelectedForOrder().setValue(addressModel);
            generalMvvm.onHomeBackNavigate().setValue(true);
            generalMvvm.getMyAddressFragmentAction().setValue("");
        }
    }

    public void delete(AddressModel addressModel, int adapterPosition) {
        mvvm.deleteAddress(getUserModel(),addressModel.getId(),activity,adapterPosition);
    }

    public void edit(AddressModel addressModel, int adapterPosition) {
        generalMvvm.getAddAddressFragmentAction().setValue("update");
        generalMvvm.getOnAddressSelectedForUpdated().setValue(addressModel);
        generalMvvm.onHomeNavigate().setValue(5);


    }
}