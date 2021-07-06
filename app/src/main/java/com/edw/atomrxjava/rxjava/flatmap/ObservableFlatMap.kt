package com.edw.atomrxjava.rxjava.flatmap

import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.ObservableSource
import com.edw.atomrxjava.rxjava.common.Observer
import com.edw.atomrxjava.rxjava.common.AbstractObservableWithUpStream
import com.edw.atomrxjava.rxjava.common.Function

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
 * Description:    flatMap操作符的具体实现，使用装饰者模式实现
 ****************************************************************************************************
 */
class ObservableFlatMap<U, T>(source: ObservableSource<T>, private var function: Function<T, ObservableSource<U>>) :
      AbstractObservableWithUpStream<U, T>(source) {

      companion object
      class FlatMapObserve<T, U>(
            private var consumer: Consumer<U>?,
            private var observer: Observer<U>?,
            private var function: Function<T, ObservableSource<U>>
      ) : Observer<T> {

            override fun onSubscribe() {
                  observer?.apply {
                        onSubscribe()
                  }
            }

            override fun onNext(e: T) {
                  val result: ObservableSource<U>? = function.apply(e)
                  result?.atomSubscribe(object : Observer<U> {
                        override fun onSubscribe() {

                        }

                        override fun onNext(e: U) {
                              observer?.apply {
                                    onNext(e)
                              }
                              consumer?.apply {
                                    onNext(e)
                              }
                        }

                        override fun onError(e: Exception) {

                        }

                        override fun onComplete() {

                        }
                  })
            }

            override fun onError(e: Exception) {
                  observer?.apply {
                        onError(e)
                  }
                  consumer?.apply {
                        onError(e)
                  }
            }

            override fun onComplete() {
                  observer?.apply {
                        onComplete()

                  }
                  consumer?.apply {
                        onComplete()
                  }
            }

      }



      override fun subscribe(observer: Observer<U>) {
            source!!.atomSubscribe(FlatMapObserve(null, observer, function))
      }

      override fun subscribe(consumer: Consumer<U>) {
            source!!.atomSubscribe(FlatMapObserve(consumer, null, function))
      }
}