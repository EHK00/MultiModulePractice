package com.example.memolist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memolist.databinding.ActivityMemoListBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MemoListActivity : AppCompatActivity() {
    private val vm by viewModels<MemoListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindMemoList(binding.rvMemo, vm)
    }

    private fun bindMemoList(recyclerView: RecyclerView, viewModel: MemoListViewModel) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collectLatest {


                }
            }
        }
    }
}