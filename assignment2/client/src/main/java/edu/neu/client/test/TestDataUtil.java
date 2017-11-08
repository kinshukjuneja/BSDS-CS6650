package edu.neu.client.test;

import edu.neu.client.SkierClient;
import edu.neu.client.model.RFIDLiftData;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {
    private long startTime;
    private long endTime;
    private long wallTime;
    private static List<RFIDLiftData> RFIDDataIn = new ArrayList<>();
    private static final String IP = "35-161-211-30"; //replace "-" with "." (Used "-" for security purpose)

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getWallTime() {
        return wallTime;
    }

    public void setWallTime(long wallTime) {
        this.wallTime = wallTime;
    }

    public static void addRFIDLiftData(RFIDLiftData rfidLiftData) {
        RFIDDataIn.add(rfidLiftData);
    }

    public static List<RFIDLiftData> getRFIDDataIn() {
        return RFIDDataIn;
    }

    public SkierClient buildClient() {
        return new SkierClient(ClientBuilder.newClient());
    }

    /**
     * sets Number of threads, IP and Port
     */
    public void validateAllInputs(String args[], SkierClient skierClient) {
        if (args.length > 0) inputThreads(args[0], skierClient);
        else inputThreads("128", skierClient);
        if (args.length > 1) inputIp(args[1], skierClient);
        else inputIp(IP, skierClient);
        if (args.length > 2) inputPort(args[2], skierClient);
        else inputPort("8080", skierClient);
    }

    public void inputThreads(String numOfThreads, SkierClient skierClient) {
        if (numOfThreads != null) skierClient.setNumOfThreads(numOfThreads);
    }

    public void inputIp(String ip, SkierClient skierClient) {
        if (ip != null) skierClient.setIp(ip);
    }

    public void inputPort(String port, SkierClient skierClient) {
        if (port != null) skierClient.setPort(port);
    }
}
