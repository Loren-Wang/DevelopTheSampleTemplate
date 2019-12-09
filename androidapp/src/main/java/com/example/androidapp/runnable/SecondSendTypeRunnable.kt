package com.example.androidapp.runnable

import com.example.androidapp.base.BaseSendTypeRunnable

/**
 * 创建时间：2019-07-15 下午 14:11:14
 * 创建人：王亮（Loren wang）
 * 功能作用：第二个发送器
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class SecondSendTypeRunnable : BaseSendTypeRunnable() {
    override fun run() {
        //如果参数为空则设置一个默认值，方便后面回调
        if (params == null) {
            params = "";
        }
        //做字符串拼接使用
        val resultStr = StringBuffer();
        //按格式拼接数据
        resultStr.append(getSum(params!!.length)).append("*****");
        //参数字符串长度判断
        if (params!!.length > 10) {
            resultStr.append(params!!.subSequence(0, 10))
        } else {
            resultStr.append(params)
        }
        //回调数据
        sendTypeRunnableCallback?.callResult(resultStr.toString())
        //清空缓存数据
        resultStr.setLength(0)
    }

    /**
     * 使用递归获取累加数据，从最大值加到最小值，逆序相加
     */
    fun getSum(maxNum: Int): Int {
        if (maxNum > 0) {
            return maxNum + getSum(maxNum - 1)
        } else {
            return maxNum
        }
    }
}
