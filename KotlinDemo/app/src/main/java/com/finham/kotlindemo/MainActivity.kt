package com.finham.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val model1 = ViewModelProviders.of(this)[MyViewModel::class.java]
        //val model2 = ViewModelProviders.of(this).get(MyViewModel::class.java)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.number.observe(this, Observer { textView.text=it.toString() })
        button_add.setOnClickListener {
            //textView.text = (number++).toString()
            viewModel.modifyNumber(1)
        }

        button_subtract.setOnClickListener {
            //textView.text = (number--).toString()
            viewModel.modifyNumber(-1)
        }
    }
}
