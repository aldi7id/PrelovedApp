import android.app.ActionBar
import android.app.AlertDialog
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.databinding.FragmentEditPasswordBinding
import com.preloved.app.ui.fragment.homepage.account.password.EditPasswordContract
import com.preloved.app.ui.fragment.homepage.account.password.EditPasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPasswordFragment : BaseFragment<FragmentEditPasswordBinding, EditPasswordViewModel>(
    FragmentEditPasswordBinding::inflate
) , EditPasswordContract.View {
    private var token = ""
    override val viewModel: EditPasswordViewModel by viewModel()
    override fun initView() {
        viewModel.userSession()
        setOnClickListeners()
    }

    override fun checkFormValidation(): Boolean {
       getViewBinding().apply {
           var isFormValid = true
           val oldPassword = etOldPassword.text.toString()
           val newPassword = etNewPassword.text.toString()
           val rePassword = etRepassword.text.toString()

           when {
               oldPassword.isEmpty() -> {
                   isFormValid = false
                   tfOldPassword.isErrorEnabled = true
                   tfOldPassword.error = "Masukkan Password Lama"
               }
               newPassword.isEmpty() -> {
                   isFormValid = false
                   tfNewPassword.isErrorEnabled = true
                   tfNewPassword.error = "Masukkan Password Baru"
               }
               rePassword.isEmpty() -> {
                   isFormValid = false
                   tfRepassword.isErrorEnabled = true
                   tfRepassword.error = "Masukkan Repassword Baru"
               }
               newPassword != rePassword -> {
                   isFormValid = false
                   tfOldPassword.isErrorEnabled = true
                   tfRepassword.error = "Password Harus Sama"
               }
                else -> {
                   tfOldPassword.isErrorEnabled = false
               tfNewPassword.isErrorEnabled = false
               tfRepassword.isErrorEnabled = false
               }
           }

           return isFormValid
       }

    }

    override fun setOnClickListeners() {
       getViewBinding().apply {
           btnEdit.setOnClickListener {
               if (checkFormValidation()) {
                   val oldPassword = etOldPassword.text.toString()
                   val newPassword = etNewPassword.text.toString()
                   val rePassword = etRepassword.text.toString()
                   viewModel.updatePassword(
                       token,
                       oldPassword = oldPassword,
                       newPassword = newPassword,
                       rePassword = rePassword
                   )
               }
           }
       }
    }

    override fun observeData() {
        super.observeData()
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                        //ToLogin Fragment
                        dialogP.dismiss()
                        findNavController().navigate(R.id.action_accountFragment_to_loginFragment3)

                    }
                    .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                        findNavController().navigate(R.id.homeFragment)

                    }
                    .setCancelable(false)
                    .show()
            } else {
                token = it.access_token
            }
        }
        viewModel.updateResultPassword().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    findNavController().navigate(R.id.action_editPasswordFragment_to_accountFragment)
                    showToastSuccess()
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    if(it.message == "HTTP 400 Bad Request"){
                        AlertDialog.Builder(context)
                            .setTitle(getString(R.string.warning))
                            .setMessage(getString(R.string.wrong_password))
                            .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
                                dialogP.dismiss()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            }
        }
    }
    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, getString(R.string.success_edit_password), Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
}