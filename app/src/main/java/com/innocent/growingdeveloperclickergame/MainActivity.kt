package com.innocent.growingdeveloperclickergame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innocent.growingdeveloperclickergame.databinding.ActivityMainBinding
import com.innocent.growingdeveloperclickergame.main.CanvasClickerActivity
import com.innocent.growingdeveloperclickergame.main.ElementCodingPerformanceActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewBinding
         * findViewById 대신 레이아웃(xml)을 inflate 후 만들어진 ActivityMainBinding (activity_main 결합클래스) 객체로 접근 가능
         * @link https://developer.android.com/topic/libraries/view-binding?hl=ko
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

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

        setContentView(binding.root)
    }
}