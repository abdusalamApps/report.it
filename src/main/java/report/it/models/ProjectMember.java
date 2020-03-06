package report.it.models;

public class ProjectMember {
    private String username;
    private String projectName;
    private int role;

    public ProjectMember(){

    }
    public ProjectMember(String username,String projectName,int role){

        this.username=username;
        this.projectName=projectName;
        this.role=role;
    }

    public String getUsername(){
        return username;
    }

    public String getProjectName(){
        return projectName;
    }

    public int getRole(){
        return role;
    }
}
