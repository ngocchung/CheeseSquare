![Screenshot](https://github.com/ngocchung/CheeseSquare/blob/master/screenshot.gif)

Cheesesquare Sample - Customized Version
===================================

[Original Version](https://github.com/chrisbanes/cheesesquare)

Demos the new Android Design library. This is not an exhaustive sample, but shows
some of the important features in the Design library:

- Collapsing Toolbar
- FloatingActionButton
- View anchoring
- NavigationView
- Snackbar

Customized Contents
--------------

```
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
                                appbarLayoutParams.height = toolbarLayoutParams.height - value + extended_text_size * 3;
                            } else {
                                appbarLayoutParams.height = toolbarLayoutParams.height + extended_text_size * 3;
                            }
                            if (appbarLayoutParams.height >= screenHeight) {
                                appbarLayoutParams.height = screenHeight;
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
```
