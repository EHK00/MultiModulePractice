package com.example.creatememo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.creatememo.databinding.ActivityCreateMemoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateMemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateMemoBinding.inflate(layoutInflater)
        val vm: CreateMemoViewModel by viewModels()

        setContentView(binding.root)

        bindSubject(vm, binding)
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

    private fun bindContent(vm: CreateMemoViewModel, binding: ActivityCreateMemoBinding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.stateFlow.collectLatest { data ->
                    if (binding.etContent.text.toString() != data.content) {
                        binding.etContent.setText(data.content)
                    }
                }
            }
        }

        binding.etContent.doAfterTextChanged {
            vm.uiAction(CreateMemoUiAction.InputContentText(it.toString()))
        }
    }
}