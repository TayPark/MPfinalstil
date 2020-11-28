package com.example.mp_final_stil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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


}