package com.edw.atomrxjava.rxjava.scheduler.executor

import java.util.concurrent.*

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
 * Description:    自定义线程池
 ****************************************************************************************************
 */
class AtomThreadPoolExecutor {

      companion object {

            /**
             * IO线程调度的线程池
             */
            fun newIOThreadPool(size: Int, threadPoolName: String): ExecutorService {
                  return ThreadPoolExecutor(
                        size,
                        size * 2,
                        0L,
                        TimeUnit.SECONDS,
                        LinkedBlockingDeque<Runnable>(size * 2),
                        AtomThreadFactory(threadPoolName),
                        ThreadPoolExecutor.DiscardPolicy()
                  )
            }

            /**
             * newThread线程调度的线程池
             */
            fun newThreadPool(size: Int, threadPoolName: String): ExecutorService {
                  return ThreadPoolExecutor(
                        size,
                        size * 2,
                        5L,
                        TimeUnit.SECONDS,
                        SynchronousQueue<Runnable>(),
                        AtomThreadFactory(threadPoolName),
                        ThreadPoolExecutor.DiscardPolicy()
                  )
            }

            /**
             * Single线程调度的线程池
             */
            fun newSingleThreadPool(threadPoolName: String): ExecutorService {
                  return ThreadPoolExecutor(
                        1,
                        1,
                        0L,
                        TimeUnit.SECONDS,
                        LinkedBlockingQueue<Runnable>(),
                        AtomThreadFactory(threadPoolName),
                        ThreadPoolExecutor.DiscardPolicy()
                  )
            }

            /**
             * CPU核心数
             */
            fun getCpuCoreCount(): Int {
                  return Runtime.getRuntime().availableProcessors()
            }
      }
}