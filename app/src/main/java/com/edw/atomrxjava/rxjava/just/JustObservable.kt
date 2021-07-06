package com.edw.atomrxjava.rxjava.just

import com.edw.atomrxjava.rxjava.Observable
import com.edw.atomrxjava.rxjava.common.Consumer
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
 * Description:    Just操作符内部实现，使用装饰者模式实现
 ****************************************************************************************************
 */
class JustObservable<T> : Observable<T>() {

      private var e: Array<out T?>? = null

      /**
       * e是可变参数，传入的个数是数组的size
       */
      fun justElement(vararg e: T?) {
            this.e = e
      }

      /**
       * 下游订阅观察者，接受上游发射的事件
       */
      override fun subscribe(observer: Observer<T>) {
            observer.onSubscribe()
            if (e != null) {
                  for (i in e!!.indices) {
                        if (null != e!![i]) {
                              observer.onNext(e!![i]!!)
                        } else {
                              observer.onError(RuntimeException("不能发送空对象~~"))
                              return
                        }
                  }
                  observer.onComplete()
            } else {
                  observer.onError(RuntimeException("不能发送数据~~"))
            }

      }

      /**
       * 下游订阅消费者，获取上游发射的事件
       */
      override fun subscribe(consumer: Consumer<T>) {
            if (e != null) {
                  for (i in e!!.indices) {
                        if (null != e!![i]) {
                              consumer.onNext(e!![i]!!)
                        } else {
                              consumer.onError(RuntimeException("不能发送空对象~~"))
                              return
                        }
                  }
                  consumer.onComplete()
            } else {
                  consumer.onError(RuntimeException("不能发送空对象~~"))
            }

      }


}