package com.codelabs.dadjokeapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codelabs.dadjokeapp.R
import com.codelabs.dadjokeapp.common.RxSchedulers
import com.codelabs.dadjokeapp.common.StringLoader
import com.codelabs.dadjokeapp.domain.GetRandomDadJoke
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class DadJokeViewModel @Inject constructor(
    private val getRandomDadJoke: GetRandomDadJoke,
    private val rxSchedulers: RxSchedulers,
    private val stringLoader: StringLoader
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = MutableLiveData<DadJokeState>()
    val state: LiveData<DadJokeState> = _state

    init {
        _state.postValue(
            DadJokeState(stringLoader.get(R.string.dad_joke_initial_message))
        )
    }

    fun generateNewJoke() {
        compositeDisposable.add(
            getRandomDadJoke.execute()
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.ui)
                .subscribe { response, error ->
                    val jokeText = if (error != null) {
                        stringLoader.get(R.string.error_dad_joke_api)
                    } else {
                        response.joke
                    }
                    _state.postValue(
                        DadJokeState(jokeText = jokeText)
                    )
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}