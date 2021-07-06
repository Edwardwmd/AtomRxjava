package com.edw.atomrxjava.rxjava.scheduler.schedulers

import android.os.Handler

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
 * Description:    主线程调度器
 ****************************************************************************************************
 */
class HandlerScheduler(private var handler: Handler) : Scheduler() {

      override fun createWorker(): Worker {
            return HandlerWorker(handler)
      }

      private companion object
      class HandlerWorker(private var handler: Handler) : Worker {
            override fun scheduler(r: Runnable) {
                  handler.post(r)
            }
      }
}