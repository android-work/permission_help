package com.android.work.permission_util

import android.Manifest

/**
 * 位置权限
 * ACCESS_LOCATION_EXTRA_COMMANDS: 申请调用A-GPS模块
 */
val LOCAL_PERMISSION = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
)

/**
 * 位置请求requestCode
 */
const val LOCAL_REQUEST_CODE = 101

/**
 * 动态申请权限requestCode
 */
const val PERMISSION_REQUEST_CODE = 10001