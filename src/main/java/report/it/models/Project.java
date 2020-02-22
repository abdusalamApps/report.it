package report.it.models;

public class Project {

    private String name;
    private String administrator;

    public Project() {

    }

    public Project(String name, String administrator) {
        this.name = name;
        this.administrator = administrator;
    }

    public String getName() {
        return name;
    }

    public String getAdministrator() {
        return administrator;
    }
}
