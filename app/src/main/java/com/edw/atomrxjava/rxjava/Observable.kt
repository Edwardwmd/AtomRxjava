package com.edw.atomrxjava.rxjava

import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.ObservableOnSubscribe
import com.edw.atomrxjava.rxjava.common.ObservableSource
import com.edw.atomrxjava.rxjava.common.Observer
import com.edw.atomrxjava.rxjava.create.CreateObservable
import com.edw.atomrxjava.rxjava.flatmap.ObservableFlatMap
import com.edw.atomrxjava.rxjava.just.JustObservable
import com.edw.atomrxjava.rxjava.common.Function
import com.edw.atomrxjava.rxjava.map.ObservableMap
import com.edw.atomrxjava.rxjava.scheduler.observeron.ObservableObserverOn
import com.edw.atomrxjava.rxjava.scheduler.schedulers.Scheduler
import com.edw.atomrxjava.rxjava.scheduler.sunbscribeon.ObservableSubscribeOn

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
 * Description:    被观察者Observable,是Rxjava的上游部分
 ****************************************************************************************************
 */
abstract class Observable<T> : ObservableSource<T> {


      companion object {

            /**
             * create操作符（一级）需要伴生对象修饰，保证当前方法是静态方法
             */
            fun <T> create(oos: ObservableOnSubscribe<T>): Observable<T> {
                  return CreateObservable(oos)
            }

            /**
             * just操作符（一级）需要伴生对象修饰，保证当前方法是静态方法
             * e:是一个可变参数
             */
            fun <T> just(vararg e: T?): Observable<T> {
                  val justObservable = JustObservable<T>()
                  justObservable.justElement(*e)
                  return justObservable
            }

      }

      /**
       * Map操作符（二级）不需要伴生对象修饰，无需变成静态方法
       */
      fun <U> map(function: Function<T, U>): ObservableMap<U, T> {
            return ObservableMap(function, this)
      }

      /**
       * flatMap操作符
       */
      fun <U> flatMap(function: Function<T, ObservableSource<U>>): ObservableFlatMap<U, T> {
            return ObservableFlatMap(this, function)
      }

      /**
       * 线程切换操作符（主要用在上游发射线程）
       */
      fun subscribeOn(scheduler: Scheduler): ObservableSubscribeOn<T> {
            return ObservableSubscribeOn(this, scheduler)
      }

      fun observerOn(scheduler: Scheduler): ObservableObserverOn<T> {
            return ObservableObserverOn(this, scheduler)
      }

      override fun atomSubscribe(observer: Observer<T>) {
            subscribe(observer)
      }

      override fun atomSubscribe(consumer: Consumer<T>) {
            subscribe(consumer)
      }

      /**
       * 订阅下游的方法，使用观察者的模式订阅
       */
      abstract fun subscribe(observer: Observer<T>)

      /**
       * 订阅下游的方法,使用消费者的模式订阅
       */
      abstract fun subscribe(consumer: Consumer<T>)

}