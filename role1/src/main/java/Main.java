package edu.uconn.cse2102.project.role1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.io.*;
import java.util.*;
import edu.uconn.cse2102.project.common.Hospital;
import edu.uconn.cse2102.project.common.Comments;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import static java.awt.SystemColor.menu;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

//import edu.uconn.cse2102.project.role1.Hospital;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class Main extends JFrame implements ActionListener
{
        //Initializers citizen objects
        String citizen;
        String citizen_ss;
        String citizen_comment;
        ArrayList<String> citizendata = new ArrayList<String>(3);
        
        
       
        
        //JLabels
        JLabel Title = new JLabel("Hospital General System");
        JLabel FullName_l = new JLabel("Enter your name");
        JLabel Social_Security_l = new JLabel("Enter your Social Security Number");
        
        JLabel title_c = new JLabel("Make a comment");
        JLabel title_thanks = new JLabel("Thank you for participating! You can exit this window ");
      
        //User fields
        JTextField FullName = new JTextField(50);
        JTextField Social_Security = new JTextField(50);
        JTextArea textbox_c = new JTextArea(10,10);
        
        
        
        

     
        //Legislator fields
        JLabel Welcome_back = new JLabel("Welcome back legislator!");
        JPasswordField keycode = new JPasswordField(50);
        
        //Frames
        JFrame frame = new JFrame("Hospital General System ");
        JFrame frame2 = new JFrame("(HGI ADMIN) Welcome Back legislator! ");
        
        
        JFrame frame3 = new JFrame("Comment Section");
        JFrame frame4 = new JFrame("Make a comment");
        JFrame frame5 = new JFrame("Thank you page");
        JFrame frame6 = new JFrame("Survey Data from the Public");
        
        //Survey frame
        JFrame frame7 = new JFrame("Taking the survey");
        
 
       //List of all Panels 
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel panel_l = new JPanel(new GridBagLayout());
        JPanel panel_comment = new JPanel(new GridBagLayout());
        JPanel panel_make_comment = new JPanel(new GridBagLayout());
        JPanel nav_container = new JPanel(new GridBagLayout());
        JPanel container_l = new  JPanel (new GridBagLayout());
        JPanel survey_panel = new JPanel(new GridBagLayout());
        
        
        
        JPanel panel_thank_you = new JPanel();
        JPanel panel_survey_ = new JPanel();
        
        //Buttons
        JButton Submit = new JButton("Confirm");
        JButton Submit_l = new JButton("Confirm");
        JButton Submit_make_comment = new JButton("Confirm");
        JButton Submit_survey = new JButton("Submit");
    
        
        //Buttons for the Legislator 
        JButton Comment_Section_btn = new JButton("Comments");
        JButton Logout = new JButton("Logout");
        
        JTextArea data = new JTextArea(512,512);
        
        
        //Menu Properties
       JMenuBar menuBar = new JMenuBar();
       JMenu menu = new JMenu("HGI");
       JMenu Comment_menu = new JMenu("Comments");
       JMenu Profile_menu = new JMenu("Profile");
       
       
       JMenuItem Loggout = new JMenuItem("Logout"); 
       JMenuItem comments = new JMenuItem("Comments");
       JMenuItem About = new JMenuItem("About");
       
       JMenuItem change_pass = new JMenuItem("Password Change");
       JMenuItem Edit_profile = new JMenuItem("Edit Profile");
       
       
       //Getting the time now 
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
       LocalDateTime now = LocalDateTime.now();  
          

                
        
 //Methods      
//Getting the basic information. 
public void user_start(){
        frame.setSize(1024, 1024);
        frame.setBackground(Color.GRAY);
      
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
        //Grid on the panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10,10,10,10);
        
        //add components to the panel 
        
        //adding in the title 
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(Title, constraints);
        
        //adding in the fullName
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(FullName_l, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(FullName, constraints);
        
        //adding in the social security
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(Social_Security_l, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(Social_Security, constraints);
        
        //adding in the button
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(Submit, constraints);
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
        
       
        panel.setBackground(Color.GRAY);
        panel.setSize(1024, 1024);
        
        Submit.addActionListener(this);
        frame.add(panel);      
      } 
//Frame legislator 
public void legislator_start(){
      Font font = new Font("Times New Roman", Font.BOLD, 36);
      Welcome_back.setFont(font);
                
        
     Welcome_back.setForeground(Color.BLACK);
        
    
      GridBagConstraints constraints = new GridBagConstraints();
      constraints.anchor = GridBagConstraints.WEST;
      constraints.insets = new Insets(10,10,10,10);
      
      //adding in components 
      constraints.gridx = 0;
      constraints.gridy = 0;
      panel_l.add(Welcome_back, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 1;
      panel_l.add(keycode, constraints);
      
      constraints.gridx = 0;
      constraints.gridy = 2;
      panel_l.add(Submit_l, constraints);
      
      Submit_l.addActionListener(this);
      
      add(panel_l);
      setLocationRelativeTo(null);
      pack();
      
      
      frame2.add(panel_l);
      
      
      frame2.setBackground(Color.DARK_GRAY);
      frame2.setSize(1024, 512);
      frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame2.setVisible(true);
      
      
      }
//User can make a comment 
public void make_comment_section() {
    
    frame4.setSize(1024, 1024);
    frame4.setVisible(true);
    frame4.setDefaultCloseOperation(EXIT_ON_CLOSE);
         
          
    GridBagConstraints constraints = new GridBagConstraints();
    
    constraints.anchor = GridBagConstraints.WEST;
    constraints.insets = new Insets(10,10,10,10);
    
    //adding in components
    constraints.gridx = 0;
    constraints.gridy = 0;
    panel_make_comment.add(title_c, constraints);
    
    constraints.gridx = 0;
    constraints.gridy = 1;
    panel_make_comment.add(textbox_c, constraints);
    
   
    constraints.gridx = 0;
    constraints.gridy = 2;
    panel_make_comment.add(Submit_make_comment, constraints);
    
  
    
    add(panel_make_comment);
    
    setLocationRelativeTo(null);
    Submit_make_comment.addActionListener(this);
    
    
    frame4.add(panel_make_comment);
      
   
    }
//Button actions Performed 
public void actionPerformed(ActionEvent e){
          if(e.getSource() == Submit_l){
          char[] legislator_password = keycode.getPassword();
          char[] c_pass = {'1','2','3','4'};
          if(Arrays.equals(legislator_password,c_pass))
          {
              RunSurveydata_();
              frame2.setVisible(false);       
          }else{
              System.out.print("Fail!");     
          }
          }
          if(e.getSource() == Submit){
              citizen = FullName.getText();
              citizen_ss = Social_Security.getText();
              
              
              
              
              
              System.out.print("Success!");
             
              make_comment_section();
              
              frame.setVisible(false);
          }
          if(e.getSource() == Submit_make_comment){
              citizen_comment = textbox_c.getText();
              
              
              List<Comments> csvComments = new ArrayList<Comments>();
           System.out.print(csvComments);
    
    String COMMA_DELIMITER = ",";
    String NEW_LINE_SEPARATOR = "\n";
    String FILE_HEADER = "ID,Full_Name,SS,Comment,Time";
    try {
                 csvComments.add(new Comments("test", "test","test", "test","test"));
                 csvComments.add(new Comments("002", citizen,citizen_ss,citizen, dtf.format(now)));
                 FileWriter fileWriter = new FileWriter(String_Array_Sample);
                 fileWriter.append(FILE_HEADER);
                 
                 for(Comments c: csvComments){
                    fileWriter.append(NEW_LINE_SEPARATOR);
                    fileWriter.append(c.getId());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(c.getFull_Name());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(c.getSS());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(c.getComment());
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(c.getTime());
                    fileWriter.append(COMMA_DELIMITER);
                    
                 }
                    fileWriter.flush();
                    fileWriter.close();
                    System.out.println("Done");
                 
                
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
              
          System.out.print("Success");
          
          thank_you_page();
          
          frame4.setVisible(false);
          }
          
          if(e.getSource() == Loggout){
          
          frame6.setVisible(false);
          legislator_start();
          }
          if(e.getSource() == comments){
          start_comment_section();
          
          }
      }
//List of comments 
public void listofComments(){
    List<Comments> csvComments = Comments.load();
    
    
    for(Comments comm: csvComments){
        System.out.print("Id: " + "\n" +  comm.getId());
        System.out.print("Full_Name: " + "\n" +  comm. getFull_Name());
        System.out.print("SS:" + "\n" + comm.getSS());
        System.out.print("Comment:" + "\n" + comm.getComment());
        System.out.print("Time:" + "\n" + comm.getTime());
        
        
        
    }


}
public JPanel ListOfData(){
  
        JPanel Hospital_data = new JPanel();
        
        List<Hospital> csvHospitals = Hospital.load();

        for (Hospital hosp: csvHospitals)
        {
            JLabel id = new JLabel("Id: " +  "\n" + hosp.getId());
            Hospital_data.add(id);
            data.append("Id: "  + hosp.getId() +  "\n");
            System.out.println("Id: " + hosp.getId());
            
            JLabel name = new JLabel("Name: \n" + hosp.getName());
            Hospital_data.add(name);
            data.append("Name: "  + hosp.getName() +  "\n");
            System.out.println("Name: " + hosp.getName());
           
            /*JLabel County = new JLabel("County: " + hosp.getCounty());
            Hospital_data.add(County);
            System.out.println("County: " + hosp.getCounty());*/
            
            JLabel Experience = new JLabel("Experience:  "+  hosp.getExperience() +  "\n");
            Hospital_data.add(Experience);
            data.append("Experience:  " +  hosp.getExperience() +  "\n");
            System.out.println("Experience: " + hosp.getExperience());
            
            JLabel Mortality = new JLabel("Mortality: " + hosp.getMortality());
            Hospital_data.add(Mortality);
            data.append("Mortality: " + hosp.getMortality() +  "\n");
            System.out.println("Mortality: " + hosp.getMortality());
            
            JLabel Timeliness = new JLabel("Timelines:" + hosp.getTimeliness());
            Hospital_data.add(Timeliness);
            data.append("Timeliness: "  +  hosp.getTimeliness() +  "\n");
            System.out.println("Timeliness: " + hosp.getTimeliness());
            
            JLabel newLine = new JLabel("=================" );
            Hospital_data.add(newLine);
            data.append("\n" + "================="+ "\n");
            System.out.println("=================");
        }
        
    return Hospital_data;
}
private void start_comment_section() {
        
        frame3.setSize(1024, 1024);
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    }
public String String_Array_Sample = "../common/build/resources/main/comments.csv";
//Thank you page 
private void thank_you_page()   {
            
           
  
        //To change body of generated methods, choose Tools | Templates.
       frame5.setSize(1024, 1024);
       frame5.setBackground(Color.black);
       panel_thank_you.add(title_thanks);
       frame5.add(panel_thank_you);
       
       frame5.setVisible(true);
       frame5.setDefaultCloseOperation(EXIT_ON_CLOSE);
       citizendata.add(citizen);
       citizendata.add(citizen_ss);
       citizendata.add(citizen_comment);
       System.out.print(citizendata);
       
       
       
       
       
       
       
    }
//Run Survey Data, running to get all the input from the CVS This brings to the menu of the homescreen
private void RunSurveydata_(){
    //Menu 
       
       
       comments.addActionListener(this);
       Loggout.addActionListener(this);
       
       
       menuBar.add(menu);
       menuBar.add(Profile_menu);
       menuBar.add(Comment_menu);
       
       
       Comment_menu.add(comments);
       Profile_menu.add(Edit_profile);
       Profile_menu.add(change_pass);
       
       menu.add(About);
       menu.add(Loggout);
       
       
       frame6.setJMenuBar(menuBar);
       
       
    
    
    
    //We are going to customize the homepage 
    panel_survey_= ListOfData();
    JPanel Main = new JPanel();
    Main.setSize(1024, 1024);
    
    // frame6.add(legislator_customization_home_page());
    JScrollPane pane = new JScrollPane(data ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    frame6.add(pane);
    frame6.setSize(1024, 1024);
    frame6.setBackground(Color.white);
    frame6.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    frame6.setVisible(true);
    
    frame6.setLocationRelativeTo(null);
    }
public void TestCase(
        String Comment, 
        String URL, 
        String Data, 
        String User_Data,
        String Fields){
    int counter = 5;
    switch(counter) {
        case 1:
            if (Comment.length() > 150 ){
                System.out.print("Failure" + counter + "Check your system");
            
            }
        System.out.print("Case 1: Success");
        case 2:
            if(String_Array_Sample.isEmpty()){
                System.out.print("Failure" + counter + "Check your system");
            
            }
       System.out.print("Case 2: Success");
        case 3:
            if(Data.isEmpty()){
                System.out.print("Failure" + counter + "Check your system");
            
            }
        System.out.print("Case 3: Success");    
        case 4:
            if(User_Data.isEmpty()){
                System.out.print("Failure" + counter + "Check your system");
            
            }
        System.out.print("Case 4: Success");   
        case 5:
            if (Fields.isEmpty()){
                System.out.print("Failure" + counter + "Check your system");
            
            }
        System.out.print("Case 5: Success");   
        default:
            System.out.print("All success!");
            
    
    }
    
    
    System.out.print("Failure" + counter + "Check your system");
    
}


//Customization

    public static void main(String[] args)
    {
        
     Main test = new Main();
    
     test.make_comment_section();   
    }


}
