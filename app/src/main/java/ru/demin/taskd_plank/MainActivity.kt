package ru.demin.taskd_plank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.demin.taskd_plank.recycler.AttemptsAdapter

class MainActivity : AppCompatActivity(), AttemptsAdapter.EventListener {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(application) }
    private val attemptsAdapter by lazy { AttemptsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupList()
        viewModel.viewState.observe(this, ::render)

    }

    override fun onAddClick() = viewModel.onAddClick()

    private fun setupList() {
        attempats_list.run {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = attemptsAdapter
        }
    }

    private fun render(viewState: ViewState) {
        attemptsAdapter.submitList(viewState.items)

        timer.isVisible = (viewState.attemptState != null)
        viewState.attemptState?.let { state->
            when(state) {
               is  ViewState.AttemptState.InProgress -> timer.text = state.currentTimeInSeconds.convertToTime()
               ViewState.AttemptState.Prepare -> timer.text = resources.getString(R.string.prepare_to_plank)
            }
        }
    }
}