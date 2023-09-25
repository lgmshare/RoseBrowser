package com.kakaxi.browser.utils

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseEventUtil {

    companion object {

        fun event(tag: String, params: Bundle? = null) {
            if (params == null) {
                Utils.log("firebase打点:$tag")
            } else {
                Utils.log("firebase打点:$tag ${params.toString()}")
            }
            Firebase.analytics.logEvent(tag, params)
        }

        fun newLinkEvent(site: String) {
            event("rose_guid", Bundle().apply {
                putString("bro", site)
            })
        }

        fun searchEvent(text: String) {
            event("rose_search", Bundle().apply {
                putString("bro", text)
            })
        }

        fun webLoadEvent(time: Long) {
            if (time < 1) {
                event("rose_reload", Bundle().apply {
                    putLong("bro", 1)
                })
            } else {
                event("rose_reload", Bundle().apply {
                    putLong("bro", time)
                })
            }
        }
    }
}