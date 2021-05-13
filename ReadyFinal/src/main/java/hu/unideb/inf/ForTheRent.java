/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author gabor
 */
public interface ForTheRent {
    public boolean isRentable(int car, LocalDate start, LocalDate end);
    public List<String> idopontListazas(int car);
    
    
}
