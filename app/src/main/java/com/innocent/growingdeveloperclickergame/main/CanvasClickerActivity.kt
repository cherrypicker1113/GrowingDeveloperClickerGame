package com.innocent.growingdeveloperclickergame.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innocent.growingdeveloperclickergame.databinding.ActivityCanvasClickerBinding

class CanvasClickerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCanvasClickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCanvasClickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}