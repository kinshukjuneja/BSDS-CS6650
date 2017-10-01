package edu.neu.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MyClient implements Runnable {
    private Client client; //To be initialized when creating an instance of this class
    private WebTarget webTarget; //Created after ip and port is given at terminal
    private String threadCount = "10"; //Default: 10
    private String iterations = "100"; //Default: 100
    private String ip = "35.161.211.30"; //Default: "35.161.211.30"
    private String port = "8080"; //Default: 8080
    private String myPath = "/assignment-1/webapi/myresource"; //Default: "/Assignment1/webapi/myresource"
    private String postParam = "Hello"; //Default String for POST: "Hello"

    public String getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(String threadCount) {
        this.threadCount = threadCount;
    }

    public String getIterations() { return iterations; }

    public void setIterations(String iterations) {
        this.iterations = iterations;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() { return ip; }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() { return port; }

    public String getPostParm() {
        return postParam;
    }

    public void setPostParm(String postParm) {
        this.postParam = postParm;
    }

    public String buildUrl() {
        StringBuffer sb = new StringBuffer();
        return sb.append("http://").append(ip).append(':').append(port).append(myPath).toString();
    }

    public MyClient(Client client) {
        this.client = client;
    }

    public void assignWebTarget() {
        webTarget = client.target(buildUrl());
    }

    public <T> T postText(Object requestEntity, Class<T> responseType) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).post(javax.ws.rs.client.Entity.entity(requestEntity,
                javax.ws.rs.core.MediaType.TEXT_PLAIN), responseType);
    }

    public String getStatus() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public static void printToCsvFormat() {
        for(int i = 0; i < Result.getStartTimeList().size(); ++i) {
            System.out.println(Result.getStartTimeList().get(i) + "," + Result.getLatencyList().get(i));
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < Integer.parseInt(iterations); ++i) {
            /**
             * GET: To test GET requests individually, please comment the
             * below POST.
             */
            Result.incrementRequestSent();
            long startTimeGet = System.currentTimeMillis();
            Result.addStartTime(startTimeGet);
            if(getStatus().equals("alive")) {
                Result.incrementRequestSuccess();
                System.out.println("GET: alive");
            }
            long endTimeGet = System.currentTimeMillis();
            Result.addLatency(endTimeGet - startTimeGet);

            /**
             * POST: To test POST requests individually, please comment the
             * above GET.
             */
            long startTimePost = System.currentTimeMillis();
            Result.addStartTime(startTimePost);
            Result.incrementRequestSent();
            if(Integer.parseInt(postText(getPostParm(), String.class)) == (getPostParm().length())) {
                Result.incrementRequestSuccess();
                System.out.println("POST(Hello): " + getPostParm().length());
            }
            long endTimePost = System.currentTimeMillis();
            Result.addLatency(endTimePost - startTimePost);
        }
    }
    static class Result {
        private static AtomicInteger requestSent = new AtomicInteger(0);
        private static AtomicInteger requestSuccess = new AtomicInteger(0);
        private static CopyOnWriteArrayList<Long> startTimeList = new CopyOnWriteArrayList<>();
        private static CopyOnWriteArrayList<Long> latencyList = new CopyOnWriteArrayList<>();
        private static OptionalDouble mean;
        private static double median;

        public static CopyOnWriteArrayList<Long> getStartTimeList() {
            return startTimeList;
        }

        public static CopyOnWriteArrayList<Long> getLatencyList() {
            return latencyList;
        }

        public static OptionalDouble getMean() {
            return mean;
        }

        public static double getMedian() {
            return median;
        }

        public static double calculatePercentile(int val) {
            return latencyList.get((int) ((latencyList.size() / 100) * (100 - val)));
        }

        public static void sortList() {
            Object[] arr = latencyList.toArray();
            Arrays.sort(arr);
            for(int i = 0; i < latencyList.size(); ++i) {
                latencyList.set(i, (Long) arr[i]);
            }
        }

        public static void calculateMean() {
            if(latencyList == null || latencyList.size() == 0) throw new NoSuchElementException("Mean = 0");
            else mean = latencyList.stream().mapToLong(a->a).average();
        }

        public static void calculateMedian() {
            if(latencyList == null || latencyList.size() == 0) return;
            sortList();
            int size = latencyList.size();
            if(size % 2 == 0) median = latencyList.get(size / 2);
            else median = (latencyList.get(size / 2) + latencyList.get((size / 2) + 1)) / 2;
        }

        public static void incrementRequestSent() {
            requestSent.addAndGet(1);
        }

        public static void incrementRequestSuccess() {
            requestSuccess.addAndGet(1);
        }

        public static int getRequestSent() {
            return requestSent.get();
        }

        public static int getRequestSuccess() {
            return requestSuccess.get();
        }

        public static void addLatency(long latency) {
            latencyList.add(latency);
        }

        public static void addStartTime(long startTime) {
            startTimeList.add(startTime);
        }
    }
}
