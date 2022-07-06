package com.example.notestesttask.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.notestesttask.R
import com.example.notestesttask.util.Constants

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun getContentView(): Int
    protected abstract fun setupDataBinding(dataBinding: ViewDataBinding?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View? = null
        val contentViewId = getContentView()
        if (contentViewId > 0) {
            val dataBinding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, contentViewId, container, false)
            view = dataBinding.root
            setupDataBinding(dataBinding)
        }
        return view
    }

    fun getErrorMessage(error: String) =
        when (error) {
            Constants.ERROR_INVALID_EMAIL -> getString(R.string.error_invalid_email)
            Constants.ERROR_INVALID_USER -> getString(R.string.error_invalid_email_or_password)
            Constants.ERROR_INVALID_CONFIRM_PASSWORD -> getString(R.string.error_invalid_confirm_password)
            Constants.ERROR_SHORT_PASSWORD -> getString(R.string.error_short_password)
            Constants.ERROR_EMAIL_IS_ALREADY_IN_USE -> getString(R.string.error_email_is_already_in_use)
            Constants.ERROR_WRONG_PASSWORD -> getString(R.string.error_invalid_email_or_password)
            else -> getString(R.string.unknown_error, error)
        }
}