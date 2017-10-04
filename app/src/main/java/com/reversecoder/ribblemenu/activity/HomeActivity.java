package com.reversecoder.ribblemenu.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.reversecoder.ribblemenu.R;
import com.reversecoder.ribblemenu.fragment.HomeFragment;
import com.reversecoder.ribblemenu.fragment.UserLikeFragment;
import com.reversecoder.ribblemenu.util.AppUtil;
import com.reversecoder.ribblemenu.util.FragmentUtilsManager;

import io.armcha.ribble.presentation.navigation.NavigationState;
import io.armcha.ribble.presentation.utils.extensions.ViewExKt;
import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationDrawerView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationId;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItem;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItemSelectedListener;

public class HomeActivity extends AppCompatActivity {

    private String TRANSLATION_X_KEY = "TRANSLATION_X_KEY";
    private String CARD_ELEVATION_KEY = "CARD_ELEVATION_KEY";
    private String SCALE_KEY = "SCALE_KEY";

    Toolbar toolbar;
    NavigationDrawerView navView;
    DrawerLayout drawerLayout;
    CardView mainView;
    ArcView arcView;
    AnimatedImageView arcImage;
    AnimatedTextView toolbarTitle;
    ImageView userAvatar;

    private boolean isArcIcon = false;
    private boolean isDrawerOpened = false;
    private String activeTitle = "";
    private NavigationState state = null;
    private int currentNavigationSelectedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        if (isArcIcon || isDrawerOpened) {
            setArcArrowState();
        } else {
            setArcHamburgerIconState();
        }
        setToolBarTitle(activeTitle);
        updateUserInfo();

        if (savedInstanceState == null) {
            handleFragmentChanges(HomeActivity.this, NavigationId.SHOT.INSTANCE.getName(), new HomeFragment());
        }
    }

    private void initViews() {

        navView = (NavigationDrawerView) findViewById(R.id.navView);
        userAvatar = (ImageView) navView.getHeader().findViewById(R.id.userAvatar);
        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navView.setNavigationItemSelectListener(new NavigationItemSelectedListener() {
            @Override
            public void onNavigationItemSelected(NavigationItem item) {
                if (!getNavigatorState().getActiveTag().equalsIgnoreCase(item.getId().getName())) {
                    if (item.getId().getName().equalsIgnoreCase(NavigationId.SHOT.INSTANCE.getName())) {
                        handleFragmentChanges(HomeActivity.this, NavigationId.SHOT.INSTANCE.getName(), new HomeFragment());
                    } else if (item.getId().getName().equalsIgnoreCase(NavigationId.USER_LIKES.INSTANCE.getName())) {
                        handleFragmentChanges(HomeActivity.this, NavigationId.USER_LIKES.INSTANCE.getName(), new UserLikeFragment());
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mainView = (CardView) findViewById(R.id.mainView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerElevation(0f);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float moveFactor = navView.getWidth() * slideOffset;
                mainView.setTranslationX(moveFactor);
                ViewExKt.setScale(mainView, 1 - slideOffset / 4);
                mainView.setCardElevation(slideOffset * AppUtil.toPx(HomeActivity.this, 10));
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                handleDrawerOpen();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                handleDrawerClose();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });

        drawerLayout.setScrimColor(Color.TRANSPARENT);
    }

    public void handleFragmentChanges(AppCompatActivity activity, String currentTag, Fragment fragment) {
        saveNavigatorState(new NavigationState(currentTag, toolbarTitle.getText().toString(), false));

        setToolBarTitle(currentTag);
        activeTitle = currentTag;
        if (isArcIcon) {
            isArcIcon = false;
            setArcHamburgerIconState();
        }

        int checkPosition = -1;
        if (currentTag.equalsIgnoreCase(NavigationId.SHOT.INSTANCE.getName())) {
            checkPosition = 0;
        } else if (currentTag.equalsIgnoreCase(NavigationId.USER_LIKES.INSTANCE.getName())) {
            checkPosition = 1;
        } else if (currentTag.equalsIgnoreCase(NavigationId.FOLLOWING.INSTANCE.getName())) {
            checkPosition = 2;
        } else if (currentTag.equalsIgnoreCase(NavigationId.ABOUT.INSTANCE.getName())) {
            checkPosition = 3;
        } else if (currentTag.equalsIgnoreCase(NavigationId.LOG_OUT.INSTANCE.getName())) {
            checkPosition = 4;
        } else {
            checkPosition = currentNavigationSelectedItem;
        }

        if (currentNavigationSelectedItem != checkPosition) {
            currentNavigationSelectedItem = checkPosition;
            checkNavigationItem(currentNavigationSelectedItem);
        }

        FragmentUtilsManager.changeSupportFragment(HomeActivity.this, fragment, currentTag);
    }

    public void setArcArrowState() {
        arcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arcImage.setAnimatedImage(io.armcha.ribble.R.drawable.arrow_left, 0L);
    }

    public void setArcHamburgerIconState() {
        arcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        arcImage.setAnimatedImage(R.drawable.hamb, 0L);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void checkNavigationItem(int position) {
        navView.setChecked(position);
    }

    public void setToolBarTitle(String title) {
        toolbarTitle.setAnimatedText(title, 0L);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putFloat(TRANSLATION_X_KEY, mainView.getTranslationX());
            outState.putFloat(CARD_ELEVATION_KEY, ViewExKt.getScale(mainView));
            outState.putFloat(SCALE_KEY, mainView.getCardElevation());
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        if (savedState != null) {
            mainView.setTranslationX(savedState.getFloat(TRANSLATION_X_KEY));
            ViewExKt.setScale(mainView, savedState.getFloat(CARD_ELEVATION_KEY));
            mainView.setCardElevation(savedState.getFloat(SCALE_KEY));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void saveNavigatorState(NavigationState state) {
        this.state = state;
    }

    public NavigationState getNavigatorState() {
        return state;
    }

    public void handleDrawerOpen() {
        if (!isArcIcon)
            setArcArrowState();
        isDrawerOpened = true;
    }

    public void handleDrawerClose() {
        if (!isArcIcon && isDrawerOpened)
            setArcHamburgerIconState();
        isDrawerOpened = false;
    }

    public void updateUserInfo() {
        Glide
                .with(HomeActivity.this)
                .load(R.drawable.ic_rashed)
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .apply(new RequestOptions().circleCropTransform())
                .into(userAvatar);
    }
}
