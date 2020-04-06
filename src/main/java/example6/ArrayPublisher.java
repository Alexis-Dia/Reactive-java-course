package example6;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * @author Alexey Druzik on 4/6/2020
 */
public class ArrayPublisher<T> implements Publisher<T> {

    private final T[] array;

    public ArrayPublisher(T[] array) {
        this.array = array;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        Subscription subscription = new Subscription() {
            AtomicInteger index = new AtomicInteger();
            AtomicLong requested = new AtomicLong();
            AtomicBoolean canceled = new AtomicBoolean();

            @Override
            public void request(long l) {
                /*long initialRequested = requested.getAndAdd(l);*/

                long initialRequested;
                for (;;) {
                    initialRequested = requested.get();

                    if (initialRequested == Long.MAX_VALUE) {
                        return;
                    }

                    l = initialRequested + l;

                    if (l <= 0) {
                        l = Long.MAX_VALUE;
                    }

                    if (requested.compareAndSet(initialRequested, l)) {
                        break;
                    }
                }

                if (initialRequested > 0) {
                    return;
                }

                int sent = 0;

                while (true) {
                    for (;sent < requested.get() && index.get() < array.length; sent++, index.incrementAndGet()) {
                        if (canceled.get()) {
                            return;
                        }

                        T element = array[index.get()];
                        if (element == null) {
                            subscriber.onError(new NullPointerException());
                            return;
                        }
                        subscriber.onNext(element);
                    }

                    if (canceled.get()) {
                        return;
                    }

                    if (index.get() == array.length) {
                        subscriber.onComplete();
                        return;
                    }

                    if (requested.addAndGet(-sent) == 0) {
                        return;
                    };

                    sent = 0;
                }
            }

            @Override
            public void cancel() {
                System.out.println("publisher = cancel");
                canceled.set(true);
            }
        };
        subscriber.onSubscribe(subscription);
    }

    public static Long[] generate(long num) {
        return LongStream.range(0, num >= Integer.MAX_VALUE ? 1000000 : num)
            .boxed()
            .toArray(Long[]::new);
    }
}

