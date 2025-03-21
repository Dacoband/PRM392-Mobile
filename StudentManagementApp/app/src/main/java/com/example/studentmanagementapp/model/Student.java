package com.example.studentmanagementapp.model;

public class Student {
    private int id;
    private String name;
    private String date;
    private String gender;
    private String email;
    private String address;
    private String phone;
    private int idMajor;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getIdMajor() { return idMajor; }
    public void setIdMajor(int idMajor) { this.idMajor = idMajor; }
}
