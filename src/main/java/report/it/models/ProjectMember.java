package report.it.models;

public class ProjectMember {
    private String username;
    private String name;
    private String projectName;
    private String email;
    private int role;


    public ProjectMember(){

    }
    public ProjectMember(String username, String name, String projectName, String email,int role){

        this.username=username;
        this.name=name;
        this.projectName=projectName;
        this.email=email;
        this.role=role;


    }

    public String getUsername(){
        return username;
    }
    public String getName(){ return name; }

    public String getProjectName(){
        return projectName;
    }

    public String getEmail(){ return email; }

    public int getRole(){
        return role;
    }


}
