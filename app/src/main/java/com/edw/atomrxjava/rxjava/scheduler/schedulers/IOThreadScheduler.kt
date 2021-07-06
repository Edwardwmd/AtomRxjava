package com.edw.atomrxjava.rxjava.scheduler.schedulers

import java.util.concurrent.ExecutorService

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
 * Description:    IO线程调度器
 ****************************************************************************************************
 */
class IOThreadScheduler(private var t:ExecutorService): Scheduler(){

      class IOThreadWorker(private var t: ExecutorService) : Worker {

            override fun scheduler(r: Runnable) {
                  t.execute(r)
            }

      }

      override fun createWorker(): Worker {
            return IOThreadWorker(t)
      }
}
