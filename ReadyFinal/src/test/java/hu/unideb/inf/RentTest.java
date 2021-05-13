/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import static hu.unideb.inf.FXMLController.Feltolt;
//import static hu.unideb.inf.FXMLController.isRentable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gabor
 */
public class RentTest {
    
    CheckRent tmp = null;
    
    @BeforeEach
    public void setUp() {
        tmp = new CheckRent();
    }
    
    public RentTest() {
    }
    
    
    @BeforeAll
    public static void setUpClass() {
        
    }
   
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    
    
    @AfterEach
    public void tearDown() {
    }

    @ParameterizedTest
    @CsvSource(
            {"2,2021-05-13/2021-05-19,true",
            "3,2021-05-14/2021-05-20,true",
            "3,2021-05-28/2021-05-29,true",
            "3,2021-05-29/2021-05-29,true",
            "9,2021-05-28/2020-05-30,false",
            "6,2021-05-14/2021-05-14,true",
            "6,2021-05-13/2021-05-13,true",
            "9,2021-05-29/2021-06-14,false",
            "9,2021-05-11/2021-05-24,false",
            "1,2021-05-19/2021-05-23,false"})
    void TestIsRentable(int car, String RentDate, boolean expected){
        DataDAO ddao = new JPADataDAO();
        CarDAO cdao = new JPACarDAO();
        Map<Integer, List<String>> kocsi = new TreeMap<>();
        List<Data> rents = ddao.getData();
        Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
        boolean siker = Feltolt(rents);
        boolean actual;
        String[] token = RentDate.split("/");
        LocalDate start = LocalDate.parse(token[0]);
        LocalDate end = LocalDate.parse(token[1]);
        actual = tmp.isRentable(car,start, end);
        assertEquals(actual, expected);
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
