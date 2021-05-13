/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import java.util.List;

/**
 *
 * @author gabor
 */
public interface CarDAO extends AutoCloseable {
    public void saveCar(Car c);
    /*
        Menti az adatbázisba a paraméterként megkapott autó adatait.
    */
    public void deleteCar(Car c); 
    /*
        Törli a megkapott autót az adatbázisból.
    */
    public void updateCar(Car c);  
    /* 
        Frissíti a kapott autó adatait.
    */
    public List<Car> getCar();
    /*
        Listába menti a híváskor a Cars táblában lévő autókat.
    */
}
