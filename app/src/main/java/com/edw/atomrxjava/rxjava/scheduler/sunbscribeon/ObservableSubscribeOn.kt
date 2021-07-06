package com.edw.atomrxjava.rxjava.scheduler.sunbscribeon

import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.ObservableSource
import com.edw.atomrxjava.rxjava.common.Observer
import com.edw.atomrxjava.rxjava.common.AbstractObservableWithUpStream
import com.edw.atomrxjava.rxjava.scheduler.schedulers.Scheduler

/*****************************************************************************************************
 * Project Name:    AtomRxjava
 *
 * Date:            2021-07-05
 *
 * Author:         EdwardWMD
 *
 * Github:          https://github.com/Edwardwmd
 *
 * Blog:            https://edwardwmd.github.io/
 *
 * Description:    ToDo
 ****************************************************************************************************
 */
class ObservableSubscribeOn<T>(source: ObservableSource<T>, private var scheduler: Scheduler) :
      AbstractObservableWithUpStream<T, T>(source) {


     private companion object class SchedulerTask<T>(
            private var upScheduler: SubscribeOnObservable<T>?,
            private var source: ObservableSource<T>?
      ) : Runnable {

            override fun run() {
                  source!!.atomSubscribe(upScheduler!!)
            }

      }


      override fun subscribe(observer: Observer<T>) {
            val worker = scheduler.createWorker()
            worker.scheduler(SchedulerTask<T>(SubscribeOnObservable(observer,null),source))

      }

      override fun subscribe(consumer: Consumer<T>) {
            val worker = scheduler.createWorker()
            worker.scheduler(SchedulerTask<T>(SubscribeOnObservable(null,consumer),source))
      }
}