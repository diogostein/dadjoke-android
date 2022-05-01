package com.codelabs.dadjokeapp.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isSuccess
import com.codelabs.dadjokeapp.networking.DadJokeResponse
import com.codelabs.dadjokeapp.networking.DadJokeService
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetRandomDadJokeTest {

    @RelaxedMockK
    private lateinit var dadJokeService: DadJokeService

    private lateinit var subject: GetRandomDadJoke

    private val expectedDadJokeModel = DadJokeModel(joke = "Haha, good joke!")
    private val expectedError = Exception("Whoops!")

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = GetRandomDadJoke(dadJokeService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun givenASuccessfulNetworkingCall() {
        every {
            dadJokeService.singleRandomDadJoke()
        } returns Single.just(DadJokeResponse(id = "", joke = expectedDadJokeModel.joke, status = 200))
    }

    private fun givenAnUnsuccessfulNetworkingCall() {
        every {
            dadJokeService.singleRandomDadJoke()
        } returns Single.error(expectedError)
    }

    @Test
    fun `given an unsuccessful call, when execute is called, an error should be emitted`() {
        givenAnUnsuccessfulNetworkingCall()

        subject.execute().test()
            .assertNoValues()
            .assertNotComplete()
            .assertError(Throwable::class.java)
    }

    @Test
    fun `given a successful networking call, when execute is called, a dad joke should be emitted`() {
        givenASuccessfulNetworkingCall()

        subject.execute().test()
            .assertNoErrors()
            .assertComplete()
            .assertValueCount(1)
    }

    @Test
    fun `given a successful networking call, when execute is called, the correct dad joke is provided`() {
        assertThat {
            givenASuccessfulNetworkingCall()
            subject.execute().blockingGet()
        }.isSuccess().isEqualTo(expectedDadJokeModel)
    }

    @Test
    fun `given a unsuccessful networking call, when execute is called, the correct error is emitted`() {
        givenAnUnsuccessfulNetworkingCall()

        subject.execute().test()
            .assertNoValues()
            .assertNotComplete()
            .assertError(expectedError)
    }

}