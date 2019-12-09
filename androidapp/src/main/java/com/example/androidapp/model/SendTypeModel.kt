package com.example.androidapp.model

import android.os.Handler
import android.os.Message
import com.example.androidapp.base.BaseModel
import com.example.androidapp.dto.SendTypeDto
import com.example.androidapp.interfaces_abstract.SendTypeCallback
import com.example.androidapp.interfaces_abstract.SendTypeRunnableCallback
import com.example.androidapp.runnable.FirstSendTypeRunnable
import com.example.androidapp.runnable.SecondSendTypeRunnable

/**
 * 创建时间：2019-07-15 下午 14:19:35
 * 创建人：王亮（Loren wang）
 * 功能作用：发送器的Model，mvp层的m，这个页面主要是模拟了网络的发送请求部分
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class SendTypeModel : BaseModel() {
    /**
     * 消息的数据类型是返回
     */
    val MESSAGE_TYPE_RESULT = 0;
    /**
     * 模拟的时间
     */
    val TIME = 10000L;
    /**
     * 模拟数据返回回调
     */
    private var handler = Handler() { message: Message ->
        when (message.what) {
            MESSAGE_TYPE_RESULT -> {
                if (message.obj is SendTypeDto) {
                    //解析回传的数据
                    val sendTypeDto = message.obj as SendTypeDto;
                    sendTypeDto.sendTypeCallback?.modelDataResult(sendTypeDto.result);
                }
            }
            else -> {

            }
        }
        return@Handler true;
    }

    /**
     * 第一个发送器
     */
    fun firstSendType(params: String, sendTypeCallback: SendTypeCallback) {
        val firstSendTypeRunnable = FirstSendTypeRunnable();
        //设置数据回调，用来模拟数据的处理
        firstSendTypeRunnable.sendTypeRunnableCallback = object : SendTypeRunnableCallback {
            override fun callResult(result: String) {
                val message = Message();
                message.what = MESSAGE_TYPE_RESULT;
                message.obj = SendTypeDto(result, sendTypeCallback)
                handler.sendMessageDelayed(message, TIME)
            }
        }
        //设置要操作的数据
        firstSendTypeRunnable.params = params;
        handler.post(firstSendTypeRunnable);
    }

    /**
     * 第二个发送器
     */
    fun secondSendType(params: String, sendTypeCallback: SendTypeCallback) {
        val secondSendTypeRunnable = SecondSendTypeRunnable();
        //设置数据回调，用来模拟数据的处理
        secondSendTypeRunnable.sendTypeRunnableCallback = object : SendTypeRunnableCallback {
            override fun callResult(result: String) {
                val message = Message();
                message.what = MESSAGE_TYPE_RESULT;
                message.obj = SendTypeDto(result, sendTypeCallback)
                handler.sendMessageDelayed(message, TIME)
            }
        }
        //设置要操作的数据
        secondSendTypeRunnable.params = params;
        handler.post(secondSendTypeRunnable);
    }
}
