package com.example.androidapp

import android.app.Activity
import android.app.Application
import android.lorenwang.tools.AtlwSetting
import android.lorenwang.tools.base.AtlwLogUtils
import android.os.Bundle
import java.util.*

/**
 * 创建时间：2019-07-14 下午 20:23:5
 * 创建人：王亮（Loren wang）
 * 功能作用：自定义application类,实现Activity的记录等操作
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */

class AppApplication : Application() {

    private val TAG = "AppApplication"
    /**
     * 记录所有已经创建的Activity，用来做销毁操作或者获取某个Activity实例使用
     */
    private val actMap = HashMap<String, Activity>()

    override fun onCreate() {
        super.onCreate()

        //注册Activity活动监听
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            var addClassName: String = ""
            var removeClassName: String = ""
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                addClassName = activity.javaClass.getName() + activity.hashCode()
                actMap[addClassName] = activity
                AtlwLogUtils.logI(TAG, "被添加的Activity:::$addClassName")
                AtlwLogUtils.logI(TAG, "添加后:::$actMap")
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                removeClassName = activity.javaClass.getName() + activity.hashCode()
                actMap.remove(removeClassName)
                AtlwLogUtils.logI(TAG, "被移除的Activity:::$removeClassName")
                AtlwLogUtils.logI(TAG, "移除后:::$actMap")

            }
        })
        //初始化工具库设置
        AtlwSetting.registActivityLifecycleCallbacks(this)
        AtlwSetting.isDebug = BuildConfig.DEBUG


    }
}
