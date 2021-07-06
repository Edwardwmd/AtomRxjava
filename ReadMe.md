## ◭说明

手写Rxjava,此Demo旨在深入Rxjava的核心思想,认识和理解Rxjava的内部原理机制，我们都知道Rxjava是目前开源的大库之一，涉及到了很多操作符以及线程调度相关的方法。Rxjava之所以受到开发者的青睐或许就是它具有响应式编程这个杀手锏，数据层与应用界面层可以实现解耦，性能也有着较大的提升（针对自己造轮子而言），然而优势不仅限于此，使用它写的功能逻辑代码层次分明，具有很强的可读性。

Rxjava的核心设计模式：观察者模式+装饰者模式，观察者模式是是实现响应式编程的关键模型，而装饰者模式则是防止Rxjava存在类爆炸的情况（打开Rxjava源码会发现，里面使用了大量的类，当中不乏操作符，线程调度这些关键类，如果不使用装饰者模式是实现“类的横向套娃”，这将会存在类爆炸的情况）。

这里着重介绍自己写的Demo，里面主要实现了Observable、create()、just()、map()、flatMap()、subscribeOn()、observerOn()、subseribe()这些操作符，可以组装成一条完整的Rxjava执行链流程,例子如下：

```kotlin
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
                                                    "接收到的数据是：id=${e.id} result=${e.result}  num=${e.resultCounter}"
                                    )
                              }

                              override fun onError(e: Exception) {
                                    Log.e(TAG, "下游error-->       ${e.message} ")
                              }

                              override fun onComplete() {
                                    Log.e(TAG, "下游数据处理成功~~")
                              }
                        })
```

执行结果如下：

<img src="/art/rxjava-result.png" style="zoom:50%;" />

