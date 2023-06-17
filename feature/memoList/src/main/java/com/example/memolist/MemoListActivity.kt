package com.example.memolist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.view.isVisible
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
//    @Inject
//    lateinit var navigator: NavController
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MaterialTheme {
//                val vm: MemoListViewModel = hiltViewModel()
//                val state by vm.stateFlow.collectAsState()
//                val navController = rememberNavController()
//
//                val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)
//                DisposableEffect(lifecycleOwner) {
//                    val lifecycle = lifecycleOwner.value.lifecycle
//                    val observer = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
//                        when (event) {
//                            Lifecycle.Event.ON_START ->
//                                vm.uiAction(MemoListAction.LoadMemoList)
//
//                            else -> Unit
//                        }
//                    }
//                    lifecycle.addObserver(observer)
//                    onDispose {
//                        lifecycle.removeObserver(observer)
//                    }
//                }
//
//                MemoListScreen(
//                    modifier = Modifier,
//                    navController = navController,
//                    onItemClick = {
//                        navController.navigate()
//                        vm.uiAction(MemoListAction.ClickMemo(it))
//                    }
//                )
//            }
//        }


//        val binding = ActivityMemoListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        bindMemoList(binding.rvMemo, vm)
//        bindNewMemoButton(binding, vm)
//        bindErrorText(binding, vm)
//        bindNavigate(vm)
//
//    }


//    private fun bindNavigate(viewModel: MemoListViewModel) {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.stateFlow.distinctAndMap { it.navigateState }.collect {
//                    when (it) {
//                        is NavigateState.CreateMemo -> {
//                            navigator.gotoCreateMemoScreen(this@MemoListActivity, it.id)
//                            vm.uiAction(MemoListAction.AfterNavigation)
//                        }
//
//                        NavigateState.None -> Unit
//                    }
//                }
//            }
//        }
//    }

//    private fun bindErrorText(binding: ActivityMemoListBinding, viewModel: MemoListViewModel) {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.stateFlow.map { it.listState }.collectLatest {
//                    binding.tvError.isVisible = it is ListState.OnError
//                    binding.tvError.text = (it as? ListState.OnError)?.errorMessage
//                }
//            }
//        }
//    }

//    private fun bindNewMemoButton(binding: ActivityMemoListBinding, viewModel: MemoListViewModel) {
//        binding.btnNewMemo.setOnClickListener {
//            vm.uiAction(MemoListAction.CreateMemo)
//        }
//    }

//    private fun bindMemoList(recyclerView: RecyclerView, viewModel: MemoListViewModel) {
//        val adapter = MemoListAdapter {
//            viewModel.uiAction(MemoListAction.ClickMemo(it))
//        }
//        recyclerView.adapter = adapter
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.stateFlow.distinctAndMap { it.listState as? ListState.OnLoad }
//                    .collectLatest {
//                        recyclerView.isVisible = it != null
//                        adapter.submitList(it?.itemList ?: listOf())
//                    }
//            }
//        }
//    }
}