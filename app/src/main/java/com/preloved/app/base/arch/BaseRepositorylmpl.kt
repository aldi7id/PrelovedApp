package com.preloved.app.base.arch

import android.util.Log

open class BaseRepositorylmpl: BaseContract.BaseRepository {
    override fun logResponse(msg: String?) {
        Log.d(BaseRepositorylmpl::class.java.simpleName, msg.orEmpty())
    }
}