/*
 *   Copyright (C) 2017 Teclib. All rights reserved.
 *
 * This file is part of flyve-mdm-android-agent
 *
 * flyve-mdm-android-agent is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    Rafael Hernandez
 * @date      02/06/2017
 * @copyright Copyright (C) 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android-agent
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

package org.flyve.mdm.agent.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;

import org.flyve.mdm.agent.R;
import org.flyve.mdm.agent.core.walkthrough.WalkthroughModel;
import org.flyve.mdm.agent.data.DataStorage;
import org.flyve.mdm.agent.utils.FlyveLog;

import java.util.ArrayList;

/**
 * This is the first screen of the app here you can get information about flyve-mdm-agent
 * if you are not register
 */
public class SplashActivity extends FragmentActivity {

    private static final int TIME = 3000;
    private IntentFilter mIntent;
    private ArrayList<WalkthroughModel> walkthrough;


    @Override
    public void onPause() {
        // unregister the broadcast
        if(mIntent != null) {
            unregisterReceiver(broadcastMessage);
            mIntent = null;
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        // register the broadcast
        super.onResume();
        LocalBroadcastManager.getInstance(SplashActivity.this).registerReceiver(broadcastMessage, new IntentFilter("flyve.ACTION_CLOSE"));
    }

    /**
     * Called when the activity is starting
     * It shows the UI with the Splash screen
     * @param savedInstanceState if the activity is being re-initialized, it contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataStorage cache = new DataStorage( SplashActivity.this );

        // if broker is on cache open the main activity
        String broker = cache.getBroker();
        //if(broker != null) {
            // if user is enrolled show landing screen
            FlyveLog.d(cache.getSessionToken());

            setContentView(R.layout.activity_splash_enrolled);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openMain();
                }
            }, TIME);

            return;
       //}

//        // if user is not enrolled show help
//        setContentView(R.layout.activity_splash);
//
//        EnvInfoSetup setup = new EnvInfoSetup(SplashActivity.this);
//
//        walkthrough = new ArrayList<>();
//        walkthrough.add(new WalkthroughModel(R.drawable.wt_text_1, getResources().getString(R.string.walkthrough_step_link_1), R.drawable.ic_walkthroug_1));
//        walkthrough.add(new WalkthroughModel(R.drawable.wt_text_2, getResources().getString(R.string.walkthrough_step_link_1), R.drawable.ic_walkthroug_2));
//        walkthrough.add(new WalkthroughModel(R.drawable.wt_text_3, getResources().getString(R.string.walkthrough_step_link_1), R.drawable.ic_walkthroug_3));
//
//        // Instantiate a ViewPager and a PagerAdapter.
//        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
//        PagerAdapter mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
//        mPager.setAdapter(mPagerAdapter);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FragmentSlideWalkthrough f = new FragmentSlideWalkthrough();
            f.config(walkthrough.get(position), walkthrough.size(), position);
            return f;
        }

        @Override
        public int getCount() {
            return walkthrough.size();
        }
    }

    /**
     * Open the main activity
     */
    private void openMain() {
        Intent miIntent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(miIntent);
        SplashActivity.this.finish();
    }

    private BroadcastReceiver broadcastMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // Close this activity
            if("flyve.ACTION_CLOSE".equalsIgnoreCase(action)) {
                SplashActivity.this.finish();
            }
        }
    };


}
