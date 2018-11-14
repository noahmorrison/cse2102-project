package edu.uconn.cse2102.project.role2;

import com.graphhopper.GraphHopper;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import java.net.URL;
import java.util.Locale;

public class RouteLength
{
    private GraphHopper hopper;

    public RouteLength(String state)
    {
        hopper = new GraphHopperOSM().forDesktop();
        URL url = Main.class.getResource("/map." + state.toLowerCase().replace(" ", "-") + ".osm.pbf");
        System.out.println(url.getFile());
        hopper.setDataReaderFile(url.getFile());
        hopper.setGraphHopperLocation("./caches/" + state.toLowerCase() + "/");
        hopper.setEncodingManager(new EncodingManager("car"));

        hopper.importOrLoad();
    }

    public double getTime(double[] from, double[] to)
    {
        GHRequest req = new GHRequest(from[0], from[1], to[0], to[1])
            .setWeighting("fastest")
            .setVehicle("car")
            .setLocale(Locale.US);

        GHResponse rsp = hopper.route(req);
        PathWrapper path = rsp.getBest();
        return path.getTime() / 1000.0 / 60.0;
    }
}
