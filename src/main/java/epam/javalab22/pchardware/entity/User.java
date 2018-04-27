package epam.javalab22.pchardware.entity;

import java.util.List;

public class User {

    private String username;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String address;
    private List<String> listOfRoles;

    public User() {}

    public User(String username, String email, String password, String name, String lastName, String phoneNumber, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getListOfRoles() {
        return listOfRoles;
    }

    public void setListOfRoles(List<String> listOfRoles) {
        this.listOfRoles = listOfRoles;
    }
}
