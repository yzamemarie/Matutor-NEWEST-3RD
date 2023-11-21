package com.example.matutor.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.matutor.Posting;
import com.example.matutor.fragments.AllPostingFragment;
import com.example.matutor.fragments.AllTutoringCenterFragment;
import com.example.matutor.fragments.AllUserFragment;

public class viewPagerAdapter_posting extends FragmentStateAdapter {
    public viewPagerAdapter_posting(@NonNull Posting fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new AllPostingFragment();
            case 1:
                return new AllUserFragment();
            case 2:
                return new AllTutoringCenterFragment();
            default:
                return new AllPostingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
