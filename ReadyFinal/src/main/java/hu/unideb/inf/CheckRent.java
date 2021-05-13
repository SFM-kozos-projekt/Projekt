/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import static hu.unideb.inf.FXMLController.Feltolt;
import static hu.unideb.inf.FXMLController.ddao;
import static hu.unideb.inf.FXMLController.kocsi;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author gabor
 */
public class CheckRent implements ForTheRent {
    
    public boolean isRentable(int car, LocalDate szam1, LocalDate szam2)
    {
        if(szam2.isBefore(szam1))
        {
            return false;
        }

        if(kocsi.containsKey(car))
        {
            if (kocsi.get(car).size() == 0)
            {
                return true;
            }

            String[] tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");
            if (LocalDate.parse(tmp[1]).isBefore(szam1))
            {
                return true;
            }

            tmp = kocsi.get(car).get(0).split("/");
            if(szam1.isAfter(LocalDate.now().minusDays(1)) && szam2.isBefore(LocalDate.parse(tmp[0])))
            {
                return true;
            }

            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");
                String[] sor1 = kocsi.get(car).get(i + 1).split("/");

                if (LocalDate.parse(sor[1]).isBefore(szam1) && LocalDate.parse(sor1[0]).isAfter(szam2))
                {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> idopontListazas(int car) {
            List<Data> rents = ddao.getData();
            Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
            boolean siker = Feltolt(rents);
            List<String> otherDate = new ArrayList<>();
            
            if(kocsi.get(car).size() == 0){
                otherDate.add("Erre a kocsira nem történt foglalás!");
            } else{
                
            String[] tmp = kocsi.get(car).get(0).split("/");

            if(LocalDate.now().plusDays(1).isBefore(LocalDate.parse(tmp[0])))
            {
                otherDate.add(LocalDate.now() + "/" + LocalDate.parse(tmp[0]).minusDays(1));
            }
            CheckRent cr = new CheckRent();
            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");
                String[] sor1 = kocsi.get(car).get(i + 1).split("/");

                if(cr.isRentable(car, LocalDate.parse(sor[1]).plusDays(1), LocalDate.parse(sor1[0]).minusDays(1)))
                    otherDate.add(LocalDate.parse(sor[1]).plusDays(1) + "/" + LocalDate.parse(sor1[0]).minusDays(1));
            }

            tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");

            otherDate.add(LocalDate.parse(tmp[1]).plusDays(1) + "-tol minden nap");
            }
            
            return otherDate;
    } 
}
