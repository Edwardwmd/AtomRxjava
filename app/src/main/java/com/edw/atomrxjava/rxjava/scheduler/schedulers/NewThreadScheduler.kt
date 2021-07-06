package com.edw.atomrxjava.rxjava.scheduler.schedulers

import java.util.concurrent.ExecutorService

/*****************************************************************************************************
 * Project Name:    AtomRxjava
 *
 * Date:            2021-07-06
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
class NewThreadScheduler(private val t:ExecutorService):Scheduler() {

      override fun createWorker(): Worker {
            return NewThreadWorker(t)
      }

      private companion object class NewThreadWorker(private val t:ExecutorService):Worker{

            override fun scheduler(r: Runnable) {
                  t.execute(r)

            }
      }
}