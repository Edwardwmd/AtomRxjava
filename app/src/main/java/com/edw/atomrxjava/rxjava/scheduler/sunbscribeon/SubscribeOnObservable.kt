package com.edw.atomrxjava.rxjava.scheduler.sunbscribeon

import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.Observer

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
class SubscribeOnObservable<T>(private var observer: Observer<T>?, private var consumer: Consumer<T>?) : Observer<T> {

      override fun onSubscribe() {
            observer?.apply {
                  onSubscribe()
            }

      }

      override fun onNext(e: T) {
            observer?.apply {
                  onNext(e)
            }
            consumer?.apply {
                  onNext(e)
            }

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