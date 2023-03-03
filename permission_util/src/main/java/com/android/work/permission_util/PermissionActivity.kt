package com.android.work.permission_util

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray

open class PermissionActivity : Activity() {

    override fun onDestroy() {
        PermissionHelper.getInstance().removeResult(this::class.java.name)
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionResult = PermissionHelper.getInstance().getResult(this::class.java.name)
        if (requestCode == permissionResult?.resultCode){
            val noGrantResult = grantResults.any { grantResult ->
                grantResult != PackageManager.PERMISSION_GRANTED
            }
            if(noGrantResult && permissionResult.permissionCallback != null) {
                val sparseArray = SparseArray<String>()
                grantResults.forEachIndexed { index, value ->
                    if (value != PackageManager.PERMISSION_GRANTED){
                        sparseArray.append(index,permissions[index])
                    }
                }
                permissionResult.permissionCallback?.permissionGrantFailed(sparseArray)
            }else if (!noGrantResult && permissionResult.permissionCallback != null){
                permissionResult.permissionCallback?.permissionGrantSuccess()
            }
        }
    }
}