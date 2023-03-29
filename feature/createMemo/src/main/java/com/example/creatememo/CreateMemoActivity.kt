package com.example.creatememo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.common.distinctAndMap
import com.example.creatememo.databinding.ActivityCreateMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

internal const val EXTRA_CREATE_MEMO_PARAM = "EXTRA_CREATE_MEMO_PARAM"

@AndroidEntryPoint
class CreateMemoActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context, param: Param): Intent {
            return Intent(context, CreateMemoActivity::class.java).apply {
                putExtra(EXTRA_CREATE_MEMO_PARAM, param)
            }
        }
    }

    private val vm: CreateMemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateMemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bindSubject(vm, binding)
        bindContent(vm, binding.etContent)
        bindSaveButton(vm, binding.btnSave)
        bindToast(vm)
        bindNavigate(vm)
    }

    private fun bindNavigate(vm: CreateMemoViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.distinctAndMap { it.onComplete }.collect {
                    if (it) finish()
                    vm.uiAction(CreateMemoUiAction.AfterNavigate)
                }
            }
        }
    }

    private fun bindToast(vm: CreateMemoViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.distinctAndMap { it.notifications }.collect {
                    val message = it.firstOrNull() ?: return@collect
                    Toast.makeText(this@CreateMemoActivity, message, Toast.LENGTH_LONG).show()
                    vm.uiAction(CreateMemoUiAction.AfterShowMessage(message))
                }
            }
        }
    }

    private fun bindSubject(vm: CreateMemoViewModel, binding: ActivityCreateMemoBinding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.collectLatest { data ->
                    if (binding.etSubject.text.toString() != data.subject) {
                        binding.etSubject.setText(data.subject)
                    }
                }
            }
        }

        binding.etSubject.doAfterTextChanged {
            vm.uiAction(CreateMemoUiAction.InputTitleText(it.toString()))
        }
    }

    private fun bindContent(vm: CreateMemoViewModel, etContent: EditText) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.collectLatest { data ->
                    if (etContent.text.toString() != data.content) {
                        etContent.setText(data.content)
                    }
                }
            }
        }

        etContent.doAfterTextChanged {
            vm.uiAction(CreateMemoUiAction.InputContentText(it.toString()))
        }
    }

    private fun bindSaveButton(vm: CreateMemoViewModel, button: Button) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.distinctAndMap { it.isLoading }.collectLatest {
                    button.isEnabled = !it
                }
            }
        }

        button.setOnClickListener {
            vm.uiAction(CreateMemoUiAction.SaveMemo(vm.stateFlow.value))
        }
    }


    @Parcelize
    data class Param(
        val id: String?
    ) : Parcelable
}