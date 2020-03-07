package report.it.models;

import java.sql.Timestamp;

public class TimeReport {

    private int id;
    private String submitted;
    private int minutes_sum;
    private boolean signed;
    private int projectID;
    private String projectName;
    private String username;
    private int week;


    public TimeReport() {

    }

    public TimeReport(int id, String submitted, int minutes_sum, boolean signed,int projectID, String projectName, String username,int week) {
        this.id = id;
        this.submitted = submitted;
        this.minutes_sum = minutes_sum;
        this.signed = signed;
        this.projectID=projectID;
        this.projectName = projectName;
        this.username = username;
        this.week = week;
    }

    public int getId(){
        return id;
    };
    public String getSubmitted(){
        return submitted;
    };
    public int getMinutes_sum(){
        return minutes_sum;
    }
    public  boolean getSigned(){
        return signed;
    }
    public  String getProjectName(){
        return projectName;
    }
    public String getUsername(){
        return username;
    }
    public int getProjectID(){return projectID;}

    public int getWeek(){ return week; }

}
