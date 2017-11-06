package edu.neu.client.test;

import edu.neu.client.SkierClient;
import edu.neu.client.display.PrintResult;
import edu.neu.client.statistics.Result;
/**
 * @author Kinsh
 */
public class TestData {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final int DATA_SIZE = 800000;

    public static void main(String[] args) {
        TestDataUtil testDataUtil = new TestDataUtil();
        Result result = new Result();
        PrintResult printResult = new PrintResult();

        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(1);

        SkierClient readSkierClient = testDataUtil.buildClient();
        testDataUtil.validateAllInputs(args, readSkierClient);
        readSkierClient.setPartition(DATA_SIZE / Integer.valueOf(readSkierClient.getNumOfThreads()));
        readSkierClient.assignWebTarget();

        testDataUtil.setStartTime(System.currentTimeMillis());
        readSkierClient.startRequest(readSkierClient, result, POST);
        readSkierClient.startRequest(readSkierClient, result, GET);
        testDataUtil.setEndTime(System.currentTimeMillis());
        testDataUtil.setWallTime(testDataUtil.getEndTime() - testDataUtil.getStartTime());

        result.sortList();
        result.calculateMean();
        result.calculateMedian();
        printResult.printToConsole(readSkierClient, testDataUtil, result);
    }
}