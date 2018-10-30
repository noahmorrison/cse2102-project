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

    // Getters
    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
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
}
