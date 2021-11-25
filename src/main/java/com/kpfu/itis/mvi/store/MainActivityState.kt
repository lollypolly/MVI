package com.kpfu.itis.mvi.store

import com.kpfu.itis.mvi.Positions

data class MainActivityState(
    val isLoading: Boolean = false,
    var firstCount: Int? = null,
    var secondCount: Int? = null,
    var thirdCount: Int? = null,
    var lastIndex: Positions = Positions.UNSPECIFIED,
    var prevLastIndex: Positions = Positions.UNSPECIFIED
)
