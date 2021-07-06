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
 * Description:    被观察者Observable顶级接口
 ****************************************************************************************************
 */
interface ObservableSource<T> {

      fun atomSubscribe(observer: Observer<T>)

      fun atomSubscribe(consumer: Consumer<T>)

}