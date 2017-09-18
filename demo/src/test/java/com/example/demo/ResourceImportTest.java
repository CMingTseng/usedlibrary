package com.example.demo;

import android.util.Pair;

import org.junit.Test;
import org.junit.runner.Runner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by Administrator on 2017/9/12 0012.
 */

public class ResourceImportTest {
    File[] mFromFile;
    File[] mToFile;
    /**
     * F copyName, S realName
     */
    List<Pair<String,String>> mFiles;
    /**
     * --corePoolSize（线程池的基本大小）：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，
     * 即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时就不再创建。
     * 如果调用了线程池的prestartAllCoreThreads方法，线程池会提前创建并启动所有基本线程。
     *
     * --runnableTaskQueue（任务队列）：用于保存等待执行的任务的阻塞队列。 可以选择以下几个阻塞队列。
     *    -ArrayBlockingQueue：是一个基于数组结构的有界阻塞队列，此队列按 FIFO（先进先出）原则对元素进行排序。
     *    -LinkedBlockingQueue：一个基于链表结构的阻塞队列，此队列按FIFO （先进先出） 排序元素，
     *    吞吐量通常要高于ArrayBlockingQueue。静态工厂方法Executors.newFixedThreadPool()使用了这个队列。
     *    -SynchronousQueue：一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，
     *    否则插入操作一直处于阻塞状态，吞吐量通常要高于LinkedBlockingQueue，
     *    静态工厂方法Executors.newCachedThreadPool使用了这个队列。
     *    -PriorityBlockingQueue：一个具有优先级的无限阻塞队列。
     *
     * --maximumPoolSize（线程池最大大小）：线程池允许创建的最大线程数。如果队列满了，
     *   并且已创建的线程数小于最大线程数，则线程池会再创建新的线程执行任务。
     *   值得注意的是如果使用了无界的任务队列这个参数就没什么效果。
     *
     * --ThreadFactory：用于设置创建线程的工厂，可以通过线程工厂给每个创建出来的线程设置更有意义的名字。
     *
     * --RejectedExecutionHandler（饱和策略）：当队列和线程池都满了，说明线程池处于饱和状态，
     *   那么必须采取一种策略处理提交的新任务。这个策略默认情况下是AbortPolicy，表示无法处理新任务时抛出异常。
     *   以下是JDK1.5提供的四种策略。
     *      -AbortPolicy：直接抛出异常。
     *      -CallerRunsPolicy：只用调用者所在线程来运行任务。
     *      -DiscardOldestPolicy：丢弃队列里最近的一个任务，并执行当前任务。
     *      -DiscardPolicy：不处理，丢弃掉。
     *      -当然也可以根据应用场景需要来实现RejectedExecutionHandler接口自定义策略。
     *    如记录日志或持久化不能处理的任务。
     *
     * --keepAliveTime（线程活动保持时间）：线程池的工作线程空闲后，保持存活的时间。所以如果任务很多，
     *   并且每个任务执行的时间比较短，可以调大这个时间，提高线程的利用率。
     *
     * --TimeUnit（线程活动保持时间的单位）：可选的单位有天（DAYS），
     *   小时（HOURS），分钟（MINUTES），毫秒(MILLISECONDS)，微秒(MICROSECONDS, 千分之一毫秒)
     *   和毫微秒(NANOSECONDS, 千分之一微秒)。
     *
     *   new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, milliseconds,runnableTaskQueue, handler)
     */
    ExecutorService mExecutorService = null;

    File temFile = null;
    File targetFile = null;

    void init(){
        mFromFile = new File("F:\\AndroidProject\\usedlibrary\\demo\\src\\main\\res").listFiles();
        mToFile = new File("C:\\Users\\Administrator\\Desktop\\Data").listFiles();
        mFiles = new ArrayList<>();
        mExecutorService = Executors.newFixedThreadPool(5);
    }

    @Test
    public void main() throws Exception {
        init();
        for (int i = 0; i < mFromFile.length; i++) {
            for (int j = 0; j < mToFile.length; j++) {
                if (mFromFile[i].getName().equals(mToFile[j].getName()) && mFromFile[i].isDirectory() && mToFile[j].isDirectory()){
                    mExecutorService.execute(new ResourceImport(mFromFile[i].listFiles(),mToFile[j].listFiles()));
                }
            }
        }
    }
    void printDirectory(String filePath){
        File file = new File(filePath);
        for (File itemFile:
             file.listFiles()) {
            System.out.println(itemFile.getName());
        }
    }

    class ResourceImport implements Runnable {
        File[] mFromFile;
        File[] mToFile;
        public ResourceImport(File[] fromFile, File[] toFile) {
            mFromFile = fromFile;
            mToFile = toFile;
        }

        @Override
        public void run() {
            for (Pair<String, String> pairFile :
                    mFiles) {
                temFile = findFile(mToFile,pairFile.second);
                targetFile = findFile(mFromFile,pairFile.first);

            }
        }
    }

    static File findFile(File[] files,String fileName){
        for (int i = 0; i < files.length; i++) {
            if (fileName.equals(files[i].getName())){
                return files[i];
            }
        }
        return null;
    }
}
