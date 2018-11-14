package edu.uconn.cse2102.project.common;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Hospital
{
    public static List<Hospital> load()
    {
        Reader reader = new BufferedReader(
            new InputStreamReader(
                    Hospital.class.getResourceAsStream("/HGI.csv")
            )
        );

        CsvToBean<Hospital> csvToBean = new CsvToBeanBuilder<Hospital>(reader)
            .withType(Hospital.class)
            .withIgnoreLeadingWhiteSpace(true)
            .build();

        List<Hospital> hospitals = csvToBean.parse();
        return hospitals;
    }

    @CsvBindByName(column = "Provider Id", required = true)
    private String id;

    @CsvBindByName(column = "Hospital Name", required = true)
    private String name;

    @CsvBindByName(column = "Address", required = true)
    private String address;

    @CsvBindByName(column = "City", required = true)
    private String city;

    @CsvBindByName(column = "State", required = true)
    private String state;

    @CsvBindByName(column = "ZIP Code", required = true)
    private String zipcode;

    @CsvBindByName(column = "Emergency Services", required = true)
    private String ems;

    @CsvBindByName(column = "Meets criteria for meaningful use of EHRs", required = false)
    private String ehr;

    // Getters
    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCity()
    {
        return city;
    }

    public String getState()
    {
        return state;
    }

    public String getZipCode()
    {
        return zipcode;
    }

    public boolean hasEmergencyServices()
    {
        return ems.equals("Yes");
    }

    public boolean hasEHR()
    {
        return ehr != null && ehr.equals("Y");
    }

    public String getFullAddress()
    {
        return this.address + ", " + this.city + ", " + this.state + " " + this.zipcode;
    }

    // Setters
    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setZipCode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public void setEms(String ems)
    {
        this.ems = ems;
    }

    public void setEhr(String ehr)
    {
        this.ehr = ehr;
    }
}
