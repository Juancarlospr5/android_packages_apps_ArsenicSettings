/*
 * Copyright (C) 2018 Havoc-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arsenic.settings.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settings.display.FontPickerPreferenceController;
import com.arsenic.settings.display.AccentPickerPreferenceController;
import com.arsenic.settings.display.AutoDarkUIPreferenceController;
import com.arsenic.settings.display.DarkUIPreferenceController;
import com.arsenic.settings.display.QsAlphaPreferenceController;
import com.arsenic.settings.display.QsColorPreferenceController;
import com.arsenic.settings.display.QsHeaderStylePreferenceController;
import com.arsenic.settings.display.QsTileStylePreferenceController;
import com.arsenic.settings.display.SwitchStylePreferenceController;
import com.arsenic.settings.display.ThemePreferenceController;
import com.arsenic.settings.display.UiStylePreferenceController;

import com.arsenic.settings.R;

import java.util.ArrayList;
import java.util.List;

public class Interface extends DashboardFragment {
    private static final String TAG = "Interface";

 	private IntentFilter mIntentFilter;
    private static FontPickerPreferenceController mFontPickerPreference;
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.android.server.ACTION_FONT_CHANGED")) {
                mFontPickerPreference.stopProgress();
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("com.android.server.ACTION_FONT_CHANGED");
    }
    @Override
    public void onResume() {
        super.onResume();
        final Context context = getActivity();
        context.registerReceiver(mIntentReceiver, mIntentFilter);
    }
    @Override
    public void onPause() {
        super.onPause();
        final Context context = getActivity();
        context.unregisterReceiver(mIntentReceiver);
        mFontPickerPreference.stopProgress();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.ARSENIC_SETTINGS;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.arsenic_settings_interface;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getLifecycle(), this);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(
            Context context, Lifecycle lifecycle, Fragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new AccentPickerPreferenceController(context, lifecycle, fragment));
	    controllers.add(new AutoDarkUIPreferenceController(context));
        controllers.add(new DarkUIPreferenceController(context));
		controllers.add(mFontPickerPreference = new FontPickerPreferenceController(context, lifecycle, fragment));
        controllers.add(new QsAlphaPreferenceController(context));
        controllers.add(new QsColorPreferenceController(context));
        controllers.add(new QsHeaderStylePreferenceController(context));
        controllers.add(new QsTileStylePreferenceController(context));
        controllers.add(new SwitchStylePreferenceController(context));
        controllers.add(new ThemePreferenceController(context, lifecycle, fragment));
        controllers.add(new UiStylePreferenceController(context));
        return controllers;
    }
}
