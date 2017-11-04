package edu.neu.client.test;


import edu.neu.client.SkierClient;
import edu.neu.client.chart.ChartPlotXY;
import edu.neu.client.display.PrintResult;
import edu.neu.client.statistics.Result;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kinsh
 *
 */
public class TestPostData {
    private static final String POST = "POST";
    private static final int DATA_SIZE = 800000;

    public static void main(String[] args) {
        TestDataUtil testDataUtil = new TestDataUtil();
        Result result = new Result();
        PrintResult printResult = new PrintResult();
        CSVParser csvParser = new CSVParser();
        csvParser.parseCSV(1);

        SkierClient postSkierClient = testDataUtil.buildClient();
        testDataUtil.validateAllInputs(args, postSkierClient);
        postSkierClient.assignWebTarget();
        postSkierClient.setPartition(DATA_SIZE / Integer.valueOf(postSkierClient.getNumOfThreads()));

        testDataUtil.setStartTime(System.currentTimeMillis());
        postSkierClient.startRequest(postSkierClient, result, POST);
        testDataUtil.setEndTime(System.currentTimeMillis());
        testDataUtil.setWallTime(testDataUtil.getEndTime() - testDataUtil.getStartTime());

        result.sortList();
        result.calculateMean();
        result.calculateMedian();
        printResult.printToConsole(postSkierClient, testDataUtil, result);


        ChartPlotXY chartPlotXY = new ChartPlotXY();
        try {
            chartPlotXY.getChart(result.getLatencyList(), result.getStartTimeList(), "POST: LATENCY VS REQUEST TIME");
        } catch (IOException ex) {
            Logger.getLogger(TestPostData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}