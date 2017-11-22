package edu.neu.client;

import edu.neu.client.statistics.Result;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkierMetrics {
    private Client client;
    private WebTarget webTarget;
    private List<String> metricsList;
    private String[] metric;

    public SkierMetrics() {
        this.client = ClientBuilder.newClient();
    }

    public WebTarget getWebTarget() {
        return webTarget;
    }

    public void setWebTarget(String baseURI) {
       webTarget = client.target(baseURI);
    }

    public void getMetrics(String requestType, Result result) {
        Response response = webTarget.path("metrics/" + requestType).request(MediaType.TEXT_PLAIN).get(Response.class);
        String output = response.readEntity(String.class);
        output = output.replace("[", "").replace("]", "");
        metricsList = new ArrayList<>(Arrays.asList(output.split(",")));
        for(String record: metricsList) {
            metric = record.split(":");
            result.addStartTime(Long.valueOf(metric[0].replace(" ", "")));
            result.addLatency(Long.valueOf(metric[1]));
        }
    }

    public void closeClient(SkierMetrics skierMetrics) {
        skierMetrics.client.close();
    }
}
