package com.edw.atomrxjava.rxjava.scheduler

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.edw.atomrxjava.rxjava.scheduler.executor.AtomThreadPoolExecutor
import com.edw.atomrxjava.rxjava.scheduler.schedulers.*

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
 * Description:    线程调度器，定义不同线程，按需使用对应的线程。
 ****************************************************************************************************
 */
class Schedulers {

      companion object {
            private val MAIN_THREAD = HandlerScheduler(Handler(Looper.myLooper()!!))

            private val IO_THREAD = IOThreadScheduler(  AtomThreadPoolExecutor.newIOThreadPool(
                  AtomThreadPoolExecutor.getCpuCoreCount(),
                  "IOCacheThreadPool"
            ))

            private val NEW_THREAD = NewThreadScheduler( AtomThreadPoolExecutor.newThreadPool(
                  AtomThreadPoolExecutor.getCpuCoreCount(),
                  "NewThreadPool"
            ))

            private val SINGLE_THREAD = SingleThreadScheduler(AtomThreadPoolExecutor.newSingleThreadPool("SingleThreadPool"))


            /**
             * 主线程
             */
            fun mainThread(): Scheduler {
                  return MAIN_THREAD
            }

            /**
             * IO线程
             */
            fun io(): Scheduler {
                  return IO_THREAD
            }

            /**
             * 新线程
             */
            fun newThread(): Scheduler {
                  return NEW_THREAD
            }

            /**
             * 单个线程，仅仅一个线程执行
             */
            fun single(): Scheduler {
                  return SINGLE_THREAD
            }

      }


}