package com.codelabs.dadjokeapp.domain

import com.codelabs.dadjokeapp.networking.DadJokeService
import io.reactivex.Single
import javax.inject.Inject

class GetRandomDadJoke @Inject constructor(private val dadJokeService: DadJokeService) {

    fun execute(): Single<DadJokeModel> {
        return dadJokeService.singleRandomDadJoke()
            .map { DadJokeModel(joke = it.joke) }
    }

}