package com.android.work.permission_util

data class PermissionDesInfo(
    val title: String? = null,
    val desMessage: String? = null,
    val cancelText: String? = null,
    val actionText: String? = null,
    val showAction: Boolean? = null,
    val showCancel: Boolean? = null,
    val clickOutClose:Boolean = false,
)