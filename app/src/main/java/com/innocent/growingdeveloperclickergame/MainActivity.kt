package com.innocent.growingdeveloperclickergame

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.innocent.growingdeveloperclickergame.ad.AdService
import com.innocent.growingdeveloperclickergame.databinding.ActivityMainBinding
import com.innocent.growingdeveloperclickergame.databinding.ResetButtonBinding
import com.innocent.growingdeveloperclickergame.main.ElementClickerActivity
import com.innocent.growingdeveloperclickergame.main.MainDC

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(R.raw.splash).into(binding.imgBackground)

        AdService.ready(this, null)
        sharedPreference = getSharedPreferences("show_init_dialog", 0)

        MainDC.fetchData(this)

        binding.btnGotoElementActivity.setOnClickListener {
            // 기존 데이터가 존재하는 유저 중 해당 버전에서 dialog를 실행한 적 없는 경우 show
            val showDialog = MainDC.existData() && sharedPreference.getBoolean("1.0.2", true)

            if (showDialog) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("업데이트 알림")
                builder.setMessage("새로운 컨텐츠 요소가 추가되었습니다. 변경 사항이 많아 처음부터 시작 하는것을 권장합니다.\n모든 데이터를 초기화 하고 새로 시작하시겠습니까?\n\n(초기화 할 경우 복구는 불가능 합니다.)")
                builder.setPositiveButton("네(초기화)") { dialogInterface: DialogInterface, i: Int ->
                    MainDC.initData(this)
                    startGame()
                }
                builder.setNegativeButton("아니오") { dialogInterface: DialogInterface, i: Int ->
                    // 보상 제공
                    startGame(true)
                }
                builder.show()
            } else {
                startGame()
            }
        }

        if (BuildConfig.DEBUG) {
            val resetBtn = ResetButtonBinding.inflate(layoutInflater).root
            resetBtn.setOnClickListener { MainDC.initData(this) }
            binding.root.addView(resetBtn)
        }
    }

    fun startGame(dataCorrection: Boolean = false) {
        sharedPreference.edit().putBoolean("1.0.2", false).apply()

        val intent = Intent(this, ElementClickerActivity::class.java)
        intent.putExtra("dataCorrection", dataCorrection);

        startActivity(intent)
    }
}