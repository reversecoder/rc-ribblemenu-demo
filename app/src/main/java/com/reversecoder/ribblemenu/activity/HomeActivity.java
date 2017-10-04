package com.reversecoder.ribblemenu.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.reversecoder.ribblemenu.R;
import com.reversecoder.ribblemenu.util.AppUtil;

import org.jetbrains.annotations.NotNull;

import io.armcha.ribble.presentation.navigation.NavigationState;
import io.armcha.ribble.presentation.navigation.Navigator;
import io.armcha.ribble.presentation.widget.AnimatedImageView;
import io.armcha.ribble.presentation.widget.AnimatedTextView;
import io.armcha.ribble.presentation.widget.ArcView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationDrawerView;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationId;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItem;
import io.armcha.ribble.presentation.widget.navigation_view.NavigationItemSelectedListener;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    NavigationDrawerView navView;
    DrawerLayout drawerLayout;
    CardView mainView;
    ArcView arcView;
    AnimatedImageView arcImage;
    AnimatedTextView toolbarTitle;
//    Navigator navigator;

    //HomePresenter
    private boolean isArcIcon = false;
    //    private User user = null;
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
//        user?.let {
//            view?.updateDrawerInfo(it)
//        }

    }

    private void initViews() {

        toolbarTitle = (AnimatedTextView) findViewById(R.id.toolbarTitle);
        arcImage = (AnimatedImageView) findViewById(R.id.arcImage);
        arcView = (ArcView) findViewById(R.id.arcView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navView = (NavigationDrawerView) findViewById(R.id.navView);
        navView.setNavigationItemSelectListener(new NavigationItemSelectedListener() {
            @Override
            public void onNavigationItemSelected(@NotNull NavigationItem item) {
//                when (item.id) {
//                    Id.SHOT -> {
//                        goTo<ShotRootFragment>()
//                    }
//                    Id.USER_LIKES -> {
//                        goTo<UserLikesFragment>()
//                    }
//                    Id.FOLLOWING -> {
//                        goTo<UserFollowingFragment>()
//                    }
//                    Id.ABOUT -> {
//                        goTo<AboutFragment>()
//                    }
//                    Id.LOG_OUT -> {
//                        presenter.logOut()
//                    }
//                }
//                drawerLayout.closeDrawer(GravityCompat.START)
            }
        });
//        navView.header.userName

        mainView = (CardView) findViewById(R.id.mainView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerElevation(0f);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                float moveFactor = navView.getWidth() * slideOffset;
                mainView.setTranslationX(moveFactor);
                mainView.setScaleX(1 - slideOffset / 4);
                mainView.setScaleY(1 - slideOffset / 4);
                mainView.setCardElevation(slideOffset * AppUtil.toPx(HomeActivity.this, 10));
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
//        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
//            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
//                super.onDrawerSlide(drawerView, slideOffset)
//                val moveFactor = navView.width * slideOffset
//                mainView.translationX = moveFactor
//                mainView.scale = 1 - slideOffset / 4
//                mainView.cardElevation = slideOffset * 10.toPx(this@HomeActivity)
//            }
//
//            override fun onDrawerOpened(drawerView: View?) {
//                super.onDrawerOpened(drawerView)
//                presenter.handleDrawerOpen()
//            }
//
//            override fun onDrawerClosed(drawerView: View?) {
//                super.onDrawerClosed(drawerView)
//                presenter.handleDrawerClose()
//            }
//        })
//        drawerLayout.setScrimColor(Color.TRANSPARENT)
//
////        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
////            v.addTopMargin(insets.systemWindowInsetTop)
////            navView.addBottomMargin(insets.systemWindowInsetBottom)
////            insets
////        }
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

//    public void updateDrawerInfo(user: User) {
//        val header = navView.header
//        with(header) {
//            userName.text = user.name
//            userInfo.text = user.location
//            userAvatar.load(user.avatarUrl, TransformationType.CIRCLE)
//        }
//    }

//    override fun onFragmentChanged(currentTag: String, currentFragment: Fragment) {
//        presenter.handleFragmentChanges(currentTag, currentFragment)
//    }

    public void setToolBarTitle(String title) {
        toolbarTitle.setAnimatedText(title, 0L);
    }

//    override fun openShotFragment() {
//        goTo<ShotRootFragment>()
//    }

//    override fun openLoginActivity() {
//        start<AuthActivity>()
//        showToast("Logged out")
//        finish()
//    }

//    @Override
//    public void onDestroy() {
//        saveNavigatorState(navigator.getState());
//        super.onDestroy();
//    }

//    override fun injectDependencies() {
//        activityComponent.inject(this)
//    }

    public void saveNavigatorState(NavigationState state) {
        this.state = state;
    }

    public NavigationState getNavigatorState(){
        return state;
    }
}
