package com.apps.albayt.uis.activity_contractor_profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ActivityContractorProfileBinding;
import com.apps.albayt.model.ContractorModel;
import com.apps.albayt.mvvm.ActivityContractorProfileMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;

public class ContractorProfileActivity extends BaseActivity {
    private ActivityContractorProfileBinding binding;
    private ActivityContractorProfileMvvm mvvm;
    private String contractor_id = "";
    private ContractorModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contractor_profile);
        getDataFromIntent();
        initView();

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        contractor_id = intent.getStringExtra("data");
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityContractorProfileMvvm.class);
        binding.setLang(getLang());
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.getOnDataSuccess().observe(this, model -> {
            this.model = model;
            binding.setModel(model);
        });
        mvvm.getProfile(getLang(), contractor_id);
        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.getProfile(getLang(), contractor_id);
            binding.setModel(null);
        });

        binding.whatsApp.setOnClickListener(view -> {
            if (model.getContact_whats_up() != null && !model.getContact_whats_up().isEmpty()) {
                String contact = model.getPhone_code() + model.getContact_whats_up();// use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            } else {
                Toast.makeText(this, "Invalid Whats app number", Toast.LENGTH_SHORT).show();
            }
        });
        binding.llBack.setOnClickListener(view -> {
            finish();
        });
        binding.call.setOnClickListener(view -> {
            if (model.getContact_phone() != null && !model.getContact_phone().isEmpty()) {
                String phone = model.getPhone_code() + model.getContact_phone();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid contact phone number", Toast.LENGTH_SHORT).show();

            }
        });

        binding.llLocation.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("geo:" + model.getLatitude() + "," + model.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);


        });
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
    }



}