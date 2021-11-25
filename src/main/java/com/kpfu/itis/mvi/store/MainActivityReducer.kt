package com.kpfu.itis.mvi.store

import com.kpfu.itis.mvi.Positions

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when (action) {
            is MainActivityAction.AddNewCount -> {
                state.apply {
                    when (action.index) {
                        Positions.FIRST -> firstCount = action.newCount
                        Positions.SECOND -> secondCount = action.newCount
                        Positions.THIRD -> thirdCount = action.newCount
                    }
                    if (lastIndex != action.index) {
                        prevLastIndex = lastIndex
                        lastIndex = action.index
                    }
                    state.copy(
                        firstCount = firstCount,
                        secondCount = secondCount,
                        thirdCount = thirdCount,
                        lastIndex = lastIndex,
                        prevLastIndex = prevLastIndex
                    )
                }
            }
            is MainActivityAction.CalculationStarted -> state.copy(isLoading = true)
            is MainActivityAction.CalculationFinished -> state.copy(
                isLoading = false,
                firstCount = action.firstCount,
                secondCount = action.secondCount,
                thirdCount = action.thirdCount
            )
        }
    }
}