package com.edw.atomrxjava.rxjava.map

import com.edw.atomrxjava.rxjava.common.AbstractObservableWithUpStream
import com.edw.atomrxjava.rxjava.common.Consumer
import com.edw.atomrxjava.rxjava.common.Function
import com.edw.atomrxjava.rxjava.common.ObservableSource
import com.edw.atomrxjava.rxjava.common.Observer

/*****************************************************************************************************
 * Project Name:    AtomRxjava
 *
 * Date:            2021-07-03
 *
 * Author:         EdwardWMD
 *
 * Github:          https://github.com/Edwardwmd
 *
 * Blog:            https://edwardwmd.github.io/
 *
 * Description:    Map操作符的具体实现,使用装饰者模式实现
 ****************************************************************************************************
 */
class ObservableMap<U, T>(function: Function<T, U>, source: ObservableSource<T>) :
      AbstractObservableWithUpStream<U, T>(source) {

      private var function: Function<T, U>? = function

      override fun subscribe(observer: Observer<U>) {
            source!!.atomSubscribe(MapObserve(observer, null, function!!))
      }

      override fun subscribe(consumer: Consumer<U>) {
            source!!.atomSubscribe(MapObserve(null, consumer, function!!))
      }

      /**
       * 建立T转换成R的一个类
       *
       */
      companion object
      class MapObserve<T, U>(
            private var downStream: Observer<U>?,
            private var downConsumer: Consumer<U>?,
            private var mapper: Function<T, U>
      ) : Observer<T> {

            override fun onSubscribe() {
                  downStream?.apply {
                        onSubscribe()
                  }
            }

            override fun onNext(e: T) {
                  downStream?.apply {
                        onNext(mapper.apply(e)!!)
                  }
                  downConsumer?.apply {
                        onNext(mapper.apply(e)!!)
                  }
            }

            override fun onError(e: Exception) {
                  downStream?.apply {
                        onError(e)
                  }
                  downConsumer?.apply {
                        onError(e)
                  }

            }

            override fun onComplete() {
                  downStream?.apply {
                        onComplete()
                  }
                  downConsumer?.apply {
                        onComplete()
                  }
            }
      }
}