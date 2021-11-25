package com.kpfu.itis.mvi.store

import com.freeletics.rxredux.reduxStore
import com.kpfu.itis.mvi.sideEffects.MainActivitySideEffect
import io.reactivex.subjects.PublishSubject

class MainActivityStore(
    sideEffects: List<MainActivitySideEffect>,
) {

    val actionRelay = PublishSubject.create<MainActivityAction>()

    val state = actionRelay.reduxStore(
        MainActivityState(),
        sideEffects,
        MainActivityReducer()::reduce
    )
}
