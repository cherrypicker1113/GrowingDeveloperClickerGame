package com.innocent.growingdeveloperclickergame.main

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.innocent.growingdeveloperclickergame.ad.AdService
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding
import com.innocent.growingdeveloperclickergame.equip.*
import com.innocent.growingdeveloperclickergame.project.Project
import com.innocent.growingdeveloperclickergame.project.ProjectDC
import com.innocent.growingdeveloperclickergame.project.ProjectDCListener
import com.innocent.growingdeveloperclickergame.project.ProjectListPopup
import com.innocent.growingdeveloperclickergame.skill.SkillDC
import com.innocent.growingdeveloperclickergame.skill.SkillListener


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        MainDC.fetchData(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnTouchListener { v, event ->
            val action = event.action
            if (action === MotionEvent.ACTION_DOWN
                || (action and MotionEvent.ACTION_POINTER_DOWN) === MotionEvent.ACTION_POINTER_DOWN) {

                ClickEffect(event.x, event.y, CodingPowerDC.getCodingPowerRate())
                    .render(layoutInflater, binding.root)
                CounterDC.click()
            }
            MainDC.saveData(this)
            return@setOnTouchListener true
        }
        binding.btnProjectMenu.setOnClickListener { ProjectListPopup(this).show() }
        binding.btnEquipMenu.setOnClickListener { EquipListPopup(this).show() }
        initSkillBtn()

        if (ProjectDC.hasProjectInProgress()) {
            binding.tvExp.visibility = View.VISIBLE
        }
        changeMenuFromState()
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        AdService.clear()
    }

    private fun initSkillBtn() {
        binding.btnSkill.setOnClickListener {
            SkillDC.castSkill(this, object: SkillListener {
                override fun onStartLoading() {
                    binding.btnSkill.text = "광고 로딩중"
                }

                override fun onFinishLoading() {
                    binding.btnSkill.text = "스킬"
                }

                override fun onStartSkillEffect() {
                    CodingPowerDC.setSkillEffect(true)
                    binding.btnSkill.text = "스킬 효과 적용중"
                }

                override fun onFinishSkillEffect() {
                    CodingPowerDC.setSkillEffect(false)
                }

                override fun onChangeCoolDown(coolDown: Long) {
                    binding.btnSkill.text = if (coolDown > 0) (coolDown / 1000).toString() else "스킬"
                }
            })
        }
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