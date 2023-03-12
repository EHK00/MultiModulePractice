package com.example.memolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.common.Navigator
import com.example.memolist.databinding.ActivityMemoListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MemoListActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm by viewModels<MemoListViewModel>()
        val binding = ActivityMemoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindMemoList(binding.rvMemo, vm)
        bindNewMemoButton(binding, vm)
    }

    private fun bindNewMemoButton(binding: ActivityMemoListBinding, viewModel: MemoListViewModel) {
        binding.btnNewMemo.setOnClickListener {
            navigator.gotoCreateMemoScreen(this, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun bindMemoList(recyclerView: RecyclerView, viewModel: MemoListViewModel) {
        val adapter = MemoListAdapter()
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.mapLatest { it.listState }.collectLatest {
                    when (it) {
                        ListState.None -> Unit
                        is ListState.OnError -> Unit
                        is ListState.OnLoad ->
                            adapter.submitList(it.itemList)
                    }
                }
            }
        }
    }
}