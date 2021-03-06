package com.kiscode.wanandroid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kiscode.wanandroid.ui.dashboard.DashboardFragment;
import com.kiscode.wanandroid.ui.home.HomeFragment;
import com.kiscode.wanandroid.ui.me.MeFragment;
import com.kiscode.wanandroid.ui.wechat.WeChatFragment;
import com.kiscode.wanandroid.widget.FixFragmentNavigator;

public class HomeNavGraphActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navgation_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

     /*   appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_wechat, R.id.navigation_me)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/

        initFixNavController(navView);

    }

    private void initFixNavController(BottomNavigationView navView) {
        //https://github.com/holidayei/JetpackStudy/blob/master/app/src/main/java/com/holiday/jetpackstudy/navigation/FixFragmentNavigator.java
        //??????????????????NavHostFragment
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //?????????????????????
        NavController navController = NavHostFragment.findNavController(fragment);
        //??????????????????Fragment?????????
        FixFragmentNavigator fragmentNavigator =
                new FixFragmentNavigator(this, fragment.getChildFragmentManager(), fragment.getId());
        //????????????????????????
        NavigatorProvider provider = navController.getNavigatorProvider();
        //???????????????Fragment?????????????????????
        provider.addNavigator(fragmentNavigator);
        //?????????????????????
        NavGraph navGraph = initNavGraph(provider, fragmentNavigator);
        //???????????????
        navController.setGraph(navGraph);
        //??????????????????????????????
        navView.setOnNavigationItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });
    }

/*
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
*/

    //???????????????????????????3????????????????????????
    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //??????????????????????????????????????????
        FragmentNavigator.Destination destination1 = fragmentNavigator.createDestination();
        destination1.setId(R.id.navigation_home);
        destination1.setClassName(HomeFragment.class.getCanonicalName());
        destination1.setLabel(getResources().getString(R.string.title_home));
        navGraph.addDestination(destination1);

        FragmentNavigator.Destination destinationDashboard = fragmentNavigator.createDestination();
        destinationDashboard.setId(R.id.navigation_dashboard);
        destinationDashboard.setClassName(DashboardFragment.class.getCanonicalName());
        destinationDashboard.setLabel(getResources().getString(R.string.title_dashboard));
        navGraph.addDestination(destinationDashboard);

        FragmentNavigator.Destination destinationWeChat = fragmentNavigator.createDestination();
        destinationWeChat.setId(R.id.navigation_wechat);
        destinationWeChat.setClassName(WeChatFragment.class.getCanonicalName());
        destinationWeChat.setLabel(getResources().getString(R.string.title_wechat));
        navGraph.addDestination(destinationWeChat);

        FragmentNavigator.Destination destinationMe = fragmentNavigator.createDestination();
        destinationMe.setId(R.id.navigation_me);
        destinationMe.setClassName(MeFragment.class.getCanonicalName());
        destinationMe.setLabel(getResources().getString(R.string.title_me));
        navGraph.addDestination(destinationMe);

        navGraph.setStartDestination(R.id.navigation_home);

        return navGraph;
    }

}