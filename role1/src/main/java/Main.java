package edu.uconn.cse2102.project.role1;

import edu.uconn.cse2102.project.common.Hospital;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        List<Hospital> csvHospitals = Hospital.load();

        for (Hospital hosp: csvHospitals)
        {
            System.out.println("Id: " + hosp.getId());
            System.out.println("Name: " + hosp.getName());
            System.out.println("==========");
        }
    }
}
