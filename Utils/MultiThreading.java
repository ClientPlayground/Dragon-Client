package Benz.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreading {

    public static ExecutorService POOL = Executors.newCachedThreadPool(new ThreadFactory() {
        AtomicInteger counter = new AtomicInteger(0);

        public Thread newThread(Runnable r) {
            return new Thread(r, String.format("Thread %s", new Object[] { Integer.valueOf(this.counter.incrementAndGet())}));
        }
    });
    private static ScheduledExecutorService RUNNABLE_POOL = Executors.newScheduledThreadPool(3, new ThreadFactory() {
        private AtomicInteger counter = new AtomicInteger(0);

        public Thread newThread(Runnable r) {
            return new Thread(r, "Thread " + this.counter.incrementAndGet());
        }
    });

    public static void schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        MultiThreading.RUNNABLE_POOL.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public static void runAsync(Runnable runnable) {
        MultiThreading.POOL.execute(runnable);
    }

    public static int getTotal() {
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) MultiThreading.POOL;

        return tpe.getActiveCount();
    }
}
