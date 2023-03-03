package com.android.work.permission_util

import android.app.Activity
import android.view.View

class PermissionResult(private val activity: Activity) {
    /**
     * 权限申请结果回调
     */
    var permissionCallback: PermissionCallback? = null

    /**
     * 需要申请的权限，可多个权限申请
     */
    var permissions:Array<out String>? = null

    /**
     * resultCode
     */
    var resultCode:Int = 1001

    /**
     * 申请权限前添加弹窗描述信息对象,null表示无需描述
     */
    var permissionDesInfo:PermissionDesInfo? = null

    /**
     * 自定义对话框回调
     */
    var customDialogFunction:(() -> Unit)? = null


    fun setCallback(permissionCallback: PermissionCallback):PermissionResult {
        this.permissionCallback = permissionCallback
        return this
    }

    fun setPermission(vararg permissions:String):PermissionResult{
        this.permissions = permissions.clone()
        return this
    }

    fun setResultCode(resultCode:Int):PermissionResult{
        this.resultCode = resultCode
        return this
    }

    fun setCustomDialogFunction(customDialogFunction:(() -> Unit)):PermissionResult{
        this.customDialogFunction = customDialogFunction
        return this
    }

    fun setPermissionDesInfo(permissionDes:PermissionDesInfo):PermissionResult{
        this.permissionDesInfo = permissionDes
        return this
    }

    fun request():Boolean {
        return PermissionHelper.getInstance().request(activity,permissions?: arrayOf(),resultCode)
    }

    companion object{
        fun build(activity: Activity):PermissionResult{
            val permissionResult = PermissionResult(activity)
            PermissionHelper.getInstance().putResult(activity::class.java.name,permissionResult)
            return permissionResult
        }
    }
}