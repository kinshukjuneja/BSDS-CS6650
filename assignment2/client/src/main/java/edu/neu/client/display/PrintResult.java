package edu.neu.client.display;

import edu.neu.client.SkierClient;
import edu.neu.client.statistics.Result;
import edu.neu.client.test.TestDataUtil;

public class PrintResult {

    private void printThreadCount(SkierClient skierClient) {
        System.out.println("Threads: " + skierClient.getNumOfThreads());
    }

    private void printIp(SkierClient skierClient) {
        System.out.println("IP: " + skierClient.getIp());
    }

    private void printPort(SkierClient skierClient) {
        System.out.println("Port: " + skierClient.getPort());
    }

    private void printRequestsSent(Result result) {
        System.out.println("Total number of requests sent: " + result.getRequestSent());
    }

    private void printRequestsSuccess(Result result) {
        System.out.println("Total number of successful responses: " + result.getRequestSuccess());
    }

    private void printWallTime(TestDataUtil test) {
        System.out.println("Wall time: " + test.getWallTime() + " ms");
    }

    private void printMeanLatency(Result result) {
        System.out.println("Mean latency for all requests: " + result.getMean() + " ms");
    }

    private void printMedianLatency(Result result) {
        System.out.println("Median latency for all requests: " + result.getMedian() + " ms");
    }

    private void printCalculatedPercentile(int percentileNum, Result result) {
        System.out.println(percentileNum + "th percentile latency: " + result.calculatePercentile(percentileNum) + " ms");
    }

    public static void printToCsvFormat(Result result) {
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
        printToCsvFormat(result);
    }
}
