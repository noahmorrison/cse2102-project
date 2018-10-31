package edu.uconn.cse2102.project.role2;

import edu.uconn.cse2102.project.common.Hospital;
import com.graphhopper.GraphHopper;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import java.util.List;
import java.net.URL;
import java.util.Locale;

public class Main
{
    public static void main(String[] args)
    {
        GraphHopper hopper = new GraphHopperOSM().forDesktop();
        URL url = Main.class.getResource("/map.osm.pbf");
        System.out.println(url.getFile());
        hopper.setDataReaderFile(url.getFile());
        hopper.setGraphHopperLocation("./graphfolder");
        hopper.setEncodingManager(new EncodingManager("car"));

        hopper.importOrLoad();

        double latFrom = 41.9660714;
        double lonFrom = -72.0496453;
        double latTo = 42.010186;
        double lonTo = -71.9250255;
        GHRequest req = new GHRequest(latFrom, lonFrom, latTo, lonTo)
            .setWeighting("fastest")
            .setVehicle("car")
            .setLocale(Locale.US);

        GHResponse rsp = hopper.route(req);
        PathWrapper path = rsp.getBest();
        System.out.printf("It takes %d minutes\n", path.getTime() / 1000 / 60);
    }
}
