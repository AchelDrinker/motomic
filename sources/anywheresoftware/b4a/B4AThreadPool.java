package anywheresoftware.b4a;

import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class B4AThreadPool {
    private static final int THREADS_SPARE = 5;
    private ThreadPoolExecutor pool;
    private final WeakHashMap<Object, ConcurrentHashMap<Integer, Future<?>>> futures = new WeakHashMap<>();
    private final ConcurrentLinkedQueue<QueuedTask> queueOfTasks = new ConcurrentLinkedQueue<>();

    public B4AThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 50, 60L, TimeUnit.SECONDS, new SynchronousQueue()) { // from class: anywheresoftware.b4a.B4AThreadPool.1
            @Override // java.util.concurrent.ThreadPoolExecutor
            protected void afterExecute(Runnable runnable, Throwable th) {
                QueuedTask queuedTask = (QueuedTask) B4AThreadPool.this.queueOfTasks.poll();
                if (queuedTask != null) {
                    BA.handler.post(queuedTask);
                }
            }
        };
        this.pool = threadPoolExecutor;
        threadPoolExecutor.setThreadFactory(new MyThreadFactory(null));
    }

    private static class MyThreadFactory implements ThreadFactory {
        private final ThreadFactory defaultFactory;

        private MyThreadFactory() {
            this.defaultFactory = Executors.defaultThreadFactory();
        }

        /* synthetic */ MyThreadFactory(MyThreadFactory myThreadFactory) {
            this();
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread threadNewThread = this.defaultFactory.newThread(runnable);
            threadNewThread.setDaemon(true);
            return threadNewThread;
        }
    }

    public void submit(Runnable runnable, Object obj, int i) {
        if (this.pool.getActiveCount() > this.pool.getMaximumPoolSize() - 5) {
            this.queueOfTasks.add(new QueuedTask(runnable, obj, i));
        } else {
            submitToPool(runnable, obj, i);
        }
    }

    class QueuedTask implements Runnable {
        final Object container;
        final Runnable task;
        final int taskId;

        public QueuedTask(Runnable runnable, Object obj, int i) {
            this.task = runnable;
            this.container = obj;
            this.taskId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (B4AThreadPool.this.pool.getActiveCount() > B4AThreadPool.this.pool.getMaximumPoolSize() - 5) {
                BA.handler.postDelayed(this, 50L);
            } else {
                B4AThreadPool.this.submitToPool(this.task, this.container, this.taskId);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void submitToPool(Runnable runnable, Object obj, int i) {
        ConcurrentHashMap<Integer, Future<?>> concurrentHashMap;
        try {
            Future<?> futureSubmit = this.pool.submit(runnable);
            synchronized (this.futures) {
                concurrentHashMap = this.futures.get(obj);
                if (concurrentHashMap == null) {
                    concurrentHashMap = new ConcurrentHashMap<>();
                    this.futures.put(obj, concurrentHashMap);
                }
            }
            Iterator<Future<?>> it = concurrentHashMap.values().iterator();
            while (it.hasNext()) {
                if (it.next().isDone()) {
                    it.remove();
                }
            }
            concurrentHashMap.put(Integer.valueOf(i), futureSubmit);
        } catch (RejectedExecutionException unused) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            submitToPool(runnable, obj, i);
        }
    }

    public boolean isRunning(Object obj, int i) {
        Future<?> future;
        ConcurrentHashMap<Integer, Future<?>> concurrentHashMap = this.futures.get(obj);
        if (concurrentHashMap == null || (future = concurrentHashMap.get(Integer.valueOf(i))) == null) {
            return false;
        }
        return !future.isDone();
    }

    public void markTaskAsFinished(Object obj, int i) {
        ConcurrentHashMap<Integer, Future<?>> concurrentHashMap = this.futures.get(obj);
        if (concurrentHashMap == null) {
            return;
        }
        concurrentHashMap.remove(Integer.valueOf(i));
    }
}
