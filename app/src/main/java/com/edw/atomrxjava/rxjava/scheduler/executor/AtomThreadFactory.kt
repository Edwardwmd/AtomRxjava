package com.edw.atomrxjava.rxjava.scheduler.executor

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

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
 * Description:    自定义线程工厂，实现名称自定义
 ****************************************************************************************************
 */
class AtomThreadFactory(poolName: String) : ThreadFactory {

      companion object {
            private val poolNumber by lazy { AtomicInteger(1) }
      }

      private val threadNum by lazy { AtomicInteger(1) }
      private var group: ThreadGroup? = null
      private var tempPoolName: String? = null

      init {
            val s = System.getSecurityManager()
            group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup
            tempPoolName = "$poolName-$poolNumber --> Thread-"
      }

      override fun newThread(r: Runnable?): Thread {

            val t = Thread(group, r!!, "${tempPoolName}$threadNum", 0)
            //设置为守护线程
            if (t.isDaemon) t.isDaemon = true
            //设置线程优先级
            if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
            return t
      }
}