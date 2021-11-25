package com.kpfu.itis.mvi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.kpfu.itis.mvi.sideEffects.CountSideEffect
import com.kpfu.itis.mvi.store.MainActivityAction
import com.kpfu.itis.mvi.store.MainActivityState
import com.kpfu.itis.mvi.store.MainActivityStore
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val store = MainActivityStore(listOf(CountSideEffect(Calculation())))

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        store.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::render)
        a.doAfterTextChanged { input ->
            sendNewCount(input.toString(), Positions.FIRST)
        }
        b.doAfterTextChanged { input ->
            sendNewCount(input.toString(), Positions.SECOND)
        }
        c.doAfterTextChanged { input ->
            sendNewCount(input.toString(), Positions.THIRD)
        }
    }

    private fun render(state: MainActivityState) {
        with(state) {
            firstCount?.let {
                if (a.text.toString() != it.toString()) {
                    a.setText(it.toString())
                }
            }
            secondCount?.let {
                if (b.text.toString() != it.toString()) {
                    b.setText(it.toString())
                }
            }
            thirdCount?.let {
                if (c.text.toString() != it.toString()) {
                    c.setText(it.toString())
                }
            }
            progressBar.isVisible = isLoading
        }
    }

    private fun sendNewCount(newCount: String, field: Positions) {
        if (newCount.isNotEmpty()) {
            store.actionRelay.onNext(
                MainActivityAction.AddNewCount(newCount.toInt(), field)
            )
        }
    }
}