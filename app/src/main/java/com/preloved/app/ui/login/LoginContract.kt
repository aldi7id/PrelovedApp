package com.preloved.app.ui.login

interface LoginContract {
    interface View {
        fun postData()
    }

    interface ViewModel {
        fun postLoginUser(email: String, password: String)
    }

    interface Repository {
        fun postLoginDataUser(email: String, password: String)
    }
}