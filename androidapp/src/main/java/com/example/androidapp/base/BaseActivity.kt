package com.example.androidapp.base

import android.os.Bundle
import android.view.ViewStub
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp.R

/**
 * 创建时间：2019-07-15 上午 11:08:53
 * 创建人：王亮（Loren wang）
 * 功能作用：基类Activity
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */

abstract class BaseActivity : AppCompatActivity(), BaseView {

    /**
     * 标题view占位
     */
    private lateinit var vstubHead: ViewStub;
    /**
     * 子view视图占位
     */
    private lateinit var vstubChildView: ViewStub;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        vstubHead = findViewById(R.id.vstubHead)
        vstubChildView = findViewById(R.id.vstubChildView)
        //初始化子view
        onCreateChildView(savedInstanceState)
    }

    /**
     * 添加子view视图
     */
    open fun addChildView(@LayoutRes resId: Int) {
        vstubChildView.layoutResource = resId;
        vstubChildView.inflate();
    }

    override fun hideLoading() {

    }

    override fun showLoading() {

    }

    abstract fun onCreateChildView(savedInstanceState: Bundle?);

}
