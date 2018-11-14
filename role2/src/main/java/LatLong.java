package edu.uconn.cse2102.project.role2;

import edu.uconn.cse2102.project.common.Hospital;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class LatLong
{
    private static double[] getCache(String street, String city, String state)
        throws IOException
    {
        if (city == null) city = "N/A";
        if (state == null) state = "N/A";

        CSVReader reader;
        try
        {
            reader = new CSVReader(new FileReader("lat-long-cache.csv"));
        }
        catch (FileNotFoundException e)
        {
            return null;
        }

        String[] line;
        while ((line = reader.readNext()) != null)
        {
            if (!line[0].toUpperCase().equals(street.toUpperCase())) continue;
            if (!line[1].toUpperCase().equals(city.toUpperCase())) continue;
            if (!line[2].toUpperCase().equals(state.toUpperCase())) continue;

            return new double[]
            {
                Double.parseDouble(line[3]),
                Double.parseDouble(line[4])
            };
        }

        return null;
    }

    private static void setCache(String street, String city, String state, double lat, double lon)
        throws IOException
    {
        if (city == null) city = "N/A";
        if (state == null) state = "N/A";

        CSVWriter writer = new CSVWriter(new FileWriter("lat-long-cache.csv", true));
        writer.writeNext(new String[]
        {
            street, city, state,
            String.valueOf(lat), String.valueOf(lon)
        });

        writer.close();
    }

    private static Map[] parse(String url)
    {
        try
        {
            System.out.println("Going to: " + url);
            Map[] results = new ObjectMapper().readValue(new URL(url), Map[].class);
            if (results.length == 0) return null;
            return results;
        }
        catch (MalformedURLException e)
        {
            System.out.println("Failed to parse as url: " + url);
            return null;
        }
        catch (IOException e)
        {
            System.out.println("Failed to read json response!");
            return null;
        }
    }

    private static long LAST_CALL = -1;
    public static double[] get(String street, String city, String state, String name, String zipcode)
    {
        state = Util.expandState(state);
        double[] latlong = null;
        try
        {
            latlong = getCache(street, city, state);
            if (latlong != null) return latlong;
        }
        catch (IOException e)
        {
            System.out.println("Failed to get cache for: " + street + ", " + city + ", " + state);
        }

        while (LAST_CALL != -1 && (System.currentTimeMillis() - LAST_CALL) < 1000)
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
            }
        }
        LAST_CALL = System.currentTimeMillis();

        String url = "https://nominatim.openstreetmap.org/search?format=json&limit=1";
        url += "&email=" + encode("noah.morrison@uconn.edu");
        String alturl1 = url;
        String alturl2 = url;

        url += "&street=" + encode(street);
        url += "&city=" + encode(city);
        url += "&state=" + encode(state);

        if (name != null) alturl1 += "&q=" + encode(name + ", " + state);
        if (zipcode != null) alturl2 += "&q=" + encode(street + ", " + state + ", " + zipcode);

        Map[] results = parse(url);
        if (results == null && name != null)
        {
            results = parse(alturl1);
        }
        if (results == null && zipcode != null)
        {
            results = parse(alturl2);
        }
        if (results == null) return null;

        latlong = new double[]
        {
            Double.parseDouble(results[0].get("lat").toString()),
            Double.parseDouble(results[0].get("lon").toString())
        };

        try
        {
            setCache(street, city, state, latlong[0], latlong[1]);
        }
        catch (IOException e)
        {
            System.out.println("Failed to set cache for: " + street + ", " + city + ", " + state);
        }

        return latlong;
    }

    public static double[] get(String street, String city, String state)
    {
        return get(street, city, state, null, null);
    }

    public static double[] get(Hospital hospital)
    {
        return get(
            hospital.getAddress(),
            hospital.getCity(),
            hospital.getState(),
            hospital.getName(),
            hospital.getZipCode()
        );
    }

    private static String encode(String data)
    {
        try
        {
            return URLEncoder.encode(data, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }
}
