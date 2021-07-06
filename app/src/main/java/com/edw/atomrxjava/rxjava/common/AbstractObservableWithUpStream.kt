package com.edw.atomrxjava.rxjava.common

import com.edw.atomrxjava.rxjava.Observable

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
 * Description:    ToDo
 ****************************************************************************************************
 */
abstract class AbstractObservableWithUpStream<U, T>() : Observable<U>() {

       var source: ObservableSource<T>? = null

      constructor(source: ObservableSource<T>) : this() {
            this.source = source

      }



}