package com.preloved.app.ui.login

import com.preloved.app.base.arch.BaseViewModellmpl

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModellmpl(), LoginContract.ViewModel {
    override fun postLoginUser(email: String, password: String) {

    }
}