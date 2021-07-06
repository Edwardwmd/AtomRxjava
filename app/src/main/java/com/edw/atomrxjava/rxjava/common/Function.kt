package com.edw.atomrxjava.rxjava.common

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
 * Description:    类型转换的接口T-->R
 ****************************************************************************************************
 */
interface Function<T,R> {
      fun apply(e:T):R?
}