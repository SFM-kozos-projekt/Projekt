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
public interface DataDAO extends AutoCloseable {
    public void saveData(Data d);
    /*
        Menti a foglalás adatait.
    */
    public void deleteData(Data d);
    /*
        Törli az adott foglalást.
    */
    public void updateData(Data d); 
    /*
        Frissíti az adott foglalást.
    */
    public List<Data> getData();
    /*
        Egy listába lementi a híváskor a Rents táblában lévő foglalásokat.
    */
}
