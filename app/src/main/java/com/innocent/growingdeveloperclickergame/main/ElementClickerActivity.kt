package com.innocent.growingdeveloperclickergame.main

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.innocent.growingdeveloperclickergame.R
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding
import com.innocent.growingdeveloperclickergame.equip.*
import com.innocent.growingdeveloperclickergame.project.Project
import com.innocent.growingdeveloperclickergame.project.ProjectDC
import com.innocent.growingdeveloperclickergame.project.ProjectDCListener
import com.innocent.growingdeveloperclickergame.project.ProjectListPopup


class ElementClickerActivity : AppCompatActivity(), CodingPowerListener, MoneyListener, EquipListener, CounterDCListener, ProjectDCListener {
    init {
        // CodingPerformanceDC에 리스너 등록
        CodingPowerDC.addListener(this)
        // MoneyDC 리스너 등록
        MoneyDC.addListener(this)
        // EquipDC 리스너 등록
        EquipDC.addListener(this)
        // CounterDC 리스너 등록
        CounterDC.addListener(this)
        // ProjectDC 리스너 등록
        ProjectDC.setListener(this)
    }

    private lateinit var binding: ActivityElementClickerBinding
    private val helloWorld: String = "Hello, World!"

    private var mRewardedAd: RewardedAd? = null

    private var rewardIdx: Int = 0
    private val rewardMap: MutableMap<Int, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        MainDC.fetchData(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnTouchListener { v, event ->
            val action = event.action
            if (action === MotionEvent.ACTION_DOWN
                || (action and MotionEvent.ACTION_POINTER_DOWN) === MotionEvent.ACTION_POINTER_DOWN) CounterDC.click()
            MainDC.saveData(this)
            return@setOnTouchListener true
        }
        binding.btnProjectMenu.setOnClickListener { ProjectListPopup(this).show() }
        binding.btnEquipMenu.setOnClickListener { EquipListPopup(this).show() }
        // 임시 광고 테스트
        binding.btnFitEnd.setOnClickListener { showAd() }

        if (ProjectDC.hasProjectInProgress()) {
            binding.tvExp.visibility = View.VISIBLE
        }
        changeMenuFromState()
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedAd = null;
    }

    private fun showAd() {
        if (mRewardedAd != null) {
            val rewardIdx = ++rewardIdx
            mRewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    Log.d("ElementClickerActivity", "onAdShowedFullScreenContent")
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    Log.d("ElementClickerActivity", "onAdFailedToShowFullScreenContent")
                }
                override fun onAdDismissedFullScreenContent() {
                    Log.d("ElementClickerActivity", "onAdDismissedFullScreenContent")
                    mRewardedAd = null
                    rewardMap[rewardIdx]?.let { ToastController.showToast(applicationContext, it) }
                }
            }
            mRewardedAd?.show(this) {
                val rewardAmount = it.amount
                val rewardType = it.type
                rewardMap[rewardIdx] = "rewardAmount : $rewardAmount, rewardType : $rewardType"
            }
        } else {
            loadAd()
        }
    }

    private fun loadAd() {
        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", AdRequest.Builder().build(), object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("ElementClickerActivity", adError.message)
                mRewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d("ElementClickerActivity", "onAdLoaded")
                mRewardedAd = rewardedAd
                showAd()
            }
        })
    }

    //뭔가 정리가 좀 필요
    private fun changeMenuFromState() {
        val equipMenuVisible: Boolean = EquipDC.hasAnyEquip() || MoneyDC.getMoney() >= EquipDC.getCheapestEquipPrice()
        val equipMenuNewIconVisible: Boolean = !EquipDC.hasAnyEquip() && MoneyDC.getMoney() >= EquipDC.getCheapestEquipPrice()
        binding.ivEnabledEquipMenu.visibility = if (equipMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.textEquipMenu.visibility = if (equipMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivDisabledEquipMenu.visibility = if (!equipMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivEquipMenuLock.visibility = if (!equipMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivEquipMenuNew.visibility = if (equipMenuNewIconVisible) View.VISIBLE else View.INVISIBLE

        val projectMenuVisible: Boolean = ProjectDC.hasProjectInProgress() || CodingPowerDC.getCodingPower() >= ProjectDC.getLowestLimitCodingPower()
        val projectMenuNewIconVisible: Boolean = !ProjectDC.hasProjectInProgress() && CodingPowerDC.getCodingPower() >= ProjectDC.getLowestLimitCodingPower()
        binding.ivEnabledProjectMenu.visibility = if (projectMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.textProjectMenu.visibility = if (projectMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivDisabledProjectMenu.visibility = if (!projectMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivProjectMenuLock.visibility = if (!projectMenuVisible) View.VISIBLE else View.INVISIBLE
        binding.ivProjectMenuNew.visibility = if (projectMenuNewIconVisible) View.VISIBLE else View.INVISIBLE
    }

    // 코딩력 변경될 때 핸들링
    override fun onChangeCodingPower(codingPerformance: Int) {
        Log.d("Activity", "onChangeCodingPerformance")
        runOnUiThread { binding.tvCodingPower.text = codingPerformance.toString() + "C" }
        changeMenuFromState()
    }

    override fun onClick(count: Int) {
        Log.d("Activity", "onClick")
        runOnUiThread {
            binding.playerRight.visibility = if (binding.playerRight.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
            binding.playerLeft.visibility = if (binding.playerLeft.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
            binding.tvHelloWorld.text = helloWorld.substring(0, count % (helloWorld.length + 1))
        }
    }

    // 돈 변경될 때 핸들링
    override fun onChangeMoney(money: Int) {
        Log.d("Activity", "onChangeMoney")
        runOnUiThread { binding.tvMoney.text = money.toString() + "\\" }
        changeMenuFromState()
    }

    override fun onChangeEquip(equip: Equip) {
        Log.d("Activity", "onChangeEquip")
        when(equip.type) {
            EquipType.TABLE -> binding.imgDesk.setImageDrawable(ContextCompat.getDrawable(this, equip.resourceId))
            EquipType.CHAIR -> binding.imgChair.setImageDrawable(ContextCompat.getDrawable(this, equip.resourceId))
            EquipType.MONITOR -> binding.imgMoniter.setImageDrawable(ContextCompat.getDrawable(this, equip.resourceId))
            EquipType.INTERIOR -> binding.imgBackground.background = (ContextCompat.getDrawable(this, equip.resourceId))
        }
        changeMenuFromState()
    }

    override fun onStartProject() {
        runOnUiThread { binding.tvExp.visibility = View.VISIBLE }
        changeMenuFromState()
    }

    override fun onProgress(progressRate: Int) {
        Log.d("Activity", progressRate.toString())
        runOnUiThread { binding.tvExp.progress = progressRate }
    }
    
    override fun onCompleteProject(project: Project) {
        ToastController.showToast(this, project.name + " 프로젝트를 완료했습니다.")
    }
}