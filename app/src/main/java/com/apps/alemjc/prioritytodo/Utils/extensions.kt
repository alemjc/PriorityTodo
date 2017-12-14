package com.apps.alemjc.prioritytodo.Utils

import android.app.AlertDialog
import android.content.Context

/**
 * Created by Jean Carlos Henriquez on 12/11/17.
 */

fun Context.buildAndShow(message:String, positiveButtonText:String, negativeButtonText:String, callbacks: (result:Boolean) -> Unit){
    val builder = AlertDialog.Builder(this)

    builder.setMessage(message)

    builder.setPositiveButton(positiveButtonText){_,_->
        callbacks(true)
    }

    builder.setNegativeButton(negativeButtonText, {_,_->
        callbacks(false)
    })

    builder.create().show()
}