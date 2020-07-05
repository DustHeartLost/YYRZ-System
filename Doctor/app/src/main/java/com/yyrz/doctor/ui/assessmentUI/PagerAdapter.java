package com.yyrz.doctor.ui.assessmentUI;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.yyrz.doctor.Util.viewmodel.CommonViewModel;

class PagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] fragments;
    PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments=new Fragment[]{new AlternateLineTest(),new VisualSpaceSkill(),new Named(),new MemoryAssessment(),new Attention(),new FluentLanguge(),new Abstraction(),new MemoryDelayAssessment(),new Directional()};
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        CommonViewModel.getInstance().setFragment((Fragment)object);
        int i=position+1;
        CommonViewModel.getInstance().getToolbar().setTitleMarginEnd(0);
        CommonViewModel.getInstance().getToolbar().setTitle(i+"."+ShowViewPager.assessment_labels[position]);
    }
}