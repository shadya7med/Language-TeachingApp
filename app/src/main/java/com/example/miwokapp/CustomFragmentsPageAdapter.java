package com.example.miwokapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomFragmentsPageAdapter extends FragmentPagerAdapter {
    Context mContext ;

    public CustomFragmentsPageAdapter(Context mContext,FragmentManager fm)
    {
        super(fm);
        this.mContext = mContext ;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int i) {

        switch(i)
        {
            case 0:return new NumbersFragment();
            case 1:return  new ColorsFragment();
            case 2:return new FamilyMembersFragment();
            case 3:return new PhrasesFragment();
        }


        return null;


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       switch (position)
       {
           case 0:return mContext.getString(R.string.numbers_button);
           case 1:return mContext.getString(R.string.colors_button);
           case 2:return mContext.getString(R.string.family_members_button);
           case 3:return mContext.getString(R.string.phrases_button);
           default:return null ;
       }
    }
}
