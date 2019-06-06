package android.zyf.ktx

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import android.widget.Toast
import timber.log.Timber

/*
fun Activity.checkStoragePermission() {
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    if (Build.VERSION.SDK_INT >= 23) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, App.resources.getString(R.string.check_permission), Toast.LENGTH_SHORT).show()
            }
            //申请权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            Timber.e("PermissionAPI checkPermission: 已经授权！")
        }
    }
}


inline fun Activity.checkInstallPermission(handler: (Boolean) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val hasInstallPermission = this.packageManager.canRequestPackageInstalls()
        if (!hasInstallPermission) {
            Toast.makeText(this, App.resources.getString(R.string.grant_installation), Toast.LENGTH_LONG).show()
            val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + packageName))
            startActivityForResult(intent, 1)
            handler(false)
        } else {
            handler(true)
            Timber.e("PermissionAPI checkPermission: 已经授权！")
        }
    } else {
        handler(true)
    }
}

fun Activity.checkBluetoothPermission() {
    val REQUEST_WRITE_EXTERNAL_STORAGE = 1
    if (Build.VERSION.SDK_INT >= 23) {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(this, App.resources.getString(R.string.check_permission), Toast.LENGTH_SHORT).show()
            }
            //申请权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            Timber.e("PermissionAPI checkPermission:${Manifest.permission.ACCESS_COARSE_LOCATION} 已经授权！")
        }
    }
}*/
