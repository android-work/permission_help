package com.android.work.permission_util

import android.util.SparseArray

interface PermissionCallback {

    /**
     * 权限满足回调
     */
    fun permissionGrantSuccess()

    /**
     * 权限不满足回调
     */
    fun permissionGrantFailed(noGrantList:SparseArray<String>? = null)
}