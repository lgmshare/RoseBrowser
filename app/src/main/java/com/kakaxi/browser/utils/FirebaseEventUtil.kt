package com.kakaxi.browser.utils

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseEventUtil {

    companion object {

        fun event(tag: String, params: Bundle? = null) {
            if (params == null) {
                Utils.log("firebase埋点:$tag")
            } else {
                Utils.log("firebase埋点:$tag ${params.toString()}")
            }
            Firebase.analytics.logEvent(tag, params)
        }

        fun newLinkEvent(site: String) {
            event("chest_guid", Bundle().apply {
                putString("bro", site)
            })
        }

        fun searchEvent(text: String) {
            event("chest_search", Bundle().apply {
                putString("bro", text)
            })
        }

        fun newTabEvent(pos: String) {
            event("chest_newTab", Bundle().apply {
                putString("bro", pos)
            })
        }

        fun webLoadEvent(time: Long) {
            if (time < 1) {
                event("chest_reload", Bundle().apply {
                    putLong("bro", 1)
                })
            } else {
                event("chest_reload", Bundle().apply {
                    putLong("bro", time)
                })
            }
        }

        fun cleanEvent(time: Long) {
            if (time < 1) {
                event("chest_clean_toast", Bundle().apply {
                    putLong("bro", 1)
                })
            } else {
                event("chest_clean_toast", Bundle().apply {
                    putLong("bro", time)
                })
            }
        }
    }
}