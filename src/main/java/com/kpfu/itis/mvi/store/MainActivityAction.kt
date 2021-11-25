package com.kpfu.itis.mvi.store

import com.kpfu.itis.mvi.Positions

sealed class MainActivityAction {
    class AddNewCount(val newCount: Int?, val index: Positions): MainActivityAction()
    class CalculationFinished(val firstCount: Int?, val secondCount: Int?, val thirdCount: Int?): MainActivityAction()
    object CalculationStarted: MainActivityAction()
}