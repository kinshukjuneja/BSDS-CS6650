package edu.neu.client;

import edu.neu.client.model.RFIDLiftData;
import edu.neu.client.statistics.Result;
import edu.neu.client.threads.PostMultiThread;
import edu.neu.client.threads.ReadMultiThread;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SkierClient {
    private Client client; //To be initialized when creating an instance of this class
    private WebTarget webTarget; //Created after ip and port is given at terminal
    private String numOfThreads; //Default: 160
    private String ip; //AWS: "35.161.211.30"
    private String port; //Default: "8080"
    private static final String MY_PATH = "/assignment-3/webapi/"; //assignment-3/webapi/
    private int partition;
    private static final String GET_REQUEST = "GET";
    private static final String POST_REQUEST = "POST";

    public SkierClient(Client client) {
        this.client = client;
    }

    public void setNumOfThreads(String numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    public String getNumOfThreads() {
        return numOfThreads;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getPartition() {
        return partition;
    }

    public String buildURI() {
        StringBuffer sb = new StringBuffer();
        return sb.append("http://").append(getIp()).append(':').append(getPort()).append(MY_PATH).toString();
    }

    public void assignWebTarget() {
        webTarget = client.target(buildURI());
    }

    public void startRequest(SkierClient skierClient, Result result, String requestType) {
        ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(numOfThreads));
        for (int i = 0; i < Integer.valueOf(numOfThreads); ++i) {
            if (requestType.equals(POST_REQUEST)) {
                executor.submit(new PostMultiThread(i * skierClient.getPartition(), (i + 1) * skierClient.getPartition(), skierClient, result));
            } else if (requestType.equals(GET_REQUEST)) {
                executor.submit(new ReadMultiThread(i * skierClient.getPartition(), (i + 1) * skierClient.getPartition(), skierClient, result, 1));
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        client.close();
    }

    public int postData(RFIDLiftData requestEntity, Result postResult) throws ClientErrorException {
        long startPostTime = System.currentTimeMillis();
        postResult.addStartTime(startPostTime);
        postResult.incrementRequestSent();
        long requestStartTimestamp = System.currentTimeMillis();
        postResult.addLatency(requestStartTimestamp);
        Response response = webTarget.path("load").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(Entity.json(requestEntity));
        postResult.addLatency(System.currentTimeMillis() - startPostTime);
        postResult.incrementRequestSuccess();
        long requestEndTimestamp = System.currentTimeMillis();
        postResult.addLatency(requestEndTimestamp - requestStartTimestamp);
        return response.getStatus();
    }

    public String getData(int skierId, int dayNum, Result getResult) throws ClientErrorException {
        long startGetTime = System.currentTimeMillis();
        getResult.addStartTime(startGetTime);
        getResult.incrementRequestSent();
        long requestStartTimestamp = System.currentTimeMillis();
        getResult.addLatency(requestStartTimestamp);
        Response response = webTarget.path("myvert/" + skierId + "/" + dayNum).request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(Response.class);
        getResult.addLatency(System.currentTimeMillis() - startGetTime);
        getResult.incrementRequestSuccess();
        long requestEndTimestamp = System.currentTimeMillis();
        getResult.addLatency(requestEndTimestamp - requestStartTimestamp);
        return response.readEntity(String.class);
    }

    public void closeClient() {
        client.close();
    }
}
