package com.edw.atomrxjava.rxjava.scheduler.observeron


import android.util.Log
import com.edw.atomrxjava.rxjava.common.AbstractObservableWithUpStream
import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.ObservableSource
import com.edw.atomrxjava.rxjava.common.Observer
import com.edw.atomrxjava.rxjava.scheduler.schedulers.Scheduler
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.SynchronousQueue

/*****************************************************************************************************
 * Project Name:    AtomRxjava
 *
 * Date:            2021-07-06
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
class ObservableObserverOn<T>(source: ObservableSource<T>, private val scheduler: Scheduler) :
      AbstractObservableWithUpStream<T, T>(source) {

      override fun subscribe(observer: Observer<T>) {
            source!!.atomSubscribe(ObserveOnObserver(observer, null, scheduler.createWorker()))
      }

      override fun subscribe(consumer: Consumer<T>) {
            source!!.atomSubscribe(ObserveOnObserver(null, consumer, scheduler.createWorker()))
      }

      private companion object
      class ObserveOnObserver<T>(
            private val downStreamObserver: Observer<T>?,
            private val downStreamConsumer: Consumer<T>?,
            private val worker: Scheduler.Worker
      ) : Observer<T>, Runnable {

            //双向队列(将事件从上游线程添加，在切换另一个线程时取出)
            @Volatile
            private var queue: Deque<T>? = null

            @Volatile
            private var done: Boolean = false

            @Volatile
            private var over: Boolean = false

            @Volatile
            private var e: Exception? = null

            init {
                  queue = LinkedBlockingDeque<T>()
            }

            override fun onSubscribe() {

            }

            override fun onNext(e: T) {
                  queue!!.offer(e)
                  schedule()
            }

            override fun onError(e: Exception) {

            }

            override fun onComplete() {

            }

            override fun run() {
                  drainNormal()
            }

            /**
             *
             */
            private fun drainNormal() {
                  val dq: Deque<T> = queue!!
                  val downStreamObserver: Observer<T>? = downStreamObserver
                  val downStreamConsumer: Consumer<T>? = downStreamConsumer
                  while (true) {
                        val d = done
                        //取出事件
                        val element = dq.poll()
                        val empty = element == null
                        downStreamObserver?.apply {
                              onSubscribe()
                        }
                        if (checkTerminated(d, empty, downStreamObserver, downStreamConsumer)) return

                        if (empty) {
                              return
                        }
                        downStreamObserver?.apply {
                              onNext(element!!)
                        }
                        downStreamConsumer?.apply {
                              onNext(element!!)
                        }
                  }
            }

            private fun checkTerminated(
                  d: Boolean,
                  empty: Boolean,
                  downStreamObserver: Observer<T>?,
                  downStreamConsumer: Consumer<T>?
            ): Boolean {
                  if (over) {
                        queue!!.clear()
                        return true
                  }

                  if (d) {
                        val error: Exception? = e
                        if (null != error) {
                              over = true
                              downStreamObserver?.apply {
                                    onError(error)
                              }
                              downStreamConsumer?.apply {
                                    onError(error)
                              }
                              return true
                        }
                  } else if (empty) {
                        over = true
                        downStreamObserver?.apply {
                              onComplete()
                        }
                        downStreamConsumer?.apply {
                              onComplete()
                        }
                        return true
                  }
                  return false
            }

            private fun schedule() {
                  worker.scheduler(this)
            }
      }
}