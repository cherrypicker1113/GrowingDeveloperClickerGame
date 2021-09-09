package com.innocent.growingdeveloperclickergame.project

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.databinding.ProjectListPopupBinding

class ProjectListPopup(private val activity: Activity) {
    private lateinit var binding: ProjectListPopupBinding
    private var popupWindow: PopupWindow? = null

    fun show() {
        binding = ProjectListPopupBinding.inflate(activity.layoutInflater)
        val popupView: View = binding.root

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow!!.isFocusable = true
        popupWindow!!.setBackgroundDrawable(null)
        popupWindow!!.showAtLocation(popupView, Gravity.TOP, 0, 0)

        binding.close.setOnClickListener { close() }
        binding.project1.setOnClickListener { startProject(0) }
        binding.project2.setOnClickListener { startProject(1) }
    }

    private fun startProject(projectIdx: Int) {
//        if (ProjectDC.hasProjectInProgress()) {
//            ToastController.showToast(activity, "진행중인 프로젝트가 있습니다.")
//            return
//        }
        if (!ProjectDC.canProject(projectIdx)) {
            ToastController.showToast(activity, "코딩력이 부족합니다.")
            return
        }
        val project = ProjectDC.startProject(projectIdx)
        if (project != null)
            ToastController.showToast(activity, project.name + " 프로젝트를 시작합니다.")
        close()
    }

    private fun close() {
        popupWindow!!.dismiss()
    }
}