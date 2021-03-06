package edu.neu.client.test;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import edu.neu.client.SkierClient;
import edu.neu.client.display.PrintResult;
import edu.neu.client.statistics.Result;
/**
 * @author Kinsh
 */
public class TestData {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static int dayNum = 5;

    public static void main(String[] args) {
        TestDataUtil testDataUtil = new TestDataUtil();
        Result result = new Result();
        PrintResult printResult = new PrintResult();

        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(dayNum);

        SkierClient skierClient = testDataUtil.buildClient();
        testDataUtil.validateAllInputs(args, skierClient);
        skierClient.setPartition(TestDataUtil.getRFIDDataIn().size() / Integer.valueOf(skierClient.getNumOfThreads()));
        skierClient.assignWebTarget();

        testDataUtil.setStartTime(System.currentTimeMillis());
        skierClient.startRequest(skierClient, result, POST, dayNum);
        skierClient.startRequest(skierClient, result, GET, dayNum);
        testDataUtil.setEndTime(System.currentTimeMillis());
        testDataUtil.setWallTime(testDataUtil.getEndTime() - testDataUtil.getStartTime());
        skierClient.closeClient();

        printResult.printToCsvFormat("Printing result for client metrics: ", result);

        result.sortList();
        result.calculateMean();
        result.calculateMedian();
        printResult.printToConsole(skierClient, testDataUtil, result);
    }
}