package edu.neu.client.threads;

import edu.neu.client.SkierClient;
import edu.neu.client.statistics.Result;
import edu.neu.client.test.TestDataUtil;

public class ReadMultiThread extends Thread {
    private int startIndex;
    private int endIndex;
    private SkierClient skierClient;
    private Result result;
    private int day;

    public ReadMultiThread(int startIndex, int endIndex, SkierClient skierClient, Result result, int day) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.skierClient = skierClient;
        this.result = result;
        this.day = day;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; ++i) {
           System.out.println(skierClient.getData(TestDataUtil.getRFIDDataIn().get(i).getSkierID(), day, result));
        }
    }
}
