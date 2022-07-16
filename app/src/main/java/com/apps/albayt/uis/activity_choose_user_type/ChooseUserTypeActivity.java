package com.apps.albayt.uis.activity_choose_user_type;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ActivityChooseUserTypeBinding;
import com.apps.albayt.databinding.ActivityLoginBinding;
import com.apps.albayt.databinding.DailogVerificationCodeBinding;
import com.apps.albayt.model.LoginModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.mvvm.ActivityLoginMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;
import com.apps.albayt.uis.activity_sign_up.SignUpActivity;
import com.apps.albayt.uis.activity_sign_up_supplier.SignUpSupplierActivity;

public class ChooseUserTypeActivity extends BaseActivity {
    private ActivityChooseUserTypeBinding binding;
    private String phone_code = "";
    private String phone = "";
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_user_type);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");
    }


    private void initView() {
        binding.setLang(getLang());
        setUpToolbar(binding.toolbar, getString(R.string.sign_up), R.color.white, R.color.black, 0, true);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });

        binding.llUser.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            intent.putExtra("phone_code",phone_code);
            intent.putExtra("phone",phone);
            launcher.launch(intent);
        });

        binding.v1.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpSupplierActivity.class);
            intent.putExtra("phone_code",phone_code);
            intent.putExtra("phone",phone);
            launcher.launch(intent);
        });



    }
}