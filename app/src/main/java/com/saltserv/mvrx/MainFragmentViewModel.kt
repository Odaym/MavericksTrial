package com.saltserv.mvrx

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

data class HelloWorldState(
    // Class properties
    val title: String = "Hello world!",
    val count: Int = 0,
    val temperature: Async<Int> = Uninitialized
) : MvRxState {
    // Optional to have a field in the class that does this
    val titleWithCount = "$title $count"
}

class MainFragmentViewModel(state: HelloWorldState) : MvRxViewModel<HelloWorldState>(state) {
    // Functions that deal with the properties of the state above, getters and setters for them
    // Within the free viewModel that is restricted to the state? Should this be called MainFragmentViewModel and MainFragmentState? Probably.
    fun incrementCount() {
        setState { copy(count = count + 1) }
    }

    fun getTemperature() {
        Observable.just(Random.nextInt(9999))
            .delay(1, TimeUnit.SECONDS)
                // execute() is helper to map an [Observable] to an [Async] property on the state object.
                // The Async itself is returned here when execute() is run on the Observable
            .execute { copy(temperature = it) }
    }
}