package com.edw.atomrxjava.rxjava.common

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
 * Description:    观察者接口，定义了Rxjava下游的相关方法
 ****************************************************************************************************
 */
interface Observer<T> {

      /**
       * 订阅
       */
      fun onSubscribe()

      /**
       * 发射事件的方法
       */
      fun onNext(e: T)

      /**
       * 异常处理方法
       */
      fun onError(e: Exception)

      /**
       * 发射完成
       */
      fun onComplete()

}