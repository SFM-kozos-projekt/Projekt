package hu.unideb.inf;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.h2.tools.Server;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.Comparator;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.h2.tools.Server;

public class FXMLController implements Initializable {
    
    CarDAO kocsik_adat = new JPACarDAO();
    List<Car> kocsi_lista = kocsik_adat.getCar();
    DataDAO ddao = new JPADataDAO();
    List<Data> rents = ddao.getData();
    
    
    @FXML
    private Label label;
    
    @FXML
    private DatePicker firstDate;
    
    @FXML
    private DatePicker lastDate;
    
    @FXML
    private static Label foglalasAllapota;
    
    @FXML
    private TextField telefonszamText;
    
    @FXML
    private TextField nevText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField megjegyzesText;
    
    @FXML
    private ComboBox<String> valasztek;
     
    ObservableList<String> list = FXCollections.observableArrayList("Audi1", "Opel", "Citroen", "Ford", "Audi2", "Chevrolet", "Daewoo", "Fiat", "Honda");
    
    @FXML
    private ComboBox<String> valasztek2;
    
    @FXML
    private Label marka_kiir;

    @FXML
    private Label modell_kiir;

    @FXML
    private Label szin_kiir;

    @FXML
    private Label ulesek_kiir;

    @FXML
    private Label ajtok_kiir;

    @FXML
    private Label fogyasztas_kiir;

    @FXML
    private Label ar_kiir;

    
    
    
    
    //-------------------------------------------------------------------------
    
    public static boolean Feltolt(List<Data> rents){
        for(Data item : rents){
            if(!kocsi.containsKey(item.getCar_id())){
                kocsi.put(item.getCar_id(), new ArrayList<>());
            }
            String rstart = String.valueOf(item.getRentStart());
            String rend = String.valueOf(item.getRentEnd());
            String rent = rstart + "/" + rend;
            kocsi.get(item.getCar_id()).add(rent);
        }
        return true;
    }
    
    static Map<Integer,List<String>> kocsi = new TreeMap<>();
    boolean siker = Feltolt(rents);
    
    /*public static boolean isRentable(int car, LocalDate szam1, LocalDate szam2)
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
    }*/
    
    /*public static boolean isAvailable(int car, LocalDate szam1, LocalDate szam2)
    {
        if(szam2.isBefore(szam1))
        {
            return false;
        }
        // temporary lista v??ltoz?? ami hozz?? lesz adva a maphez
        List<String> lista = new ArrayList<>();

        // ha van ilyen kocsi
        if(kocsi.containsKey(car))
        {
            //ha nincs egyetlen id??pont foglal??s sem a kocsira
            if (kocsi.get(car).size() == 0)
            {
                //id??pont hozz??adasa a list??hoz rentStart/rentEnd form??ban
                lista.add(szam1 + "/" + szam2);
                //id??pont hozz??ad??sa a kiv??lasztott kocsihoz
                kocsi.put(car,lista);
                //System.out.println("A kocsi foglalas sikeres.");
                return true;
            }

            //legk??s??bbi foglalt d??tum
            String[] tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");

            //ha a legk??s??bb foglalt id??pont v??ge a mostani foglalt id??pont eleje el??tt van
            if (LocalDate.parse(tmp[1]).isBefore(szam1))
            {
                lista = kocsi.get(car);
                lista.add(szam1 + "/" + szam2);
                kocsi.put(car, lista);
                //System.out.println("A kocsi foglalas sikeres.");
                return true;
            }

            //ciklus v??gigmegy az ??sszes lefoglalt id??ponton
            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");      //i - edik id??pont
                String[] sor1 = kocsi.get(car).get(i + 1).split("/"); //i+1 - edik id??pont

                //el??rhet??-e a kocsi a megadott id??pontban
                if (LocalDate.parse(sor[1]).isBefore(szam1) && LocalDate.parse(sor1[0]).isAfter(szam2))
                {
                    lista = kocsi.get(car);
                    // az el??rhet?? id??potok k??z?? sz??rja az ??j id??pontot teh??t rendezve lesz
                    lista.add(i + 1,szam1 + "/" + szam2);
                    kocsi.put(car,lista);
                    //System.out.println("A kocsi foglalas sikeres.");
                    return true;
                }
            }
            //System.out.println("A kocsi a megadott idoszakban foglalt.");
            int counter = 0;
            List<Integer> otherCars = new ArrayList<>();
            List<String> otherDate = new ArrayList<>();

            tmp = kocsi.get(car).get(0).split("/");

            if(LocalDate.now().plusDays(1).isBefore(LocalDate.parse(tmp[0])))
            {
                otherDate.add(LocalDate.now() + "/" + LocalDate.parse(tmp[0]).minusDays(1));
            }

            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");
                String[] sor1 = kocsi.get(car).get(i + 1).split("/");

                if(isRentable(car, LocalDate.parse(sor[1]).plusDays(1), LocalDate.parse(sor1[0]).minusDays(1)))
                    otherDate.add(LocalDate.parse(sor[1]).plusDays(1) + "/" + LocalDate.parse(sor1[0]).minusDays(1));
            }

            tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");

            otherDate.add(LocalDate.parse(tmp[1]).plusDays(1) + "-tol minden nap");

            //System.out.println("Ez a jarmu elerheto: " + otherDate.toString());

            for(Map.Entry<Integer, List<String>> i : kocsi.entrySet())
            {
                if(isRentable(i.getKey(), szam1,szam2))
                {
                    counter++;
                    otherCars.add(i.getKey());
                }
            }
            if(counter == 0)
            {
                foglalasAllapota.setText("Nincs egyeb elerheto jarmu ebben az idopontban.");
            }
            /*else
            {
                System.out.print("Egyeb elerheto jarmuvek ebben az idopontban: ");
                System.out.println(otherCars.toString());
            }*/
            /*return false;
        }
        
        //System.out.println("A kocsi foglalas sikertelen. Nincs ilyen jarmu");
        return false;
    }*/
      
