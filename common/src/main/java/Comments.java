package edu.uconn.cse2102.project.common;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Comments
{
    
    
    
    
    public static List<Comments> load()
    {
        Reader reader = new BufferedReader(
            new InputStreamReader(
                    Comments.class.getResourceAsStream("/comments.csv")
            )
        );
        
        CsvToBean<Comments> csvToBean = new CsvToBeanBuilder<Comments>(reader)
            .withType(Comments.class)
            .withIgnoreLeadingWhiteSpace(true)
            .build();

        List<Comments> comments = csvToBean.parse();
        return comments;
    }

    @CsvBindByName(column = "ID", required = true)
    private String id;

    @CsvBindByName(column = "Full_Name", required = true)
    private String name;
    
    @CsvBindByName(column = "SS", required = true)
    private String ss;
    
    
    
    @CsvBindByName(column = "Comment", required=true)
    private String comment;
    
    @CsvBindByName(column = "Time", required=true)
    private String time;
    
   
    // Getters
    public String getId()
    {
        return id;
    }
    
    public String getFull_Name(){
        return name;
    }
    
    public String getSS(){
        return ss;
    }
    public String getComment(){
        return comment;
    }
    public String getTime(){
        return time;
    }    
    
    

    // Setters
    public void setId(String id)
    {
        this.id = id;
    }

    public void setFull_Name(String name)
    {
        this.name = name;
    }
    public void setSS(String state){
        this.ss = ss;
    }
  
    public void setComment(String comment){
        this.comment = comment;
    }
    public void setTime(String time){
        this.time = time;
    }
    
    public Comments(String id, String name, String SS, String comment, String Time){
        this.id = id;
        this.name = name;
        this.ss = SS;
        this.time = Time;
        this.comment = comment;
        
    
    }
    
}
