package com.kiscode.wanandroid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiscode.wanandroid.ui.dashboard.DashboardFragment;
import com.kiscode.wanandroid.ui.home.HomeFragment;
import com.kiscode.wanandroid.ui.me.MeFragment;
import com.kiscode.wanandroid.ui.wechat.WeChatFragment;

public class HomeActivity extends AppCompatActivity {

    private static final String F_TAG_SUFIX = "home_";
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initBottomNavigation();

    }

    private void initBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(item -> {
            changeToMenu(item.getItemId());
            return true;
        });

        changeToMenu(R.id.navigation_home);
    }


    private void changeToMenu(@IdRes int menuId) {
        Fragment targetFragmnet = getFragmentByMenu(menuId);
        assert targetFragmnet != null;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        String fragmentTag = F_TAG_SUFIX + menuId;

        if (currentFragment == null) {
            if (!targetFragmnet.isAdded()) {
                transaction.add(R.id.container_home, targetFragmnet, fragmentTag).commit();
            } else {
                transaction.replace(R.id.container_home, targetFragmnet, fragmentTag).commit();
            }
            currentFragment = targetFragmnet;
            return;
        }
        if (currentFragment == targetFragmnet) {
            return;
        }

        if (!targetFragmnet.isAdded()) {
            transaction.hide(currentFragment).add(R.id.container_home, targetFragmnet, fragmentTag).commit();
        } else {
            transaction.hide(currentFragment).show(targetFragmnet).commit();
        }
        currentFragment = targetFragmnet;
    }

    @SuppressLint("NonConstantResourceId")
    private Fragment getFragmentByMenu(@IdRes int menuId) {
        String fragmentTag = F_TAG_SUFIX + menuId;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null) {
            return fragment;
        }

        switch (menuId) {
            case R.id.navigation_home:
                return new HomeFragment();
            case R.id.navigation_dashboard:
                return new DashboardFragment();
            case R.id.navigation_wechat:
                return new WeChatFragment();
            case R.id.navigation_me:
                return new MeFragment();
        }
        return null;
    }

}