package com.apps.albayt.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;


import com.apps.albayt.R;
import com.apps.albayt.databinding.SliderBinding;
import com.apps.albayt.model.SliderDataModel;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<SliderDataModel.SliderModel> list;
    private LayoutInflater inflater;
    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        SliderBinding rowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.slider, view, false);
        rowBinding.setPhoto(list.get(position).getImage());
        view.addView(rowBinding.getRoot());
        return rowBinding.getRoot();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void updateList(List<SliderDataModel.SliderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}