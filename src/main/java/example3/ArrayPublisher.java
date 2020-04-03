package example3;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.stream.LongStream;

/**
 * @author Alexey Druzik on 4/3/2020
 */
public class ArrayPublisher<T> implements Publisher<T> {

    private final T[] array;

    public ArrayPublisher(T[] array) {
        this.array = array;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        Subscription subscription = new Subscription() {
            int index;

            @Override
            public void request(long l) {
                for (int i = 0; i < l && index < array.length; i++, index++) {
                    T element = array[index];
                    if (element == null) {
                        subscriber.onError(new NullPointerException());
                        return;
                    }
                    subscriber.onNext(element);
                }
                if (index == array.length) {
                    subscriber.onComplete();
                }
            }

            @Override
            public void cancel() {
                System.out.println("publisher = cancel");
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

