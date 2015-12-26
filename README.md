![Screenshot](https://github.com/ngocchung/CheeseSquare/blob/master/screenshot.gif)

Cheesesquare Sample - Customized Version
===================================

Demos the new Android Design library. This is not an exhaustive sample, but shows
some of the important features in the Design library:

- Collapsing Toolbar
- FloatingActionButton
- View anchoring
- NavigationView
- Snackbar

Pre-requisites
--------------

- Android SDK v22
- Android Build Tools v22.0.1
- Android Support Repository r16 (for v22.2.1)

Customized Contents
--------------

```
		final ViewGroup.LayoutParams toolbarLayoutParams = toolbar.getLayoutParams();
        if (toolbarLayoutParams != null) {
            toolbarHeight = toolbarLayoutParams.height;
        }

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        screenHeight = getScreenHeight(this);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayoutHeight = linearLayout.getHeight();
                if (linearLayoutHeight + toolbarHeight < screenHeight) {
                    if (toolbarLayoutParams != null) {
                        toolbarLayoutParams.height = screenHeight - linearLayoutHeight - 20;                        
                        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(mContext, android.R.color.transparent));
                    }
                }
                // Removes the listener if possible
                ViewTreeObserver viewTreeObserver = linearLayout.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
```

License
-------

Copyright 2014 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
