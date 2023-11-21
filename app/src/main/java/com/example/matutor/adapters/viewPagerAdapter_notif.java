package com.example.matutor.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.matutor.databinding.ActivityNotificationBinding;
import com.example.matutor.fragments.MessagesFragment;
import com.example.matutor.fragments.NotificationFragment;

public class viewPagerAdapter_notif extends FragmentStateAdapter {

    ActivityNotificationBinding binding;
    public viewPagerAdapter_notif(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0:
                return new NotificationFragment();
            case 1:
                return new MessagesFragment();
            default:
                return new NotificationFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
