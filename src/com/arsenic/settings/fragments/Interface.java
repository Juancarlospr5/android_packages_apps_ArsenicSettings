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
import android.content.Context;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.arsenic.settings.display.AccentPickerPreferenceController;
import com.arsenic.settings.display.AutoDarkUIPreferenceController;
import com.arsenic.settings.display.ContentPaddingPreferenceController;
import com.arsenic.settings.display.DarkUIPreferenceController;
import com.arsenic.settings.display.QsTileStylePreferenceController;
import com.arsenic.settings.display.RoundedCornersPreferenceController;

import com.arsenic.settings.R;

import java.util.ArrayList;
import java.util.List;

public class Interface extends DashboardFragment {
    private static final String TAG = "Interface";

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
        controllers.add(new ContentPaddingPreferenceController(context));
        controllers.add(new DarkUIPreferenceController(context));
        controllers.add(new QsTileStylePreferenceController(context));
        controllers.add(new RoundedCornersPreferenceController(context));
        return controllers;
    }
}
