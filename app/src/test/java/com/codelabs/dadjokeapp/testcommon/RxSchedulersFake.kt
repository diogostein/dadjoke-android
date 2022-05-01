package com.codelabs.dadjokeapp.testcommon

import com.codelabs.dadjokeapp.common.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class RxSchedulersFake(
    override val io: Scheduler = Schedulers.trampoline(),
    override val ui: Scheduler = Schedulers.trampoline(),
) : RxSchedulers