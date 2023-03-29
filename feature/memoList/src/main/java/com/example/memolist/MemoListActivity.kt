package com.example.memolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Navigator
import com.example.common.distinctAndMap
import com.example.memolist.databinding.ActivityMemoListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MemoListActivity : AppCompatActivity() {
    private val vm by viewModels<MemoListViewModel>()

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindMemoList(binding.rvMemo, vm)
        bindNewMemoButton(binding, vm)
        bindErrorText(binding, vm)
        bindNavigate(vm)

    }

    override fun onStart() {
        super.onStart()
        vm.uiAction(MemoListAction.LoadMemoList)
    }

    private fun bindNavigate(viewModel: MemoListViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.distinctAndMap { it.navigateState }.collect {
                    when (it) {
                        is NavigateState.CreateMemo -> {
                            navigator.gotoCreateMemoScreen(this@MemoListActivity, it.id)
                            vm.uiAction(MemoListAction.AfterNavigation)
                        }
                        NavigateState.None -> Unit
                    }
                }
            }
        }
    }

    private fun bindErrorText(binding: ActivityMemoListBinding, viewModel: MemoListViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.map { it.listState }.collectLatest {
                    binding.tvError.isVisible = it is ListState.OnError
                    binding.tvError.text = (it as? ListState.OnError)?.errorMessage
                }
            }
        }

    }

    private fun bindNewMemoButton(binding: ActivityMemoListBinding, viewModel: MemoListViewModel) {
        binding.btnNewMemo.setOnClickListener {
            vm.uiAction(MemoListAction.CreateMemo)
        }
    }

    private fun bindMemoList(recyclerView: RecyclerView, viewModel: MemoListViewModel) {
        val adapter = MemoListAdapter {
            viewModel.uiAction(MemoListAction.ClickMemo(it))
        }
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.distinctAndMap { it.listState as? ListState.OnLoad }.collectLatest {
                    recyclerView.isVisible = it != null
                    adapter.submitList(it?.itemList ?: listOf())
                }
            }
        }
    }
}