package edu.neu.client.test;

import edu.neu.client.SkierMetrics;
import edu.neu.client.display.PrintResult;
import edu.neu.client.statistics.Result;

public class TestSkierMetrics {
    private static final String BASE_URI_1 = "http://54.69.50.91:8080/assignment-3/webapi/";
    private static final String BASE_URI_2 = "http://52.88.226.56:8080/assignment-3/webapi/";
    private static final String BASE_URI_3 = "http://34.209.239.142:8080/assignment-3/webapi/";
    private static final String GET_REQUEST = "GET";
    private static final String POST_REQUEST = "POST";

    public void testResult(PrintResult printResult, Result result, String serverID) {
        result.sortList();
        result.calculateMean();
        result.calculateMedian();
        System.out.println("Server " + serverID + " metrics:");
        printResult.printMeanLatency(result);
        printResult.printMedianLatency(result);
        printResult.printCalculatedPercentile(99, result);
        printResult.printCalculatedPercentile(95, result);
        printResult.printCalculatedThroughput(result);
    }

    public static void main(String[] args) {
        TestSkierMetrics testSkierMetrics = new TestSkierMetrics();
        SkierMetrics skierMetrics = new SkierMetrics();
        Result resultServer1 = new Result();
        PrintResult printResult = new PrintResult();
        skierMetrics.setWebTarget(BASE_URI_1);
        //skierMetrics.getMetrics(GET_REQUEST, resultServer1);
        skierMetrics.getMetrics(POST_REQUEST, resultServer1);

        Result resultServer2 = new Result();
        skierMetrics.setWebTarget(BASE_URI_2);
        //skierMetrics.getMetrics(GET_REQUEST, resultServer2);
        skierMetrics.getMetrics(POST_REQUEST, resultServer2);

        Result resultServer3 = new Result();
        skierMetrics.setWebTarget(BASE_URI_3);
        //skierMetrics.getMetrics(GET_REQUEST, resultServer3);
        skierMetrics.getMetrics(POST_REQUEST, resultServer3);
        skierMetrics.closeClient();

        Result allResult = new Result();
        allResult.buildCollectiveList(resultServer1, resultServer2, resultServer3);

        printResult.printToCsvFormat("Printing result for server 1", resultServer1);
        printResult.printToCsvFormat("Printing result for server 2", resultServer2);
        printResult.printToCsvFormat("Printing result for server 3", resultServer3);

        testSkierMetrics.testResult(printResult, resultServer1, "1");
        testSkierMetrics.testResult(printResult, resultServer2, "2");
        testSkierMetrics.testResult(printResult, resultServer3, "3");
        testSkierMetrics.testResult(printResult, allResult, "collective");
    }
}
