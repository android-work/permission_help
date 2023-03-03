package com.android.work.permissiondemo

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import com.android.work.permission_util.*

class MainActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PermissionResult.build(this).setCallback(object : PermissionCallback {
            override fun permissionGrantSuccess() {
                Toast.makeText(this@MainActivity, "权限申请通过", 0).show()
            }

            override fun permissionGrantFailed(noGrantList: SparseArray<String>?) {
                Toast.makeText(this@MainActivity, "权限申请不通过", 0).show()
            }

        }).setPermission(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
        ).setPermissionDesInfo(
            PermissionDesInfo(
                title = "老子要你给权限",
                desMessage = "你别废话，老子就要你把权限全都给老子，否则揍不死你",
                clickOutClose = false,
            )
        ).setCustomDialogFunction {
            AlertDialog.Builder(this)
                .setTitle("自定义权限弹窗")
                .setMessage("滚，别跟老子比比，赶紧、马上、立刻、现在给老子把权限给同意咯")
                .setNegativeButton("滚蛋",DialogInterface.OnClickListener{
                    _, _ ->
                    PermissionHelper.getInstance().cancelFunction.invoke(this)
                }).setPositiveButton("好，马上弄",DialogInterface.OnClickListener{
                    _,_ ->
                    PermissionHelper.getInstance().actionFunction.invoke(this)
                }).setCancelable(false)
                .create()
                .show()
        }.request()
    }
}