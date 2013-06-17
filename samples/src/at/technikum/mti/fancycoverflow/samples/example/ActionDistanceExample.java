/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package at.technikum.mti.fancycoverflow.samples.example;

import android.util.DisplayMetrics;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.samples.shared.BaseExampleActivity;

public class ActionDistanceExample extends BaseExampleActivity {

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected void setupFancyCoverFlow(FancyCoverFlow fancyCoverFlow) {
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        fancyCoverFlow.setActionDistance((int) (48 * metrics.xdpi / 160.0f));
        fancyCoverFlow.setMaxRotation(60);
        fancyCoverFlow.setSpacing(-100);
        fancyCoverFlow.setUnselectedScale(0.75f);
    }
}
