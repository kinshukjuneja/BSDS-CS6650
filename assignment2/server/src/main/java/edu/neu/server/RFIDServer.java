package edu.neu.server;

import edu.neu.server.dao.SkierDataDao;
import edu.neu.server.model.SkierData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class RFIDServer {
    private final SkierDataDao skierDataDao = SkierDataDao.getInstance();
    private final int[] VERTICAL_VALUES = {200,300,400,500};

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "APPLICATION_JSON" media type.
     *
     * @return SkierData object that will be returned as a APPLICATION_JSON response.
     */
    @Path("myvert/{skierId}/{dayNum}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getData(@PathParam("skierId") int skierID, @PathParam("dayNum") int dayNum) throws SQLException {
        return skierDataDao.getDataBySkierIdAndDay(skierID, dayNum);
    }

    @Path("load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postData(SkierData data) throws SQLException {
        //skierDataDao.insert(data); // fast test
        // Uncomment for actual test
        int verticalMetres = calculateVertical(data.getLiftID());
        skierDataDao.insert(data, verticalMetres);

    }
    //
    public int calculateVertical(int liftID) {
        if(liftID >= 1 && liftID <= 10) return VERTICAL_VALUES[0];
        else if(liftID >= 11 && liftID <= 20) return VERTICAL_VALUES[1];
        else if(liftID >= 21 && liftID <= 30) return VERTICAL_VALUES[2];
        else if(liftID >= 31 && liftID <= 40) return VERTICAL_VALUES[3];
        return 0;
    }
}