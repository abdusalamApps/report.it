package report.it.models;

import report.it.Constants;

public class ProjectMember {

    private String username;
    private String name;
    private String role;

    public ProjectMember(String username, String name, int role) {
        this.username = username;
        this.name = name;
        this.role = Constants.rolesMap.get(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
