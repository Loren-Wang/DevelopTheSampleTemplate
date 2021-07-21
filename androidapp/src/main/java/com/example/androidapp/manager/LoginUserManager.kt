package com.example.androidapp.manager

/**
 * 功能作用：登录用户管理
 * 创建时间：2021-01-23 12:20 下午
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
class LoginUserManager private constructor() {

    /**
     * 当前登陆用户信息
     */
    var loginUserInfo: LoginUserInfoBean? = null

    companion object {
        private var optionsInstance: LoginUserManager? = null

        @JvmStatic
        val instance: LoginUserManager
            get() {
                if (optionsInstance == null) {
                    synchronized(this::class.java) {
                        if (optionsInstance == null) {
                            optionsInstance = LoginUserManager()
                        }
                    }
                }
                return optionsInstance!!
            }
    }


    /**
     * 检查用户是否登录
     */
    fun checkUserIsLogin(): Boolean {
        return false
    }

    /**
     * 清除登陆用户信息
     */
    fun clearUserInfo() {
    }

}
