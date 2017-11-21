package edu.neu.server.model;

public class SkierMetrics {
    private long startTime;
    private long responseTime;

    public SkierMetrics(long startTime, long responseTime) {
        this.startTime = startTime;
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return startTime+":"+responseTime;
    }
}
