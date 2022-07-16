package com.apps.albayt.uis.activity_home.suppliers_module;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.apps.albayt.R;
import com.apps.albayt.adapter.SpinnerCityAdapter;
import com.apps.albayt.databinding.FragmentSuppliersBinding;
import com.apps.albayt.model.CityModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.mvvm.FragmentSuppliersMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class FragmentSuppliers extends BaseFragment implements OnMapReadyCallback {
    private GeneralMvvm generalMvvm;
    private HomeActivity activity;
    private FragmentSuppliersBinding binding;
    private FragmentSuppliersMvvm mvvm;
    private GoogleMap mMap;
    private SpinnerCityAdapter adapter;
    private String selectedCityId=null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static FragmentSuppliers newInstance() {
        return new FragmentSuppliers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suppliers, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentSuppliersMvvm.class);
        adapter = new SpinnerCityAdapter(activity);
        binding.spinner.setAdapter(adapter);

        mvvm.getIsLoading().observe(activity, isLoading -> {
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
        mvvm.getOnDataSuccess().observe(activity, suppliers -> {
            Log.e("size",suppliers.size()+"");
            if (suppliers.size() > 0) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (UserModel.Data model : suppliers) {
                    if (model.getLatitude() != null && !model.getLatitude().isEmpty() && model.getLongitude() != null && !model.getLongitude().isEmpty()) {
                        double lat = Double.parseDouble(model.getLatitude());
                        double lng = Double.parseDouble(model.getLongitude());
                        builder.include(new LatLng(lat, lng));
                        addMarker(new LatLng(lat, lng), model);
                    }
                }
                zoom(builder.build());
            }else {
                mMap.clear();
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

        setUpMap();

    }

    private void setUpMap() {
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (fragment!=null){
            fragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setBuildingsEnabled(false);
            mMap.setIndoorEnabled(false);
            mvvm.getCities(getLang());
            mMap.setInfoWindowAdapter(new WindowInfoAdapter());
        }
    }

    private void addMarker(LatLng latLng, UserModel.Data model) {
        MarkerOptions options = new MarkerOptions();
        options.draggable(false);
        options.position(latLng);
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.pin3)));
        Marker marker = mMap.addMarker(options);
        marker.setTag(model);
        marker.showInfoWindow();



    }

    private void zoom(LatLngBounds bounds) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }

    public class WindowInfoAdapter implements GoogleMap.InfoWindowAdapter {
        private LayoutInflater inflater = LayoutInflater.from(activity);

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            UserModel.Data model = (UserModel.Data) marker.getTag();

           if (model!=null){
               View view = inflater.inflate(R.layout.map_window_inf,null,false);
               TextView tvName = view.findViewById(R.id.tvName);
               TextView tvDetails = view.findViewById(R.id.tvDetails);
               tvDetails.setPaintFlags(tvDetails.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
               ImageView logo = view.findViewById(R.id.image);
               tvName.setText(model.getFull_name());
               marker.setTag(model);
               Glide.with(activity)
                       .asBitmap()
                       .load(Uri.parse(model.getLogo()))
                       .centerCrop()
                       .into(new SimpleTarget<Bitmap>() {
                           @Override
                           public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                               logo.setImageBitmap(resource);
                               if (marker.isInfoWindowShown()){
                                   marker.hideInfoWindow();
                                   marker.showInfoWindow();
                               }
                           }
                       });

               return view;
           }else {
             return  null;
           }


        }
    }
}