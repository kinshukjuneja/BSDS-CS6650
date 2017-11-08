package edu.neu.client.statistics;

import org.jetbrains.annotations.Contract;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Result {
    private AtomicInteger requestSent = new AtomicInteger(0);
    private AtomicInteger requestSuccess = new AtomicInteger(0);
    private CopyOnWriteArrayList<Long> startTimeList = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Long> latencyList = new CopyOnWriteArrayList<>();
    private double mean;
    private double median;

    @org.jetbrains.annotations.Contract(pure = true)
    public CopyOnWriteArrayList<Long> getStartTimeList() {
        return startTimeList;
    }

    @Contract(pure = true)
    public CopyOnWriteArrayList<Long> getLatencyList() {
        return latencyList;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public void incrementRequestSent() {
        requestSent.addAndGet(1);
    }

    public int getRequestSent() {
        return requestSent.get();
    }

    public void incrementRequestSuccess() {
        requestSuccess.addAndGet(1);
    }

    public int getRequestSuccess() {
        return requestSuccess.get();
    }

    public void addStartTime(long startTime) {
        startTimeList.add(startTime);
    }

    public void addLatency(long latency) {
        latencyList.add(latency);
    }

    /**
     * Sort Latency List in ascending order
     */
    public void sortList() {
        Object[] arr = latencyList.toArray();
        Arrays.sort(arr);
        for(int i = 0; i < latencyList.size(); ++i) {
            latencyList.set(i, (Long) arr[i]);
        }
    }

    /**
     * Calculating mean
     */
    public void calculateMean() {
        if(latencyList == null || latencyList.size() == 0) return;
        else {
            for(long val: latencyList) {
                mean += val;
            }
            mean /= latencyList.size();
        }
    }

    /**
     * Calculating Median
     */
    public void calculateMedian() {
        if(latencyList == null || latencyList.size() == 0) return;
        int size = latencyList.size();
        if(size % 2 == 0) median = (double) (latencyList.get(size / 2) + latencyList.get((size / 2) + 1)) / 2;
        else median = (double) latencyList.get(size / 2);
    }
    /**
     * Calculating Percentile for given input: 95/99th Percentile
     */
    public double calculatePercentile(int val) {
        try {
            return latencyList.get((latencyList.size() / 100) * val);
        } catch(IndexOutOfBoundsException ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}