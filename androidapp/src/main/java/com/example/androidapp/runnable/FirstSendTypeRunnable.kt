package com.example.androidapp.runnable

import com.example.androidapp.base.BaseSendTypeRunnable

/**
 * 创建时间：2019-07-15 下午 14:04:57
 * 创建人：王亮（Loren wang）
 * 功能作用：第一个发送器
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class FirstSendTypeRunnable : BaseSendTypeRunnable() {
    override fun run() {
        //如果参数为空则设置一个默认值，方便后面回调
        if (params == null) {
            params = "";
        }
        //做字符串拼接使用
        val resultStr = StringBuffer();
        //按格式拼接数据
        resultStr.append(params!!.length).append("-----").append(params);
        //回调数据
        sendTypeRunnableCallback?.callResult(resultStr.toString())
        //清空缓存数据
        resultStr.setLength(0)
    }
}
