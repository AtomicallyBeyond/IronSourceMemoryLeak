package com.ironsource.ironsourcesdkdemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;


public class BannerFragment extends Fragment {

    FrameLayout bannerContainer;
    private IronSourceBannerLayout mIronSourceBannerLayout;

    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance() {
        BannerFragment fragment = new BannerFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyAndDetachBanner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_banner, container, false);
         bannerContainer = view.findViewById(R.id.banner_container);
        createAndloadBanner();
         return view;
    }

    private void destroyAndDetachBanner() {
        IronSource.destroyBanner(mIronSourceBannerLayout);
        if (bannerContainer != null) {
            bannerContainer.removeView(mIronSourceBannerLayout);
        }
    }

    private void createAndloadBanner() {
        // choose banner size
        ISBannerSize size = ISBannerSize.BANNER;

        // instantiate IronSourceBanner object, using the IronSource.createBanner API
        mIronSourceBannerLayout = IronSource.createBanner(getActivity(), size);

        // add IronSourceBanner to your container
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        bannerContainer.addView(mIronSourceBannerLayout, 0, layoutParams);

        if (mIronSourceBannerLayout != null) {
            // set the banner listener
            mIronSourceBannerLayout.setBannerListener(new BannerListener() {
                @Override
                public void onBannerAdLoaded() {
                    // since banner container was "gone" by default, we need to make it visible as soon as the banner is ready
                    bannerContainer.setVisibility(View.VISIBLE);
                }

                @Override
                public void onBannerAdLoadFailed(IronSourceError error) {
                }

                @Override
                public void onBannerAdClicked() {
                }

                @Override
                public void onBannerAdScreenPresented() {
                }

                @Override
                public void onBannerAdScreenDismissed() {
                }

                @Override
                public void onBannerAdLeftApplication() {
                }
            });

            // load ad into the created banner
            IronSource.loadBanner(mIronSourceBannerLayout);
        } else {
            Toast.makeText(getActivity(), "IronSource.createBanner returned null", Toast.LENGTH_LONG).show();
        }
    }
}