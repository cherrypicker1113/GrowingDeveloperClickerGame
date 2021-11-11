package com.innocent.growingdeveloperclickergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.innocent.growingdeveloperclickergame.databinding.ActivityMainBinding
import com.innocent.growingdeveloperclickergame.databinding.ResetButtonBinding
import com.innocent.growingdeveloperclickergame.main.ElementClickerActivity
import com.innocent.growingdeveloperclickergame.main.MainDC

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).load(R.raw.splash).into(binding.imgBackground)

        binding.btnGotoElementActivity.setOnClickListener {
            val intent = Intent(this, ElementClickerActivity::class.java)
            startActivity(intent)
        }

        if (BuildConfig.DEBUG) {
            val resetBtn = ResetButtonBinding.inflate(layoutInflater).root
            resetBtn.setOnClickListener { MainDC.initData(this) }
            binding.root.addView(resetBtn)
        }
    }
}