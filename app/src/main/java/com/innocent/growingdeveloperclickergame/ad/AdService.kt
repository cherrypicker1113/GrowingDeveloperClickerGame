package com.innocent.growingdeveloperclickergame.ad

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

enum class AdServiceStatus {
    IDLE, FETCHED, RUN
}

object AdService {

    private var status: AdServiceStatus = AdServiceStatus.IDLE
    private val adList: MutableList<RewardedAd> = mutableListOf()
    private var mRewardedAd: RewardedAd? = null

    fun ready(activity: Activity, listener: OnReadyAdListener?) {
        if (adList.size > 1) {
            listener?.onReady(true)
            return
        }
        RewardedAd.load(activity,"ca-app-pub-3940256099942544/5224354917", AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdService", adError.message)
                listener?.onReady(false)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d("AdService", "onAdLoaded")
                adList.add(rewardedAd)
                listener?.onReady(true)
                ready(activity, null)
            }
        })
    }

    fun fetch(activity: Activity, listener: OnFetchAdListener) {
        val readyAd = shiftReadyAd()

        if (readyAd != null) {
            status = AdServiceStatus.FETCHED
            mRewardedAd = readyAd
            listener.onFetch(true)
            return
        }

        ready(activity, object: OnReadyAdListener {
            override fun onReady(success: Boolean) {
                if (success) {
                    fetch(activity, listener)
                } else {
                    status = AdServiceStatus.IDLE
                    mRewardedAd = null
                    listener.onFetch(false)
                }
            }
        })
    }

    private fun shiftReadyAd(): RewardedAd? {
        return if(adList.isNotEmpty()) adList.removeFirst() else null
    }

    fun show(activity: Activity, listener: OnRewardedAdListener) {
        if (status != AdServiceStatus.FETCHED || mRewardedAd == null) {
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
        adList.clear()
        mRewardedAd = null
    }
}

interface OnReadyAdListener {
    fun onReady(success: Boolean)
}

interface OnFetchAdListener {
    fun onFetch(success: Boolean)
}

interface OnRewardedAdListener {
    fun onRewarded(rewarded: Boolean)
}