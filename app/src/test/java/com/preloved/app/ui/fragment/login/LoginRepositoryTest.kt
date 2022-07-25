package com.preloved.app.ui.fragment.login

import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.auth.LoginResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class LoginRepositoryTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var userDataSource: UserDataSource
    private lateinit var loginRepository: LoginRepository

    private val datastorePreferences = DatastorePreferences("token","email")

    @Before
    fun setUp() {
        localDataSource = mockk()
        userDataSource = mockk()
        loginRepository = LoginRepository(localDataSource, userDataSource)
    }

    @Test
    fun testSetTokenSession(): Unit = runBlocking {
        val datastorePref = mockk<Unit>()
        every {
            runBlocking {
                loginRepository.setTokenSession(datastorePreferences)
            }
        } returns datastorePref
        loginRepository.setTokenSession(datastorePreferences)
        verify {
            runBlocking {
                loginRepository.setTokenSession(datastorePreferences)
            }
        }
    }

    @Test
    suspend fun postLoginDataUser() {
        val getLoginResponse = mockk<LoginResponse>()
        every {
            runBlocking {
                loginRepository.postLoginDataUser(LoginRequest("arrai@mail.com", "123456"))
            }
        } returns getLoginResponse
        loginRepository.postLoginDataUser(LoginRequest("arrai@mail.com", "123456"))
        verify {
            runBlocking {
                loginRepository.postLoginDataUser(LoginRequest("arrai@mail.com", "123456"))
            }
        }
    }
}