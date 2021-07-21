package com.example.androidapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.lorenwang.commonbaseframe.AcbflwBaseConfig
import android.lorenwang.tools.app.AtlwSharedPrefUtil
import com.example.androidapp.BuildConfig
import com.example.androidapp.constant.SharePreKey
import com.example.androidapp.manager.LoginUserManager
import javabase.lorenwang.dataparse.JdplwJsonUtils
import javabase.lorenwang.tools.common.JtlwDateTimeUtils
import kotlinbase.lorenwang.tools.extend.kttlwEmptyCheck
import kotlinbase.lorenwang.tools.extend.kttlwToJsonData

/**
 * 功能作用：共享存储工具
 * 创建时间：2021-03-03 10:27
 * 创建人：王亮（Loren）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 *
 * @author 王亮（Loren）
 */
class SharedPrefUtil private constructor() {
    /**
     * 使用的共享存储
     */
    private val sharedPreferences: SharedPreferences = if (AcbflwBaseConfig.appCompileTypeIsRelease(BuildConfig.APP_COMPILE_TYPE)) {
        AtlwSharedPrefUtil.getInstance().getSharedPreferences("SPs.FILE_NAME", Context.MODE_PRIVATE)
    } else {
        AtlwSharedPrefUtil.getInstance()
            .getSharedPreferences(AtlwSharedPrefUtil.getInstance().getString(SharePreKey.BUILD_TYPE_MAIN_DATA_KEY, "SPs.FILE_NAME"),
                Context.MODE_PRIVATE)
    }

    /**
     * 记录的时间格式
     */
    private val recordTimePattern = "yyyyMMdd"

    companion object {
        private var optionsInstance: SharedPrefUtil? = null

        @JvmStatic
        val instance: SharedPrefUtil
            get() {
                if (optionsInstance == null) {
                    synchronized(this::class.java) {
                        if (optionsInstance == null) {
                            optionsInstance = SharedPrefUtil()
                        }
                    }
                }
                return optionsInstance!!
            }
    }

    /**
     * 插入字符串数据
     */
    fun putString(key: String, value: String) {
        AtlwSharedPrefUtil.getInstance().putString(sharedPreferences, key, value)
    }

    /**
     * 获取字符串数据
     */
    fun getString(key: String): String? {
        return AtlwSharedPrefUtil.getInstance().getString(sharedPreferences, key, null)
    }

    /**
     * 存储布尔值数据
     */
    fun putBoolean(key: String, value: Boolean) {
        AtlwSharedPrefUtil.getInstance().putBoolean(sharedPreferences, key, value)
    }

    /**
     * 获取布尔值数据
     */
    fun getBoolean(key: String): Boolean {
        return AtlwSharedPrefUtil.getInstance().getBoolean(sharedPreferences, key, false)
    }

    /**
     * 获取布尔值数据
     */
    fun getBoolean(key: String, default: Boolean): Boolean {
        return AtlwSharedPrefUtil.getInstance().getBoolean(sharedPreferences, key, default)
    }

    /**
     * 移除数据
     */
    fun removeData(key: String) {
        AtlwSharedPrefUtil.getInstance().remove(sharedPreferences, key)
    }


    /**
     * 插入用户相关记录,格式id_time_value
     */
    fun putUserRecord(key: String, value: String) {
        if (LoginUserManager.instance.checkUserIsLogin()) {
            val id = LoginUserManager.instance.loginUserInfo!!.userId!!
            putUserRecord(id, key, value)
        }
    }

    /**
     * 获取用户相关记录,格式id_time_value
     */
    fun getUserRecord(key: String): String? {
        if (LoginUserManager.instance.checkUserIsLogin()) {
            val id = LoginUserManager.instance.loginUserInfo!!.userId!!
            return getUserRecord(id, key)
        }
        return null
    }

    /**
     * 插入用户相关记录,格式id_time_value
     */
    fun putUserRecord(id: String, key: String, value: String) {
        //清除旧数据
        getUserRecord(id, key, clearOld = true, today = true)
        //插入新数据
        val list = getString(key).kttlwEmptyCheck({ arrayListOf<String>() }, {
            JdplwJsonUtils.fromJsonArray(it, String::class.java)
        })
        list.add("${id}_${JtlwDateTimeUtils.getInstance().getFormatDateNowTime(recordTimePattern)}_${value}")
        putString(key, list.kttlwToJsonData())
    }

    /**
     * 获取用户相关记录,格式id_time_value
     */
    fun getUserRecord(id: String, key: String): String? {
        return getUserRecord(id, key, clearOld = true, today = true)
    }

    /**
     * 获取用户相关记录,格式id_time_value
     * @param clearOld 是否清除旧数据
     * @param today 是否要返回今天的数据
     */
    fun getUserRecord(id: String, key: String, clearOld: Boolean, today: Boolean): String? {
        //数据列表
        val list = getString(key).kttlwEmptyCheck({ arrayListOf<String>() }, {
            JdplwJsonUtils.fromJsonArray(it, String::class.java)
        })
        var split: List<String>
        //判断是否要清除旧数据
        if (clearOld) {
            //当前记录
            var current: List<String>? = null
            //其他数据
            val otherList = arrayListOf<String>()
            for (item in list) {
                split = item.split("_")
                if (split.size == 3) {
                    //判断当前处理的数据是否是要查找的数据
                    if (split[0] == id) {
                        //判断是否是要当天的数据，如果不是要当天的数据则读取数据返回，同时加入到其他数据列表
                        if (today) {
                            //当前需要的是当天的数据，如果是当天的数据则返回并加入到其他列表中，否则不处理，清除掉之前时间的数据，保证数据量最小
                            if (JtlwDateTimeUtils.getInstance().getFormatDateNowTime(recordTimePattern) == split[1]) {
                                current = split
                                otherList.add(item)
                            }
                        } else {
                            current = split
                            otherList.add(item)
                        }
                    } else {
                        otherList.add(item)
                    }
                } else {
                    otherList.add(item)
                }
            }
            //清除数据
            putString(key, otherList.kttlwToJsonData())
            //判断是否要返回数据
            if (current != null) {
                return current[2]
            }
        } else {
            for (item in list) {
                split = item.split("_")
                if (split.size == 3) {
                    if (split[0] == id) {
                        if (today) {
                            if (JtlwDateTimeUtils.getInstance().getFormatDateNowTime(recordTimePattern) == split[1]) {
                                return split[2]
                            }
                        } else {
                            return split[2]
                        }
                    }
                }
            }
        }
        return null
    }


    /**
     * 插入每日相关记录
     */
    fun putDayRecord(key: String) {
        putString(key, JtlwDateTimeUtils.getInstance().getFormatDateNowTime(recordTimePattern))
    }

    /**
     * 获取每日相关记录,格式id_time_value
     */
    fun getDayRecord(key: String): String? {
        val time = getString(key)
        return if (time == JtlwDateTimeUtils.getInstance().getFormatDateNowTime(recordTimePattern)) {
            time
        } else {
            null
        }
    }
}
