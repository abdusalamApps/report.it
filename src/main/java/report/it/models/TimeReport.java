package report.it.models;

import java.sql.Timestamp;

public class TimeReport {

    private int id;
    private Timestamp submitted;
    private int minutes_sum;
    private boolean signed;
    private int projectId;
    private String username;
    private int week;

    public TimeReport() {

    }

    public TimeReport(int id, Timestamp submitted, int minutes_sum, boolean signed, int projectId, String username,int week) {
        this.id = id;
        this.submitted = submitted;
        this.minutes_sum = minutes_sum;
        this.signed = signed;
        this.projectId = projectId;
        this.username = username;
        this.week = week;
    }

    public int getId(){
        return id;
    };
    public Timestamp getSubmitted(){
        return submitted;
    };
    public int getMinutes_sum(){
        return minutes_sum;
    }
    public  boolean getSigned(){
        return signed;
    }
    public  int getProjectId(){
        return projectId;
    }
    public String getUsername(){
        return username;
    }

    public int getWeek(){
        return week;
    }

}
