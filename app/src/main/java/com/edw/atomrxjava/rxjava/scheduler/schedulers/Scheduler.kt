package com.edw.atomrxjava.rxjava.scheduler.schedulers

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
 * Description:    ToDo
 ****************************************************************************************************
 */
abstract class Scheduler {

      abstract fun createWorker(): Worker

    interface Worker{
         fun scheduler(r:Runnable)
    }

}