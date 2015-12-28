/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    private final Context mContext = this;
    private int screenHeight;
    private int linearLayoutHeight;
    private int toolbarHeight_org;
    private int toolbarHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        screenHeight = getScreenHeight(this);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        final int colorPrimary = typedValue.data;

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
        final CoordinatorLayout.LayoutParams appbarLayoutParams = (CoordinatorLayout.LayoutParams)appbar.getLayoutParams();

        final ViewGroup.LayoutParams toolbarLayoutParams = toolbar.getLayoutParams();
        if (toolbarLayoutParams != null) {
            toolbarHeight_org = toolbarLayoutParams.height;
            toolbarHeight = toolbarLayoutParams.height;
        }

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        collapsingToolbar.setContentScrimColor(colorPrimary);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedTitleTextAppearance);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedTitleTextAppearance);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        ViewTreeObserver observer = linearLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                linearLayoutHeight = linearLayout.getHeight();
                if (linearLayoutHeight + toolbarHeight < screenHeight) {
                    if (toolbarLayoutParams != null) {
                        toolbarLayoutParams.height = screenHeight - linearLayoutHeight - 20;
                        if (toolbarLayoutParams.height < toolbarHeight_org) {
                            toolbarLayoutParams.height = toolbarHeight_org;
                        }

                        int extended_text_size = (int) getResources().getDimension(R.dimen.expanded_text_size);

                        if (appbarLayoutParams.height - toolbarLayoutParams.height <= extended_text_size) {
                            int value = appbarLayoutParams.height - toolbarLayoutParams.height;
                            if (value < 0) {
                                appbarLayoutParams.height = toolbarLayoutParams.height - value + extended_text_size;
                            } else {
                                appbarLayoutParams.height = toolbarLayoutParams.height + extended_text_size;
                            }
                        }

                        // collapsingToolbar.setContentScrimColor(getResources().getColor(android.R.color.transparent));
                        if (toolbarLayoutParams.height > toolbarHeight_org) {
                            collapsingToolbar.setContentScrimColor(ContextCompat.getColor(mContext, android.R.color.transparent));
                        }
                    }
                }
                // Removes the listener if possible
                ViewTreeObserver viewTreeObserver = linearLayout.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });

        loadBackdrop();
        appbar.setExpanded(true);
    }

    private int getScreenHeight(Context context) {
        int measuredHeight;
        Point size = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            wm.getDefaultDisplay().getSize(size);
            measuredHeight = size.y;
        } else {
            Display d = wm.getDefaultDisplay();
            measuredHeight = d.getHeight();
        }

        return measuredHeight;
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
