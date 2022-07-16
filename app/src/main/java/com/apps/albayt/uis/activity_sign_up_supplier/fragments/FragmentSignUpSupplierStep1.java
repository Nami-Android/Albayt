package com.apps.albayt.uis.activity_sign_up_supplier.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.albayt.BuildConfig;
import com.apps.albayt.R;
import com.apps.albayt.databinding.DialogChooseImageBinding;
import com.apps.albayt.databinding.FragmentSignUpSupplierStep1Binding;
import com.apps.albayt.model.SupplierSignUpModel;
import com.apps.albayt.share.Common;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_sign_up_supplier.SignUpSupplierActivity;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FragmentSignUpSupplierStep1 extends BaseFragment {
    private SignUpSupplierActivity activity;
    private FragmentSignUpSupplierStep1Binding binding;
    private SupplierSignUpModel model ;
    private ActivityResultLauncher<String[]> permissions;
    private ActivityResultLauncher<Intent> launcher;
    private String phone_code="",phone = "";
    private Uri outPutUri = null;
    private String imagePath = "";
    private int req;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SignUpSupplierActivity) context;
        permissions = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
            if (req == 1) {
                if (permissions.containsValue(false)) {
                    Toast.makeText(activity, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
                } else {
                    openCamera();
                }
            } else if (req == 2) {
                if (permissions.containsValue(false)) {
                    Toast.makeText(activity, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
                } else {
                    openGallery();
                }
            }
        });
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 2 && result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri uri = result.getData().getData();
                model.setPhoto_path(Common.getImagePath(activity, uri));

                Glide.with(this)
                        .asBitmap()
                        .load(uri)
                        .into(binding.image);
            } else if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                model.setPhoto_path(imagePath);


                Glide.with(this)
                        .asBitmap()
                        .load(outPutUri)
                        .into(binding.image);

            }
        });

    }

    public static FragmentSignUpSupplierStep1 newInstance(String phone_code,String phone) {
        FragmentSignUpSupplierStep1 fragment = new FragmentSignUpSupplierStep1();
        Bundle bundle = new Bundle();
        bundle.putString("phone_code",phone_code);
        bundle.putString("phone",phone);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            phone_code = getArguments().getString("phone_code","");
            phone = getArguments().getString("phone","");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_supplier_step1, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        model = new SupplierSignUpModel();
        model.setPhone_code(phone_code);
        model.setPhone(phone);

        binding.setModel(model);
        binding.image.setOnClickListener(view -> {
            openSheet();
        });
    }

    private void openSheet() {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        DialogChooseImageBinding imageBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.dialog_choose_image, null, false);
        dialog.setView(imageBinding.getRoot());
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        imageBinding.tvCamera.setOnClickListener(v -> {
            checkCameraPermission();
            dialog.dismiss();
        });
        imageBinding.tvGallery.setOnClickListener(v -> {
            checkPhotoPermission();
            dialog.dismiss();
        });
        imageBinding.tvCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }



    public void checkCameraPermission() {
        req = 1;
        String[] permissions = new String[]{WRITE_PERM, CAM_PERM};
        if (ContextCompat.checkSelfPermission(activity, WRITE_PERM) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity, CAM_PERM) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            this.permissions.launch(permissions);
        }
    }

    public void checkPhotoPermission() {
        req = 2;
        String[] permissions = new String[]{READ_PERM};
        if (ContextCompat.checkSelfPermission(activity, READ_PERM) == PackageManager.PERMISSION_GRANTED

        ) {
            openGallery();
        } else {
            this.permissions.launch(permissions);
        }
    }

    private void openCamera() {
        req = 1;
        outPutUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", getCameraOutPutFile());
        Log.e("outPutUri",outPutUri.toString());

        //01026638997
        if (outPutUri != null) {
            Intent intentCamera = new Intent();
            intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            launcher.launch(intentCamera);
        } else {
            Toast.makeText(activity, "You don't allow to access photos", Toast.LENGTH_SHORT).show();
        }


    }

    private void openGallery() {
        req = 2;
        Intent intentGallery = new Intent();
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        intentGallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentGallery.setType("image/*");
        launcher.launch(Intent.createChooser(intentGallery, "Choose photos"));


    }

    private File getCameraOutPutFile() {
        File file = null;
        String stamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageName = "JPEG_" + stamp + "_";

        File appFile = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            file = File.createTempFile(imageName, ".jpg", appFile);
            imagePath = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}