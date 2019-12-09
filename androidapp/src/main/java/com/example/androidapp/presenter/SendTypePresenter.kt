package com.example.androidapp.presenter

import com.example.androidapp.activity.MainActivity
import com.example.androidapp.base.BasePersenter
import com.example.androidapp.interfaces_abstract.SendTypeCallback
import com.example.androidapp.model.SendTypeModel

/**
 * 创建时间：2019-07-15 下午 14:39:30
 * 创建人：王亮（Loren wang）
 * 功能作用：发送器presenter，mvp模型的p层，用来处理返回的数据
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */
class SendTypePresenter : BasePersenter() {
    var sendTypeModel = SendTypeModel()

    /**
     * 使用第一个发送器
     */
    fun firstSendType(params: String) {
        sendTypeModel.firstSendType(params, object : SendTypeCallback {
            override fun modelDataResult(result: String) {
                if (baseView is MainActivity) {
                    (baseView as MainActivity).showResult(result);
                }
            }
        })
    }

    /**
     * 使用第二个发送器
     */
    fun secondSendType(params: String) {
        sendTypeModel.secondSendType(params, object : SendTypeCallback {
            override fun modelDataResult(result: String) {
                if (baseView is MainActivity) {
                    (baseView as MainActivity).showResult(result);
                }
            }
        })
    }

}
