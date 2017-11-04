package edu.neu.client.test;


import edu.neu.client.SkierClient;
import edu.neu.client.chart.ChartPlotXY;
import edu.neu.client.display.PrintResult;
import edu.neu.client.statistics.Result;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Kinsh
 */
public class TestGetData {
    private static final String GET = "GET";
    private static final int DATA_SIZE = 800000;

    public static void main(String[] args) {
        //BasicConfigurator.configure();
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
        readSkierClient.startRequest(readSkierClient, result, GET);
        testDataUtil.setEndTime(System.currentTimeMillis());
        testDataUtil.setWallTime(testDataUtil.getEndTime() - testDataUtil.getStartTime());

        result.sortList();
        result.calculateMean();
        result.calculateMedian();
        printResult.printToConsole(readSkierClient, testDataUtil, result);

        ChartPlotXY chartPlotXY = new ChartPlotXY();
        try {
            chartPlotXY.getChart(result.getLatencyList(), result.getStartTimeList(), "GET: LATENCY VS REQUEST TIME");
        } catch (IOException ex) {
            Logger.getLogger(TestPostData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}