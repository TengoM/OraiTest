package com.orai.oraitest.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast

fun Context.shareContent(shareText: String, intentType: String = Intent.ACTION_SEND, type: String = "text/plain") {
    val shareIntent = Intent(intentType)
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
    shareIntent.type = type
    startActivity(shareIntent)
}

fun Context.showToast(text: String, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, toastLength).show()
}

fun Context.getConnectivityService(): ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


fun Context.showToast(@StringRes text: Int, toastLength: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, toastLength).show()
}

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.openUrl(url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)
}

fun Context.makeToast(@StringRes text: Int, toastDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, toastDuration).show()
}

fun Context.makeToast(text: String, toasdDuration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, toasdDuration).show()
}

fun Context.getWindowWidth(): Int = resources.displayMetrics.widthPixels
fun Context.getWindowHeight(): Int = resources.displayMetrics.heightPixels

fun Context.getWindowWidthInDP(): Int {
    val dens = resources.displayMetrics.density
    return (getWindowWidth() / dens).toInt()
}

fun Context.getDencity(): Float = resources.displayMetrics.density

fun Activity.changeStatusBarColor(color: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = color
}

fun Activity.havePermissionsFor(permissionList: Array<String>): Boolean{
    permissionList.forEach{
        if(!havePermissionFor(it))
            return false
    }
    return true
}

fun Activity.havePermissionFor(permission: String) = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun Context.eGetColor(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun AppCompatActivity.replaceFragment(@IdRes layoutId: Int, fragment: Fragment, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null) {
    try {
        val transaction = supportFragmentManager.beginTransaction()
        if (updateTransaction != null) {
            updateTransaction(transaction)
        }
        transaction?.replace(layoutId, fragment)?.commit()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun AppCompatActivity.addFragment(@IdRes layoutId: Int, fragment: Fragment, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null) {
    try {
        val transaction = supportFragmentManager.beginTransaction()
        if (updateTransaction != null) {
            updateTransaction(transaction)
        }
        transaction?.add(layoutId, fragment)?.commit()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

fun Fragment.replaceChildFragment(@IdRes layoutId: Int, fragment: Fragment, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null) {
    try {
        val transaction = childFragmentManager.beginTransaction()
        if (updateTransaction != null) {
            updateTransaction(transaction)
        }
        transaction?.replace(layoutId, fragment)?.commit()
    } catch (e: Throwable) {
        e.printStackTrace()
    }

}

fun Fragment.addChildFragment(@IdRes layoutId: Int, fragment: Fragment,
                              tag: String? = null,
                              addToBackStack: Boolean = false,
                              updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null) {
    try {
        val transaction = childFragmentManager.beginTransaction()
        if (updateTransaction != null) {
            updateTransaction(transaction)
        }
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction?.add(layoutId, fragment, tag)?.commit()
    } catch (e: Throwable) {
        e.printStackTrace()
    }

}

fun Fragment.removeFragment(fr: Fragment?, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null): Boolean {
    return if (fr != null) {
        try {
            val transaction = childFragmentManager.beginTransaction()
            if (updateTransaction != null) {
                updateTransaction(transaction)
            }
            transaction?.remove(fr)?.commit()
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }

    } else {
        false
    }
}

fun Fragment.removeFragment(@IdRes layoutId: Int, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null): Boolean {

    val fr = childFragmentManager.findFragmentById(layoutId)
    return if (fr != null) {
        try {
            val transaction = childFragmentManager.beginTransaction()
            if (updateTransaction != null) {
                updateTransaction(transaction)
            }
            transaction?.remove(fr)?.commit()
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }

    } else {
        false
    }
}

fun AppCompatActivity.removeFragment(@IdRes layoutId: Int, updateTransaction: ((transaction: FragmentTransaction?) -> Unit)? = null): Boolean {
    val fr = supportFragmentManager.findFragmentById(layoutId)
    return if (fr != null) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            if (updateTransaction != null) {
                updateTransaction(transaction)
            }
            transaction?.remove(fr)?.commit()
            true
        } catch (e: Throwable) {
            e.printStackTrace()
            false
        }

    } else {
        false
    }
}

fun AppCompatActivity.findFragment(@IdRes layoutId: Int) = supportFragmentManager.findFragmentById(layoutId)

fun Context.isInternetConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    if (activeNetwork != null) {
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
            return true
        } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
            return true
        }
    }
    return false

}
