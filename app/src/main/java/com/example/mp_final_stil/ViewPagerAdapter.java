package com.example.mp_final_stil;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

class ViewpagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_FRAGS = 3;
    private ArrayList<Fragment> fragList = new ArrayList<>();
    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragList.add(new TabMy());
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
        return this.NUM_FRAGS;
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