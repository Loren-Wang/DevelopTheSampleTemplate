package com.example.androidapp.base

import com.example.androidapp.interfaces_abstract.SendTypeRunnableCallback

/**
 * 创建时间：2019-07-15 下午 14:02:51
 * 创建人：王亮（Loren wang）
 * 功能作用：不同类型发送器runnable
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
abstract class BaseSendTypeRunnable : Runnable {
    var sendTypeRunnableCallback: SendTypeRunnableCallback? = null
    var params: String? = null
}
