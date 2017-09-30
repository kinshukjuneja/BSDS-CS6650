package edu.neu.client;

import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;

public class TestMyClient {
    private static List<Thread> listOfThreads = new ArrayList<>();
    private long startTime;
    private long endTime;
    private long wallTime;
    private long latency;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getWallTime() {
        return wallTime;
    }

    public void setWallTime(long wallTime) {
        this.wallTime = wallTime;
    }

    private MyClient buildClient() {
        return new MyClient(ClientBuilder.newClient());
    }

    private void buildThreads(MyClient myClient, String threadCount) {
        for(int i = 0; i < Integer.parseInt(threadCount); ++i) {
            Thread t = new Thread(myClient);
            listOfThreads.add(t);
        }
    }

    /**
     * NOTE: We are iterating a list of threads and joining them together.
     * In doing so, we are blocking the main() thread to execute followed code for correctness in latency.
     * Threads are still running concurrently and not blocking each other except main().
     * Verified concurrency with Thread.currentThread().getName() that displayed threads in random order.
     */

    private void joinThreads()  {
        for(Thread t : listOfThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startThreads() {
        for(Thread t : listOfThreads) {
            t.start();
        }
    }

    private long calculateWallTime() {
        return getEndTime() - getStartTime();
    }

    private double milliToseconds() {
        return getWallTime() / 1000.0;
    }

    private void printThreadCount(MyClient myClient) {
        System.out.println("Threads: " + myClient.getThreadCount());
    }

    private void printIterationsCount(MyClient myClient) {
        System.out.println("Iterations: " + myClient.getIterations());
    }

    private void printIp(MyClient myClient) {
        System.out.println("IP: " + myClient.getIp());
    }

    private void printPort(MyClient myClient) {
        System.out.println("Port: " + myClient.getPort());
    }

    private void printClientStartTime() {
        System.out.println("Client starting time: " + getStartTime());
    }

    private void printThreadsRunning(MyClient myClient) {
        System.out.println("All threads running: " + myClient.getThreadCount());
    }

    private void printThreadsEndTime() {
        System.out.println("All threads complete time: " + getEndTime());
    }

    private void printRequestsSent() {
        System.out.println("Total number of requests sent: " + MyClient.Result.getRequestSuccess());
    }

    private void printRequestsSuccess() {
        System.out.println("Total number of successful responses: " + MyClient.Result.getRequestSuccess());
    }

    private void printWallTime() {
        System.out.println("Wall time: " + getWallTime());
    }

    private void printMeanLatency() {
        System.out.println("Mean latency for all requests: " + MyClient.Result.getMean().getAsDouble());
    }

    private void printMedianLatency() {
        System.out.println("Median latency for all requests: " + MyClient.Result.getMedian());
    }

    private void printCalculatedPercentile(int x) {
        System.out.println(x + "the percentile latency: " + MyClient.Result.calculatePercentile(x));
    }

    private void printToConsole(MyClient myClient) {
        printThreadCount(myClient);
        printIterationsCount(myClient);
        printIp(myClient);
        printPort(myClient);
        printClientStartTime();
        printThreadsRunning(myClient);
        printThreadsEndTime();
        printRequestsSent();
        printRequestsSuccess();
        printWallTime();
        printMeanLatency();
        printMedianLatency();
        printCalculatedPercentile(99);
        printCalculatedPercentile(95);
    }

    private void validateAllInputs(String args[], MyClient myClientRunnable) {
        if(args.length > 0) inputThreads(args[0], myClientRunnable);
        if(args.length > 1) inputIterations(args[1], myClientRunnable);
        if(args.length > 2) inputIp(args[2], myClientRunnable);
        if(args.length > 3) inputPort(args[3], myClientRunnable);
    }

    private void inputThreads(String threadCount, MyClient myClientRunnable) {
        if(threadCount != null) myClientRunnable.setThreadCount(threadCount);
    }

    private void inputIterations(String iterations, MyClient myClientRunnable) {
        if(iterations != null) myClientRunnable.setIterations(iterations);
    }

    private void inputIp(String ip, MyClient myClientRunnable) {
        if(ip != null) myClientRunnable.setIp(ip);
    }

    private void inputPort(String port, MyClient myClientRunnable) {
        if(port != null) myClientRunnable.setPort(port);
    }

    public static void main(String[] args) {
        TestMyClient test = new TestMyClient();
        test.setStartTime(System.currentTimeMillis());
        MyClient myClientRunnable = test.buildClient();
        test.validateAllInputs(args, myClientRunnable);
        myClientRunnable.assignWebTarget();
        test.buildThreads(myClientRunnable, myClientRunnable.getThreadCount());
        test.startThreads();
        test.joinThreads();
        test.setEndTime(System.currentTimeMillis());
        test.setWallTime(test.calculateWallTime());
        MyClient.Result.calculateMean();
        MyClient.Result.calculateMedian();
        test.printToConsole(myClientRunnable);
        //MyClient.printToCsvFormat();
    }
}
