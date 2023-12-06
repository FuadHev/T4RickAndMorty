package com.fuadhev.rickandmortyapp.common.utils

import android.app.Activity
import android.view.View
import com.shashank.sony.fancytoastlib.FancyToast

object Extensions {
    fun Activity.showMessage(
        message: String,
        style: Int,
    ) {
        FancyToast.makeText(this,message, FancyToast.LENGTH_SHORT,style,false).show()
    }

    fun View.visible(){
        this.visibility = View.VISIBLE
    }

    fun View.gone(){
        this.visibility = View.GONE
    }

    fun List<View>.goneEach(){
        this.forEach{
            it.gone()
        }
    }

    fun List<View>.visibleEach(){
        this.forEach{
            it.visible()
        }
    }
}