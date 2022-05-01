package com.codelabs.dadjokeapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.codelabs.dadjokeapp.R
import com.codelabs.dadjokeapp.common.StringLoader
import com.codelabs.dadjokeapp.domain.DadJokeModel
import com.codelabs.dadjokeapp.domain.GetRandomDadJoke
import com.codelabs.dadjokeapp.testcommon.RxSchedulersFake
import com.codelabs.dadjokeapp.testcommon.StringLoaderFake
import com.codelabs.dadjokeapp.testcommon.StringResValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DadJokeViewModelTest {

    @get:Rule
    val viewModelRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getRandomDadJoke: GetRandomDadJoke

    private val stringLoader = StringLoaderFake()

    private lateinit var subject: DadJokeViewModel

    private val dadJokeInitialMessage = StringResValue(R.string.dad_joke_initial_message)
    private val expectedDadJokeText = "This joke is hilarious!!"
    private val expectedDadJokeModel = DadJokeModel(joke = expectedDadJokeText)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        stringLoader.expect(dadJokeInitialMessage)

        subject = DadJokeViewModel(
            getRandomDadJoke = getRandomDadJoke,
            rxSchedulers = RxSchedulersFake(),
            stringLoader = stringLoader
        )
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `given a new subject, when observing on state, it should provide an initial state`() {
        val actual = subject.state.value!!

        assertThat(actual.jokeText)
            .isEqualTo(dadJokeInitialMessage.value)
    }

    @Test
    fun `given a successful new joke generation, when observing on state, it should provide a new dad joke`() {
        every {
            getRandomDadJoke.execute()
        } returns Single.just(expectedDadJokeModel)

        subject.generateNewJoke()

        val actual = subject.state.value!!

        assertThat(actual.jokeText).isEqualTo(expectedDadJokeText)
    }

    @Test
    fun `given an unsuccessful new joke generation, when observing on state, it should provide an error message`() {
        every { getRandomDadJoke.execute() } returns Single.error(Exception())

        val errorStringResValue = StringResValue(R.string.error_dad_joke_api).also {
            stringLoader.expect(it)
        }

        subject.generateNewJoke()

        val actual = subject.state.value!!

        assertThat(actual.jokeText).isEqualTo(errorStringResValue.value)
    }

}