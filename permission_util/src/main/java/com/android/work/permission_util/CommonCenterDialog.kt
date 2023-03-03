package com.android.work.permission_util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class CommonCenterDialog(private val activity: Activity, private val builder: Builder) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_center_layout,null)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        layoutParams.marginStart = 40
        layoutParams.marginEnd = 40
        setContentView(view,layoutParams)
        val title = view.findViewById<TextView>(R.id.dialog_title)
        val message = view.findViewById<TextView>(R.id.dialog_message)
        val cancelBtn = view.findViewById<TextView>(R.id.cancel_button)
        val actionBtn = view.findViewById<TextView>(R.id.action_button)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(builder.clickOutClose)

        title.text = builder.title ?: ""
        message.text = builder.message ?: ""
        cancelBtn.visibility = if (builder.showCancel) View.VISIBLE else View.GONE
        cancelBtn.text = builder.cancelText
        actionBtn.visibility = if (builder.showAction) View.VISIBLE else View.GONE
        actionBtn.text = builder.actionText

        cancelBtn.setOnClickListener {
            builder.cancelFunction?.invoke()
            dismiss()
        }

        actionBtn.setOnClickListener {
            builder.actionFunction?.invoke()
            dismiss()
        }
    }

    class Builder(private val activity: Activity) {
        var title:String? = null
        var message:String? = null
        var showCancel:Boolean = true
        var showAction:Boolean = true
        var cancelText:String = "取消"
        var actionText:String = "确定"
        var clickOutClose:Boolean = false
        var cancelFunction:(() -> Unit)? = null
        var actionFunction:(() -> Unit)? = null

        fun setTitle(title:String):Builder{
            this.title = title
            return this
        }

        fun setMessage(message: String):Builder{
            this.message = message
            return this
        }

        fun setShowCancel(showCancel:Boolean):Builder{
            this.showCancel = showCancel
            return this
        }

        fun setShowAction(showAction:Boolean):Builder{
            this.showAction = showAction
            return this
        }

        fun setCancelText(cancelText:String):Builder{
            this.cancelText = cancelText
            return this
        }

        fun setClickOutClose(clickOutClose:Boolean):Builder{
            this.clickOutClose = clickOutClose
            return this
        }

        fun setActionText(actionText:String):Builder{
            this.actionText = actionText
            return this
        }

        fun setCancelFunction(cancelFunction: (() -> Unit)?):Builder{
            this.cancelFunction = cancelFunction
            return this
        }

        fun setActionFunction(actionFunction: (() -> Unit)?):Builder{
            this.actionFunction = actionFunction
            return this
        }

        fun build():CommonCenterDialog{
            return CommonCenterDialog(activity,this)
        }
    }
}
