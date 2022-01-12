package com.innocent.growingdeveloperclickergame.skill

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.innocent.growingdeveloperclickergame.ad.AdService
import com.innocent.growingdeveloperclickergame.ad.OnFetchAdListener
import com.innocent.growingdeveloperclickergame.ad.OnRewardedAdListener
import kotlin.concurrent.thread

object SkillDC {
    private const val SKILL_DELAY: Long = 60000L
    private const val SKILL_DURATION: Long = 10000L
    private var isLocked: Boolean = false
    private var lastSkillEffectFinishTime: Long = 0L
    private var listener: SkillListener? = null
    private var handler: Handler? = null

    fun castSkill(activity: Activity, listener: SkillListener) {
        if (isLocked || getCoolDown() > 0)
            return

        isLocked = true
        this.listener = listener
        listener.onStartLoading()
        AdService.fetch(activity, object: OnFetchAdListener {
            override fun onFetch(success: Boolean) {
                listener.onFinishLoading()
                if (success) onFetchedAd(activity)
                else resetState()
            }
        })
    }

    private fun onFetchedAd(activity: Activity) {
        AdService.show(activity, object: OnRewardedAdListener {
            override fun onRewarded(rewarded: Boolean) {
                if (rewarded) onCompleteSkillCasting()
                else resetState()
            }
        })
    }

    private fun onCompleteSkillCasting() {
        listener?.onStartSkillEffect()

        thread(start = true) {
            Thread.sleep(SKILL_DURATION)
            handleListenerInThread(HandlerMessage.SKILL_EFFECT_FINISH)
        }
    }

    private fun notifySkillCoolDown() {
        val coolDown = getCoolDown()
        listener?.onChangeCoolDown(coolDown)

        if (coolDown > 0) {
            thread(start = true) {
                Thread.sleep(1000)
                handleListenerInThread(HandlerMessage.NOTIFY_SKILL_COOL_DOWN)
            }
        } else {
            resetState()
        }
    }

    private fun getCoolDown(): Long {
        return (SKILL_DELAY - (System.currentTimeMillis() - lastSkillEffectFinishTime)).coerceAtLeast(0)
    }

    private fun resetState() {
        isLocked = false
        lastSkillEffectFinishTime = 0
        listener?.onChangeCoolDown(0)
        listener = null
    }

    private fun handleListenerInThread(handlerMessage: HandlerMessage) {
        if (handler == null)
            createHandler()
        handler!!.obtainMessage(handlerMessage.what).sendToTarget()
    }

    private fun createHandler() {
        handler = object: Handler(Looper.getMainLooper()) {
            override fun handleMessage(inputMessage: Message) {
                when (inputMessage.what) {
                    HandlerMessage.SKILL_EFFECT_FINISH.what -> {
                        lastSkillEffectFinishTime = System.currentTimeMillis()
                        listener?.onFinishSkillEffect()
                        notifySkillCoolDown()
                    }
                    HandlerMessage.NOTIFY_SKILL_COOL_DOWN.what -> {
                        notifySkillCoolDown()
                    }
                }
            }
        }
    }
}

enum class HandlerMessage(val what: Int) {
    SKILL_EFFECT_FINISH(1), NOTIFY_SKILL_COOL_DOWN(2)
}

interface SkillListener {
    fun onStartLoading()
    fun onFinishLoading()
    fun onStartSkillEffect()
    fun onFinishSkillEffect()
    fun onChangeCoolDown(coolDown: Long)
}