Reactive-java-course

Oleh Dokuka - Reactive Hardcore: How to Build Your Publisher
Rep - https://github.com/CollaborationInEncapsulation/reactive-hardcore
Video with explanations on youtube - https://www.youtube.com/watch?v=qmuNAWKNJWs

Oleh tries to implement spec of Rx-programming example-by-example.
1. Example1 is about ordering of calling methods in 
   Here Oleh says about right order: 
       - in Publisher create Subscription
       - send to Subscriber -> Subscription: subscriber.onSubscribe(subscription);
       - send data: subscriber.onNext(data);
       - says to Subscriber that is finish: subscriber.onComplete();
2. Example2 (10:20) is about back pressure (backpressure). Backpressure - it's control of thread of data. Problem of 
    example1 is that data was came when we even didn't ask.
3. Example3 (12:30) is about checking null.
4. Example4 (13:50) is about recursion. Oleh uses pattern - work in progress.
5. Example5 is about subscription canceling.
    19:20 is about reactive-streams-tck. We have to add dependence and inherit from PublisherVerification-class. 
    This lib reactive-streams-tck uses for checking all 41 requirements of reactive-specification.
6. Example6 (24:20 - 36:50) is about multithreading.

Networking in Java with NIO and Netty — Konstantin Slisenko
1. Example11 is about simply ServerSocket server. For running use - telnet localhost 45000