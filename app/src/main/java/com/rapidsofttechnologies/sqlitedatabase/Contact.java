package com.rapidsofttechnologies.sqlitedatabase;

import java.io.Serializable;

/**
 * Created by AND-18 on 9/24/2015.
 */
public class Contact implements Serializable {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String city;

    public Contact(){}
    public void setId(int id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public void setEmail (String email){
        this.email=email;
    }

    public void setCity(String city){
        this.city=city;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public String getCity(){
        return city;
    }

}
