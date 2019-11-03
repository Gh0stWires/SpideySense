package com.example.spideysense

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.widget.TextView


import java.security.NoSuchAlgorithmException
import android.provider.SyncStateContract.Helpers.update



/**
 * Created by Eloi Jr on 24/12/2014.
 */
object Tools {

    val URL_MARVEL = "http://www.marvel.com"

    val PUBLIC_KEY = "c246424c2dff19e64ca41fc41dc4518e"
    val PRIVATE_KEY = "2cd718c64692282f68efed526286c560ea5547c0"

    val TIMESTAMP = "ts"
    val API_KEY = "apikey"
    val HASH = "hash"


    fun genKeyUser(): String {
        val ts = java.lang.Long.toString(System.currentTimeMillis() / 1000)
        val hash = Tools.md5(ts + PRIVATE_KEY + PUBLIC_KEY)
        return "?" + TIMESTAMP + "=" + ts + "&" + API_KEY + "=" + PUBLIC_KEY + "&" +
                HASH + "=" + hash
    }

    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = java.security.MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }
}