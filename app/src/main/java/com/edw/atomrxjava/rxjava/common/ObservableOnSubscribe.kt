package com.edw.atomrxjava.rxjava.common

/**
 * 建立Observable-Emitter-Observer三者之间的联系，
 * 相当于桥梁
 */
interface ObservableOnSubscribe<T> {

      fun subscribe(e: Emitter<T>)

}