    @FXML
    void ajanlatKereseButton(ActionEvent event) {
        System.out.println("Kattintottal!");
        LocalDate date1 = firstDate.getValue(); // 2021-05-24
        LocalDate date2 = lastDate.getValue(); // 2021-05-25
                
        System.out.println(date1);
        System.out.println(date2);
        
        Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
        
        var valasztas = valasztek2.getValue();
        
        int index = -1;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equals(valasztas))
            {
                index = i;
            }
        }
        
        Car valasztott_auto = kocsi_lista.get(index);
        
        //if(isAvailable(valasztott_auto.getId(), date1, date2))
        //{        
            if(megjegyzesText.getText() != null){
                Data uj = new Data(valasztott_auto.getId(), nevText.getText(), telefonszamText.getText(), emailText.getText(), date1, date2, megjegyzesText.getText());
                rents.add(uj);
                ddao.saveData(uj);
                kocsi = new TreeMap<>();
                boolean sikeres = Feltolt(rents);
            }
            else if(megjegyzesText.getText() == null){
                Data uj = new Data(valasztott_auto.getId(), nevText.getText(), telefonszamText.getText(), emailText.getText(), date1, date2);
                rents.add(uj);
                ddao.saveData(uj);
                kocsi = new TreeMap<>();
                boolean sikeres = Feltolt(rents);
            }
        //}
    }
    
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //System.out.println("You clicked me!");
        var valasztas = valasztek.getValue();
        //System.out.println(valasztas);
        
        int index = -1;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equals(valasztas))
            {
                index = i;
            }
        }
        //System.out.println(index);
        
        Car cars = kocsi_lista.get(index);
        marka_kiir.setText(cars.getBrand());
        modell_kiir.setText(cars.getModel());
        szin_kiir.setText(cars.getColor());
        ulesek_kiir.setText(String.valueOf(cars.getSeats()));
        ajtok_kiir.setText(String.valueOf(cars.getDoors()));
        fogyasztas_kiir.setText(String.valueOf(cars.getConsumption()));
        ar_kiir.setText(String.valueOf(cars.getPrice()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            startDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        valasztek.setItems(list);
        valasztek2.setItems(list);
        // TODO
    }   
    
    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    } 
}
