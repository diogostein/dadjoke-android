package com.codelabs.dadjokeapp.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSchedulers {
    val io: Scheduler
    val ui: Scheduler

    companion object {
        operator fun invoke(): RxSchedulers {
            return object : RxSchedulers {
                override val io: Scheduler
                    get() = Schedulers.io()
                override val ui: Scheduler
                    get() = AndroidSchedulers.mainThread()
            }
        }
    }
}