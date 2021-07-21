package com.example.androidapp

import android.app.Activity
import android.app.Application
import android.lorenwang.commonbaseframe.AcbflwBaseConfig.appCompileTypeIsDebug
import android.lorenwang.commonbaseframe.AcbflwBaseConfig.appCompileTypeIsRelease
import android.lorenwang.commonbaseframe.AcbflwBaseConfig.initBaseConfig
import android.lorenwang.commonbaseframe.network.AcbflwNetworkManager
import android.lorenwang.tools.AtlwConfig
import com.example.androidapp.constant.SharePreKey.PRIVACY_FIRST
import com.example.androidapp.utils.SharedPrefUtil
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinbase.lorenwang.tools.KttlwConfig.DEFAULT_NET_PAGE_INDEX
import kotlinbase.lorenwang.tools.KttlwConfig.DEFAULT_NET_PAGE_SIZE

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
        //异常信息收集
        if (!appCompileTypeIsRelease(BuildConfig.APP_COMPILE_TYPE)) {
            CrashHandler.getInstance()
        }
        //初始化fresco图片加载框架
        Fresco.initialize(this)
        //初始化第三方配置，自己写的，初始化的时候只是变量初始化，这个必须要先初始化，否则很多地方都会崩溃
        initLorenWangTools()
        //初始化SDK调用，因为可能会执行
        if (SharedPrefUtil.instance.getBoolean(PRIVACY_FIRST)) {
            initSdk()
        }
    }

    /**
     * 初始化LorenWang相关框架工具
     */
    private fun initLorenWangTools() {
        AtlwConfig.initAndroidCustomTools(this, appCompileTypeIsDebug(BuildConfig.APP_COMPILE_TYPE),
            if (getExternalFilesDir("logInfo") != null) getExternalFilesDir("logInfo")!!.absolutePath else "")
        //初始化网络请求
        AcbflwNetworkManager.instance.initRetrofit(BuildConfig.APP_COMPILE_TYPE, BuildConfig.API_BASE, BuildConfig.API_BASE, BuildConfig.API_BASE,
            15000L, null, arrayOf())

        initBaseConfig(packageName, "200", null, null, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background)

        //分页数据
        DEFAULT_NET_PAGE_SIZE = 20
        DEFAULT_NET_PAGE_INDEX = 1
    }

    /**
     * 初始第三方sdk相关
     */
    fun initSdk() {
//        //已经同意了隐私协议，然后进行SDK初始化
//        appManager = AppManager.getInstance()
//        appManager.init(this)
//
//        //Mob第三方分享
//        MobSDK.init(this)
//
//        //加密处理
//        val aesKey =
//            "" //AppUtils.isDebug() ? "N1FSWHZzbm9mOHNDRkhIZg==" : AppUtils.isRelease() ? "d3JzcFN0YUxueEJ3Q3BJZg==" : "TG03eVcwVnROT3RMejhuUA==";
//        AesGsonConverterFactory.ENCRYPTION = !TextUtils.isEmpty(aesKey)
//        Aes128CdcUtils.init(aesKey)
//
//        //配置第三方分享登陆等相关
//        try {
//            AcbflwPluginUtil.getInstance().initWeChatConfigInfo(
//                AcbflwWeChatConfigInfoBean.builder().appid(AppConstant.PLUGINS_WX_APP_ID).checkSignature(true).weChatId(AppConstant.PLUGINS_WX_APP_ID)
//                    .build())
//        } catch (ignore: Exception) {
//        }
//        try {
//            AcbflwPluginUtil.getInstance().initSinaConfigInfo(
//                AcbflwSinaConfigInfoBean.builder().redirectUrl(AppConstant.PLUGINS_SINA_REDIRECT_URI).scope(AppConstant.PLUGINS_SINA_APP_SECRET)
//                    .appKey(AppConstant.PLUGINS_SINA_APP_KEY).build())
//        } catch (ignore: Exception) {
//        }
//        //        try {
//        //            AcbflwPluginUtil.getInstance().initQQConfigInfo(AcbflwQQConfigInfoBean.builder().authorities(getMainAppPackageName() + ".fileProvider")
//        //                    .appId(AppConstant.PLUGINS_QQ_APP_ID).build());
//        //        } catch (Exception ignore) {
//        //        }
//        //初始化个推
//        PushManager.getInstance().initialize(this)
//        if (mainAppCompileTypeIsDebug()) {
//            Log.i(TAG, "pushCli_id：" + PushManager.getInstance().getClientid(this))
//            PushManager.getInstance().setDebugLogger(this, object : IUserLoggerInterface() {
//                fun log(s: String?) {
//                    Log.i(TAG, s)
//                }
//            })
//        }
    }
}
