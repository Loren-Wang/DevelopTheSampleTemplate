package com.example.androidapp.activity

import android.lorenwang.customview.dialog.AvlwBottomListOptionsDialogType1
import android.os.Bundle
import android.widget.TextView
import com.example.androidapp.R
import com.example.androidapp.base.BaseActivity
import com.example.androidapp.presenter.SendTypePresenter

class MainActivity : BaseActivity() {
    private lateinit var tvShowState: TextView
    private lateinit var sendTypeSelectDialog: AvlwBottomListOptionsDialogType1
    private var sendTypePresent = SendTypePresenter()
    private var PARAMS = "测试发送器数据";

    override fun onCreateChildView(savedInstanceState: Bundle?) {
        addChildView(R.layout.activity_main)
        tvShowState = findViewById(R.id.tvShowState)

        //初始化p层
        sendTypePresent.baseView = this;


        //初始化弹窗
        sendTypeSelectDialog = AvlwBottomListOptionsDialogType1(this);
        sendTypeSelectDialog.setOptionsList(arrayOf(getString(R.string.send_type_first), getString(R.string.send_type_second)), null, null, null, null, null, null)
        sendTypeSelectDialog.setOnOptionsItemClick { posi, _ ->
            //判断是否显示，显示的话则隐藏
            if (sendTypeSelectDialog.isShowing) {
                sendTypeSelectDialog.dismiss();
            }
            tvShowState.append(getString(R.string.send_start));
            tvShowState.append("\n");
            when (posi) {
                0 -> {
                    sendTypePresent.firstSendType(PARAMS)
                }
                1 -> {
                    sendTypePresent.secondSendType(PARAMS)
                }
                else -> {

                }
            }
        }

        //显示初始数据
        tvShowState.append(getString(R.string.send_params));
        tvShowState.append(PARAMS);
        tvShowState.append("\n");
    }

    override fun onResume() {
        super.onResume()
        //判断是否显示，没有显示的话则显示
        if (!sendTypeSelectDialog.isShowing) {
            sendTypeSelectDialog.show();
        }
    }

    /**
     * 显示数据
     */
    fun showResult(result: String) {
        runOnUiThread {
            //拼接显示数据
            tvShowState.append(getString(R.string.send_result));
            tvShowState.append(result)
            tvShowState.append("\n");
        }
    }

}
