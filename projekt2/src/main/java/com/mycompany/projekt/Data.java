package com.mycompany.projekt;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gabor
 */
@Entity
@Table(name = "Rents")
public class Data {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private String phone;
    private String e_mail;
    private LocalDate rentStart;
    private LocalDate rentEnd;
    private String note;
    private int car_id;

    
    //Megjegyzéssel
    public Data(int car_id, String name, String phone, String e_mail, LocalDate rentStart, LocalDate rentEnd, String note) {
        this.car_id = car_id;
        this.name = name;
        this.phone = phone;
        this.e_mail = e_mail;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.note = note;
        
    }
    //Megjegyzés nélkül. Ilyenkor a táblában null értéket kap az oszlop
    public Data(int car_id, String name, String phone, String e_mail, LocalDate rentStart, LocalDate rentEnd) {
        this.car_id = car_id;
        this.name = name;
        this.phone = phone;
        this.e_mail = e_mail;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        
    }
    //paraméter nélküli konstruktor
    Data() {
    }
    
    //Getterek és Setterek
    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
    public LocalDate getRentStart() {
        return rentStart;
    }

    public void setRentStart(LocalDate rentStart) {
        this.rentStart = rentStart;
    }

    public LocalDate getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(LocalDate rentEnd) {
        this.rentEnd = rentEnd;
    }

}
