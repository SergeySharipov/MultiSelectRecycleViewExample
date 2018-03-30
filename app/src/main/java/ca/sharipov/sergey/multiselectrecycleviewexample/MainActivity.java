package ca.sharipov.sergey.multiselectrecycleviewexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ItemsFragment itemsFragment = (ItemsFragment) fragmentManager.findFragmentById(R.id.fragment_container);

        if (itemsFragment == null) {
            itemsFragment = new ItemsFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, itemsFragment, ItemsFragment.class.getName());
            fragmentTransaction.commit();
        }
    }
}