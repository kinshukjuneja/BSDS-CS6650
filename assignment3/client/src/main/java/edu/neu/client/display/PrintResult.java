package edu.neu.client.display;

import edu.neu.client.SkierClient;
import edu.neu.client.statistics.Result;
import edu.neu.client.test.TestDataUtil;

public class PrintResult {

    public void printThreadCount(SkierClient skierClient) {
        System.out.println("Threads: " + skierClient.getNumOfThreads());
    }

    public void printIp(SkierClient skierClient) {
        System.out.println("IP: " + skierClient.getIp());
    }

    public void printPort(SkierClient skierClient) {
        System.out.println("Port: " + skierClient.getPort());
    }

    public void printRequestsSent(Result result) {
        System.out.println("Total number of requests sent: " + result.getRequestSent());
    }

    public void printRequestsSuccess(Result result) {
        System.out.println("Total number of successful responses: " + result.getRequestSuccess());
    }

    public void printWallTime(TestDataUtil test) {
        System.out.println("Wall time: " + test.getWallTime() + " ms");
    }

    public void printMeanLatency(Result result) {
        System.out.println("Mean latency for all requests: " + result.getMean() + " ms");
    }

    public void printMedianLatency(Result result) {
        System.out.println("Median latency for all requests: " + result.getMedian() + " ms");
    }

    public void printCalculatedPercentile(int percentileNum, Result result) {
        System.out.println(percentileNum + "th percentile latency: " + result.calculatePercentile(percentileNum) + " ms");
    }

    public void printCalculatedThroughput(Result result) {
        System.out.println("Throughput: " + result.calculateThroughput() + "/secs");
    }

    public void printToCsvFormat(String info, Result result) {
        System.out.println(info);
        for (int i = 0; i < result.getStartTimeList().size(); ++i) {
            System.out.println(result.getStartTimeList().get(i) + "," + result.getLatencyList().get(i));
        }
    }

    public void printToConsole(SkierClient skierClient, TestDataUtil testDataUtil, Result result) {
        printThreadCount(skierClient);
        printIp(skierClient);
        printPort(skierClient);
        printRequestsSent(result);
        printRequestsSuccess(result);
        printWallTime(testDataUtil);
        printMeanLatency(result);
        printMedianLatency(result);
        printCalculatedPercentile(99, result);
        printCalculatedPercentile(95, result);
        printCalculatedThroughput(result);
    }
}
