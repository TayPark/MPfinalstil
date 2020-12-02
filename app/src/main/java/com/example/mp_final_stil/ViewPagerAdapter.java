package com.example.mp_final_stil;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

class ViewpagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragList = new ArrayList<>();

    /* To init fragments */
    public ViewpagerAdapter(FragmentManager fm, JSONArray data) {
        super(fm);
        fragList.add(new TabMy(data));
        fragList.add(new TabShare());
        fragList.add(new TabBookmark());
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return fragList.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount()
    {
        return fragList.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public void updateItem(int position, Fragment frag) {
        fragList.set(position, frag);
        this.notifyDataSetChanged();
    }
}