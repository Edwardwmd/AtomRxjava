package com.edw.atomrxjava

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.edw.atomrxjava.rxjava.ConversionResult
import com.edw.atomrxjava.rxjava.Observable
import com.edw.atomrxjava.rxjava.common.*
import com.edw.atomrxjava.rxjava.common.Function
import com.edw.atomrxjava.rxjava.scheduler.Schedulers

class MainActivity : AppCompatActivity() {
      companion object {

            private const val TAG = "MainActivity"
      }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val jump = findViewById<Button>(R.id.btn_jump)
            jump.setOnClickListener {
                  var id = 0
                  Observable
                        .create(object : ObservableOnSubscribe<String> {
                              override fun subscribe(e: Emitter<String>) {
                                    Log.e(TAG, "create操作符   线程--> ${Thread.currentThread().name}")
                                    e.onNext("Kotlin")
                              }
                        }).subscribeOn(Schedulers.io())
                        .observerOn(Schedulers.newThread())
                        .map(object : Function<String, Map<String, Double>> {
                              override fun apply(e: String): Map<String, Double> {
                                    val map = HashMap<String, Double>()
                                    map[e] = e.hashCode() * Math.PI
                                    Log.e(TAG, "map操作符      线程--> ${Thread.currentThread().name}")
                                    return map
                              }
                        }).observerOn(Schedulers.single())
                        .flatMap(object : Function<Map<String, Double>, ObservableSource<ConversionResult>> {
                              override fun apply(e: Map<String, Double>): ObservableSource<ConversionResult>? {
                                    Log.e(TAG, "flatMap操作符  线程--> ${Thread.currentThread().name}")

                                    e.entries.forEach {
                                          return Observable.just(ConversionResult(++id, it.key, it.value))
                                    }

                                    return null
                              }
                        })
                        .observerOn(Schedulers.mainThread())
                        .subscribe(object : Observer<ConversionResult> {
                              override fun onSubscribe() {
                              }

                              override fun onNext(e: ConversionResult) {
                                    Log.e(
                                          TAG, "下游线程--》${Thread.currentThread().name}\n" +
                                                    "接收到的数据是：id=${e.id} result=${e.result} num=${e.resultCounter}"
                                    )
                              }

                              override fun onError(e: Exception) {
                                    Log.e(TAG, "下游error-->       ${e.message} ")
                              }

                              override fun onComplete() {
                                    Log.e(TAG, "下游数据处理成功~~")
                              }
                        })
            }
      }


}