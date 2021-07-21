package com.example.androidapp.activity

import android.os.Bundle
import com.example.androidapp.R
import com.example.androidapp.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreateChildView(savedInstanceState: Bundle?) {
        addChildView(R.layout.activity_main)
    }

}
