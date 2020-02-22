package report.it.models;

public class User {

    private String username;
    private String name;
    private String password;
    private String email;
    private int role;
    private String project;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String name, String password, String email, int role, String project) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getRole() {
        return role;
    }

    public String getProject() {
        return project;
    }
}
