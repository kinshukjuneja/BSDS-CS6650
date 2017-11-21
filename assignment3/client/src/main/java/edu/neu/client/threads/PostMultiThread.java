package edu.neu.client.threads;


import edu.neu.client.SkierClient;
import edu.neu.client.statistics.Result;
import edu.neu.client.test.TestDataUtil;

public class PostMultiThread extends Thread {
    private int startIndex;
    private int endIndex;
    private SkierClient skierClient;
    private Result result;

    public PostMultiThread(int startIndex, int endIndex, SkierClient skierClient, Result result) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.skierClient = skierClient;
        this.result = result;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; ++i) {
            System.out.println(skierClient.postData(TestDataUtil.getRFIDDataIn().get(i), result));
        }
    }
}
