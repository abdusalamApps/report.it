package report.it.models;

public class Project {

    private int id;
    private String name;
    private String administrator;

    public Project() {

    }

    public Project(String name) {
        this.name = name;
      //  this.administrator = administrator;
    }

    public Project(int id,String name, String administrator){
        this.id=id;
        this.name = name;
        this.administrator = administrator;
    }

    public void setAdministrator(String administrator){
        this.administrator = administrator;
    }
    public int getId(){ return id;}
    public String getName() {
        return name;
    }
    public String getAdministrator() { return administrator; }
}
