package com.kpfu.itis.mvi.sideEffects

import com.freeletics.rxredux.SideEffect
import com.freeletics.rxredux.StateAccessor
import com.kpfu.itis.mvi.Calculation
import com.kpfu.itis.mvi.Positions
import com.kpfu.itis.mvi.store.MainActivityAction
import com.kpfu.itis.mvi.store.MainActivityState
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType

typealias MainActivitySideEffect = SideEffect<MainActivityState, MainActivityAction>

class CountSideEffect(
    private val calc: Calculation,
) : MainActivitySideEffect {

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.AddNewCount>()
            .switchMap { action ->
                calcAndUpdate(action.newCount, action.index, state())
                    .map<MainActivityAction> { calculatedValues ->
                        MainActivityAction.CalculationFinished(
                            calculatedValues[0],
                            calculatedValues[1],
                            calculatedValues[2]
                        )
                    }
                    .toObservable()
                    .startWith(MainActivityAction.CalculationStarted)
            }
    }

    private fun calcAndUpdate(
        newCount: Int?,
        index: Positions,
        state: MainActivityState
    ): Single<MutableList<Int?>> {
        with(state) {
            when (index) {
                Positions.FIRST -> firstCount = newCount
                Positions.SECOND -> secondCount = newCount
                Positions.THIRD -> thirdCount = newCount
                else -> {}
            }
        }
        if (index != state.lastIndex) {
            state.prevLastIndex = state.lastIndex
            state.lastIndex = index
        }
        return calc.calculateValue(state)
    }
}