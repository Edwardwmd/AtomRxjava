package com.edw.atomrxjava.rxjava.create

import com.edw.atomrxjava.rxjava.Observable
import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.Emitter
import com.edw.atomrxjava.rxjava.common.ObservableOnSubscribe
import com.edw.atomrxjava.rxjava.common.Observer

/*****************************************************************************************************
 * Project Name:    AtomRxjava
 *
 * Date:            2021-07-02
 *
 * Author:         EdwardWMD
 *
 * Github:          https://github.com/Edwardwmd
 *
 * Blog:            https://edwardwmd.github.io/
 *
 * Description:    Create操作符内部实现
 ****************************************************************************************************
 */
class CreateObservable<T>(oos: ObservableOnSubscribe<T>) : Observable<T>() {

      private var oos: ObservableOnSubscribe<T>? = null

      init {
            this.oos = oos
      }

      private companion object
      class CreateEmitter<T>(private var observer: Observer<T>?, private var consumer: Consumer<T>?) : Emitter<T> {



            //当调用onError或onComplete方法时就终止onNext的发射
            private var done = false

            override fun onNext(e: T) {
                  if (done) return
                  observer?.apply {
                        onNext(e)
                  }
                  consumer?.apply {
                        onNext(e)
                  }
                  done = false
            }

            override fun onError(e: Exception) {
                  if (done) return
                  observer?.apply {
                        onError(e)
                  }
                  consumer?.apply {
                        onError(e)
                  }
                  done = true
            }

            override fun onComplete() {
                  if (done) return
                  observer?.apply {
                        onComplete()
                  }
                  consumer?.apply {
                        onComplete()
                  }
                  done = true
            }

      }

      override fun subscribe(observer: Observer<T>) {
            //订阅
            observer.onSubscribe()
            //发射器与Observe关联
            val createEmitter = CreateEmitter(observer, null)
            oos!!.subscribe(createEmitter)
      }

      override fun subscribe(consumer: Consumer<T>) {
            val createEmitter = CreateEmitter(null, consumer)
            oos!!.subscribe(createEmitter)
      }

}