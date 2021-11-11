package com.innocent.growingdeveloperclickergame.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.innocent.growingdeveloperclickergame.common.ToastController

enum class AdServiceStatus {
    IDLE, READY, RUN
}

object AdService {

    private var status: AdServiceStatus = AdServiceStatus.IDLE
    private var mRewardedAd: RewardedAd? = null

    private var rewardIdx: Int = 0
    private val rewardMap: MutableMap<Int, String> = HashMap()

    fun fetch(activity: Activity, listener: OnFetchAdListener) {
        RewardedAd.load(activity,"ca-app-pub-3940256099942544/5224354917", AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdService", adError.message)
                status = AdServiceStatus.IDLE
                mRewardedAd = null
                listener.onFetch(false)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d("AdService", "onAdLoaded")
                status = AdServiceStatus.READY
                mRewardedAd = rewardedAd
                listener.onFetch(true)
            }
        })
    }

    fun show(activity: Activity, listener: OnRewardedAdListener) {
        if (status != AdServiceStatus.READY || mRewardedAd == null) {
            Log.d("AdService", "not ready. status: $status")
            return
        }

        status = AdServiceStatus.RUN

        var rewarded = false
        mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                Log.d("AdService", "onAdShowedFullScreenContent")
            }
            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("AdService", "onAdFailedToShowFullScreenContent")
            }
            override fun onAdDismissedFullScreenContent() {
                Log.d("AdService", "onAdDismissedFullScreenContent")
                status = AdServiceStatus.IDLE
                mRewardedAd = null
                listener.onRewarded(rewarded)
            }
        }

        mRewardedAd?.show(activity) {
            rewarded = true

            val rewardAmount = it.amount
            val rewardType = it.type
            Log.d("AdService", "rewardAmount : $rewardAmount, rewardType : $rewardType")
        }
    }

    fun clear() {
        mRewardedAd = null
    }
}

interface OnFetchAdListener {
    fun onFetch(success: Boolean)
}

interface OnRewardedAdListener {
    fun onRewarded(rewarded: Boolean)
}