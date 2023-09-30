package com.slaytertv.firegym.util

import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent

class ExtensionsAnal {
}
fun Fragment.analregister(emai:String, name:String){
    FirebaseAnalytics.getInstance(requireContext())
        .logEvent(FirebaseAnalytics.Event.SIGN_UP) {
            param(FirebaseAnalytics.Param.METHOD,"UserPass")
            param("FullName", name)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "email")
            param(FirebaseAnalytics.Param.CONTENT, emai)
        }
}
//
fun Fragment.anallogin(emai:String){
    FirebaseAnalytics.getInstance(requireContext())
        .logEvent(FirebaseAnalytics.Event.LOGIN) {
            param(FirebaseAnalytics.Param.METHOD,"UserPass")
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "email")
            param(FirebaseAnalytics.Param.CONTENT, emai)
        }
}