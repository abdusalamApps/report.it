package report.it.models;

public class Project {

    private int id;
    private String name;
    private int members;

    public Project() {

    }

    public Project(String name) {
        this.name = name;
    }

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project(String name, int members) {
        this.name = name;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
