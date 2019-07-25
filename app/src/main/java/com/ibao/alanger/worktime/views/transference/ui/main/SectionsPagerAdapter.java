package com.ibao.alanger.worktime.views.transference.ui.main;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ibao.alanger.worktime.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final SparseArray<WeakReference<Fragment>> instantiatedFragments = new SparseArray<>();


    private List<String> TAB_TITLES;



    public SectionsPagerAdapter(FragmentManager fm, ArrayList<String> tabHeader) {
        super(fm);
        this.TAB_TITLES = tabHeader;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        Fragment fragment= null;

        if(position==0){
           fragment= AddPersonalFragment.newInstance("","");
        }else {
            fragment= ListPersonalAddedFragment.newInstance("","");
        }

        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }



    @Nullable
    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> wr = instantiatedFragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }



}