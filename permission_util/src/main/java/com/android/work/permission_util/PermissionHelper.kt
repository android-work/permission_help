package com.android.work.permission_util

import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper {

    companion object {
        private val mPermissionHelper = PermissionHelper()
        fun getInstance(): PermissionHelper {
            return mPermissionHelper
        }
    }

    private val permissionResultMap = mutableMapOf<String, PermissionResult>()

    fun putResult(clazzName: String, permissionResult: PermissionResult) {
        permissionResultMap[clazzName] = permissionResult
    }

    fun removeResult(clazzName: String) {
        permissionResultMap.remove(clazzName)
    }

    fun getResult(clazzName: String) = permissionResultMap[clazzName]

    /**
     * 检查权限
     */
    private fun checkPermission(activity: Activity, permissions: Array<out String>): Boolean {
        return !(permissions.any { permission ->
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        })
    }

    /**
     * 请求权限
     */
    fun request(activity: Activity, permissions: Array<out String>, requestCode: Int): Boolean {
        val permissionResult = permissionResultMap[activity.javaClass.name]

        // 检查权限
        val checkResult = checkPermission(activity, permissions)
        if (!checkResult) {
            // 权限申请前的描述，如果为null默认是继续请求权限的
            val continueRequest = permissionResult?.permissionDesInfo != null
            // 有描述，说明需要展示弹窗
            if (continueRequest) {
                if (permissionResult?.customDialogFunction != null){
                    permissionResult.customDialogFunction?.invoke()
                }else {
                    showDesDialog(activity, permissionResult)
                }
            } else {
                // 权限不满足，申请权限
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            }
        } else {
            // 权限以满足，直接回调权限成功回调
            permissionResult?.permissionCallback?.permissionGrantSuccess()
        }
        return checkResult
    }

    private fun showDesDialog(
        activity: Activity,
        permissionResult: PermissionResult?,
    ) {
        CommonCenterDialog.Builder(activity)
            .setTitle(permissionResult?.permissionDesInfo?.title ?: "")
            .setMessage(permissionResult?.permissionDesInfo?.desMessage ?: "")
            .setCancelText(permissionResult?.permissionDesInfo?.cancelText ?: "取消")
            .setActionText(permissionResult?.permissionDesInfo?.actionText ?: "确定")
            .setShowAction(permissionResult?.permissionDesInfo?.showAction ?: true)
            .setShowCancel(permissionResult?.permissionDesInfo?.showCancel ?: true)
            .setClickOutClose(permissionResult?.permissionDesInfo?.clickOutClose ?: false)
            .setCancelFunction {
                cancelFunction.invoke(activity)
            }
            .setActionFunction {
                // 权限不满足，申请权限
                actionFunction.invoke(activity)
            }.build().show()
    }

    val cancelFunction = { activity:Activity ->
        permissionResultMap[activity.javaClass.name]?.permissionCallback?.permissionGrantFailed()
    }

    val actionFunction = { activity:Activity ->
        val result = permissionResultMap[activity.javaClass.name]
        ActivityCompat.requestPermissions(activity, result?.permissions?: arrayOf(), result?.resultCode?: PERMISSION_REQUEST_CODE)
    }
}
