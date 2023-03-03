package com.android.work.permission_util

import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback

class LocalPermission : OnRequestPermissionsResultCallback {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }
}