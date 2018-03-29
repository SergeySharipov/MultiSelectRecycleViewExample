package ca.sharipov.sergey.multiselectrecycleviewexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ItemsFragment mItemsFragment;
    private FragmentManager mFragmentManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null)
            mItemsFragment = (ItemsFragment) mFragmentManager.getFragment(savedInstanceState, ItemsFragment.class.getName());

        if (mItemsFragment == null)
            mItemsFragment = new ItemsFragment();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.fragment_container, mItemsFragment, ItemsFragment.class.getName());
        } else {
            fragmentTransaction.replace(R.id.fragment_container, mItemsFragment, ItemsFragment.class.getName());
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mFragmentManager.putFragment(outState, ItemsFragment.class.getName(), mItemsFragment);
    }
}