package com.saltserv.mvrx

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseMvRxFragment() {

    private val viewModel: MainFragmentViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Alternatively to waiting for invalidate() to be called, you can subscribe manually to state changes
        // You must do this inside onCreate() of the Fragment because it's supposed to survive configuration changes

//        viewModel.subscribe { state ->
//            textView.text = "The state is $state"
//        }

        // OR you can

//        viewModel.selectSubscribe(HelloWorldState::count, deliveryMode = UniqueOnly("count")) {
//            textView.text = "The Count is $it"
//        }

        // OR you can

        viewModel.asyncSubscribe(
            HelloWorldState::temperature,
            deliveryMode = UniqueOnly("temperature"),
            onSuccess = {
                textView.text = "The Temperature is $it"
            },
            onFail = {
                textView.text = "Failed to fetch the Temperature!"
            })

        // All the 3 api calls below are lifecycle-aware, they subscribe to the fragment's lifecycle owner so that
        //it will only emit items between onStart and onStop, you don't have to worry about managing this yourself.

        // Also all those subscription methods deliver the above state when the fragment is started
        // Maybe not desirable for ALL changes like when you're tracking stuff and analytics
        // You can restrict this re-delivery to only when the state truly uniquely changes with a new value
        // by adding a deliveryMode property and giving it UniqueOnly
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.setOnClickListener {
            viewModel.incrementCount()
            viewModel.getTemperature()
        }
    }

    override fun invalidate() = withState(viewModel, { state ->
        //        textView.text = when (state.temperature) {
//            is uninitialized -> "click to load the temperature"
//            is loading -> "loading..."
//            is fail -> "failed to fetch the temperature!"
//            is success -> "the temperature is ${state.temperature()}"
//        }
    })
}