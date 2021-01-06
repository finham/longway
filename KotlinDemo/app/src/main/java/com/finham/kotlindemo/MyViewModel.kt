package com.finham.kotlindemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * User: Fin
 * Date: 2020/2/29
 * Time: 20:45
 */
class MyViewModel : ViewModel() {
    private val _number: MutableLiveData<Int> by lazy { MutableLiveData<Int>().also { it.value = 0 } } //不需要改，只需要赋值一次初始化即可，所以设为val
    val number: LiveData<Int>
    get() = _number
    //val number1=MutableLiveData(0)

    fun modifyNumber(modifiedAmount: Int) {
        _number.value = _number.value?.plus(modifiedAmount)
    }
}