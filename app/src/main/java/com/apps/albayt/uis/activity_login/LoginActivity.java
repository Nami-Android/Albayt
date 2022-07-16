package com.apps.albayt.uis.activity_login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.apps.albayt.R;
import com.apps.albayt.adapter.CountryCodeAdapter;
import com.apps.albayt.databinding.ActivityLoginBinding;
import com.apps.albayt.databinding.DailogVerificationCodeBinding;
import com.apps.albayt.model.CountryCodeModel;
import com.apps.albayt.model.LoginModel;
import com.apps.albayt.mvvm.ActivityLoginMvvm;
import com.apps.albayt.share.Common;
import com.apps.albayt.uis.activity_base.BaseActivity;
import com.apps.albayt.uis.activity_choose_user_type.ChooseUserTypeActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String phone_code = "";
    private String phone = "";
    private LoginModel model;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityLoginMvvm mvvm;
    private DailogVerificationCodeBinding dailogVerificationCodeBinding;
    private AlertDialog builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        binding.setLang(getLang());
        setUpToolbar(binding.toolbar, getString(R.string.login), R.color.white, R.color.black, 0, true);
        model = new LoginModel();
        binding.setModel(model);
        setUpSpinner();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            ProgressDialog dialog = Common.createProgressDialog(this, getResources().getString(R.string.wait));
            dialog.setCancelable(false);
            dialog.show();
            mvvm.login(this,model,dialog);
           /* mvvm.sendSmsCode(model,this);
            createVerificationCodeDialog();*/
        });

        mvvm.getTime().observe(this, time -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvResend.setText(time);

            }
        });

        mvvm.canResend().observe(this, canResend -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvResend.setEnabled(canResend);
                if (canResend) {
                    dailogVerificationCodeBinding.tvResend.setText(getString(R.string.resend));

                }
            }

        });
        mvvm.getUserData().observe(this, userModel -> {
            if (userModel != null) {
                setUserModel(userModel);
                setResult(RESULT_OK);
                finish();
                if(builder!=null){
                    builder.dismiss();
                    mvvm.stopTimer();
                }
            } else {
                if(builder!=null){
                    builder.dismiss();
                    mvvm.stopTimer();
                }
               navigateToChooseUserTypeActivity();
            }
        });

    }

    private void setUpSpinner() {
        List<CountryCodeModel> list = new ArrayList<>();
        list.add(new CountryCodeModel(R.drawable.saudi_arabia, getString(R.string.saudi_arabia), "+966"));
        list.add(new CountryCodeModel(R.drawable.egypt_flag, getString(R.string.egypt), "+20"));
        CountryCodeAdapter adapter = new CountryCodeAdapter(this);
        adapter.updateList(list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CountryCodeModel model = (CountryCodeModel) parent.getAdapter().getItem(position);
                LoginActivity.this.model.setPhone_code(model.getCode());
                binding.setModel(LoginActivity.this.model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void createVerificationCodeDialog() {
        builder = new AlertDialog.Builder(this)
                .create();
        builder.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dailogVerificationCodeBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dailog_verification_code, null, false);
        dailogVerificationCodeBinding.setModel(model);
        builder.setView(dailogVerificationCodeBinding.getRoot());
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        dailogVerificationCodeBinding.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dailogVerificationCodeBinding.btnVerify.setEnabled(s.toString().length() == 6);

            }
        });
        dailogVerificationCodeBinding.tvResend.setOnClickListener(v -> mvvm.reSendSmsCode(model,this));
        builder.show();

        dailogVerificationCodeBinding.btnVerify.setOnClickListener(v -> {

        });
        builder.setOnCancelListener(dialog -> mvvm.stopTimer());

    }

    private void navigateToChooseUserTypeActivity() {
        Intent intent = new Intent(this, ChooseUserTypeActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        launcher.launch(intent);
    }
}