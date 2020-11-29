package com.example.mp_final_stil;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragList = new ArrayList<>();

    public ViewpagerAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
        fragList.add(new TabMy());
        fragList.add(new TabShare());
        fragList.add(new TabBookmark());
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return fragList.get(position);
    }

    @Override
    public int getCount()
    {
        return fragList.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    public void replaceFragment(int position, Fragment frag) {
        fragList.remove(position);
        fragList.add(position, frag);
    }
}