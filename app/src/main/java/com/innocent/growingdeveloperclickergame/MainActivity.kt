package com.innocent.growingdeveloperclickergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innocent.growingdeveloperclickergame.databinding.ActivityMainBinding
import com.innocent.growingdeveloperclickergame.main.ElementClickerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGotoElementActivity.setOnClickListener {
            val intent = Intent(this, ElementClickerActivity::class.java)
            startActivity(intent)
        }
    }
}