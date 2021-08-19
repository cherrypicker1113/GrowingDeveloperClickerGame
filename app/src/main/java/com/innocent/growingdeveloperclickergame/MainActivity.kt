package com.innocent.growingdeveloperclickergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innocent.growingdeveloperclickergame.databinding.ActivityMainBinding
import com.innocent.growingdeveloperclickergame.main.CanvasClickerActivity
import com.innocent.growingdeveloperclickergame.main.ElementCodingPerformanceActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.innocent.growingdeveloperclickergame.common.SharedPreferencesService
import com.innocent.growingdeveloperclickergame.player.Player
import com.innocent.growingdeveloperclickergame.skill.Skill
import com.innocent.growingdeveloperclickergame.skill.SkillService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 리소스 로딩 중 fetch
        player = SharedPreferencesService.fetchPlayer(this)

        /**
         * ViewBinding
         * findViewById 대신 레이아웃(xml)을 inflate 후 만들어진 ActivityMainBinding (activity_main 결합클래스) 객체로 접근 가능
         * @link https://developer.android.com/topic/libraries/view-binding?hl=ko
         */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.player.value = player

        viewModel.player.observe(this, {
            binding.powerText.text = it.power.toString()
            binding.assetText.text = it.asset.toString()
            SharedPreferencesService.savePlayer(this, player)
        })

        binding.increasePowerButton.setOnClickListener { viewModel.player.value = player.increasePower() }
        binding.increaseAssetButton.setOnClickListener { viewModel.player.value = player.addAsset(100) }

        binding.skill1Button.setOnClickListener {
            SkillService.useSkill(player, Skill.POWER_DOUBLE)
            viewModel.player.value = player
        }
        binding.skill2Button.setOnClickListener {
            SkillService.useSkill(player, Skill.POWER_TRIPLE)
            viewModel.player.value = player
        }

        binding.clearButton.setOnClickListener {
            player.power = 0
            player.asset = 0
            viewModel.player.value = player
        }

        // Canvas는 아직 미구현
        binding.btnGoToCanvasActivity.setOnClickListener {
            val intent = Intent(this, CanvasClickerActivity::class.java)
            startActivity(intent)
        }

        // Element?로 만든 클리커 게임 Activity 실행
        binding.btnGotoElementActivity.setOnClickListener {
            val intent = Intent(this, ElementCodingPerformanceActivity::class.java)
            startActivity(intent)
        }
    }
}

class MainViewModel : ViewModel() {
    val player: MutableLiveData<Player> by lazy {
        MutableLiveData<Player>()
    }
}