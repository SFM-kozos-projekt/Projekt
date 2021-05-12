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
    static Map<Integer,List<String>> kocsi = new TreeMap<>();
    CarDAO kocsik_adat = new JPACarDAO();
    List<Car> kocsi_lista = kocsik_adat.getCar();
    static DataDAO ddao = new JPADataDAO();
    List<Data> rents = ddao.getData();
    boolean siker = Feltolt(rents);
    //Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
    
    
    @FXML
    private Label label;
    
    @FXML
    private DatePicker firstDate;
    
    @FXML
    private DatePicker lastDate;
    
    @FXML
    private Label foglalasAllapota;
    
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
     
    ObservableList<String> list = FXCollections.observableArrayList("Audi A4", "Opel", "Citroen", "Ford", "Audi A6", "Chevrolet", "Daewoo", "Fiat", "Honda");
    
    @FXML
    private ComboBox<String> valasztek2;
    
     @FXML
    private ComboBox<String> melyikAuto;
    
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
    
    @FXML
    private Label idopontLabel;
    
    @FXML
    public Label idopontLabel1;

    
    
    
    
    //-------------------------------------------------------------------------
    
   public static boolean Feltolt(List<Data> rents){
       for(int i = 0; i < rents.size(); i++){
           if(rents.get(i).getRentEnd().isBefore(LocalDate.now())){
               ddao.deleteData(rents.get(i));
           }
       }
       
       for (int i = 1; i < 10 ; i++) {
           kocsi.put(i, new ArrayList<>());
       }
        for(Data item : rents){
            String rstart = String.valueOf(item.getRentStart());
            String rend = String.valueOf(item.getRentEnd());
            String rent = rstart + "/" + rend;
            kocsi.get(item.getCar_id()).add(rent);
        }
        return true;
    }
   
   
    //globális map, amely tartalmazza a kocsik foglalásait a car_id szerint.
    
    
    //boolean siker = Feltolt(rents);
     
public static boolean isRentable(int car, LocalDate szam1, LocalDate szam2)
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

    public static List<String> idopontListazas(int car){
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

            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");
                String[] sor1 = kocsi.get(car).get(i + 1).split("/");

                if(isRentable(car, LocalDate.parse(sor[1]).plusDays(1), LocalDate.parse(sor1[0]).minusDays(1)))
                    otherDate.add(LocalDate.parse(sor[1]).plusDays(1) + "/" + LocalDate.parse(sor1[0]).minusDays(1));
            }

            tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");

            otherDate.add(LocalDate.parse(tmp[1]).plusDays(1) + "-tol minden nap");
            }
            
            return otherDate;
            
            //System.out.println("Ez a jarmu elerheto: " + otherDate.toString());
    }
    
    //a kocsi elérhetőségét vizsgáló függvény
    /*public static boolean isAvailable(int car, LocalDate szam1, LocalDate szam2)
    {
        if(szam2.isBefore(szam1))
        {
            return false;
        }
        // temporary lista változó ami hozzá lesz adva a maphez
        List<String> lista = new ArrayList<>();

        // ha van ilyen kocsi
        if(kocsi.containsKey(car))
        {
            //ha nincs egyetlen időpont foglalás sem a kocsira
            if (kocsi.get(car).size() == 0)
            {
                //időpont hozzáadasa a listához rentStart/rentEnd formában
                lista.add(szam1 + "/" + szam2);
                //időpont hozzáadása a kiválasztott kocsihoz
                kocsi.put(car,lista);
                System.out.println("A kocsi foglalas sikeres.");
                return true;
            }

            //legkésőbbi foglalt dátum
            String[] tmp = kocsi.get(car).get(kocsi.get(car).size() - 1).split("/");

            //ha a legkésőbb foglalt időpont vége a mostani foglalt időpont eleje előtt van
            if (LocalDate.parse(tmp[1]).isBefore(szam1))
            {
                lista = kocsi.get(car);
                lista.add(szam1 + "/" + szam2);
                kocsi.put(car, lista);
                System.out.println("A kocsi foglalas sikeres.");
                return true;
            }

            //ciklus végigmegy az összes lefoglalt időponton
            for (int i = 0; i < kocsi.get(car).size() - 1; i++)
            {
                String[] sor = kocsi.get(car).get(i).split("/");      //i - edik időpont
                String[] sor1 = kocsi.get(car).get(i + 1).split("/"); //i+1 - edik időpont

                //elérhető-e a kocsi a megadott időpontban
                if (LocalDate.parse(sor[1]).isBefore(szam1) && LocalDate.parse(sor1[0]).isAfter(szam2))
                {
                    lista = kocsi.get(car);
                    // az elérhető időpotok közé szúrja az új időpontot tehát rendezve lesz
                    lista.add(i + 1,szam1 + "/" + szam2);
                    kocsi.put(car,lista);
                    System.out.println("A kocsi foglalas sikeres.");
                    return true;
                }
            }
            System.out.println("A kocsi a megadott idoszakban foglalt.");
            
            return false;
        }
        
        System.out.println("A kocsi foglalas sikertelen. Nincs ilyen jarmu");
        return false;
    }*/
      
    @FXML
    void ajanlatKereseButton(ActionEvent event) {
        List<Data> rents = ddao.getData();
        Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
        boolean siker = Feltolt(rents);
        System.out.println("Kiras");
        for(Map.Entry<Integer, List<String>> par : kocsi.entrySet()){
            System.out.println("irtam");
            System.out.println(par.getKey() + " ; " + String.join(",", par.getValue()));
        }
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
        
        System.out.println("Index: " + index);
        if(isRentable(valasztott_auto.getId(), date1, date2)){
            if(megjegyzesText.getText() != null){
                Data uj = new Data(valasztott_auto.getId(), nevText.getText(), telefonszamText.getText(), emailText.getText(), date1, date2, megjegyzesText.getText());
                rents.add(uj);
                ddao.saveData(uj);
                rents = ddao.getData();
                kocsi = new TreeMap<>();
                Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
                boolean sikeres = Feltolt(rents);
           }
            else if(megjegyzesText.getText() == null){
                Data uj = new Data(valasztott_auto.getId(), nevText.getText(), telefonszamText.getText(), emailText.getText(), date1, date2);
                rents.add(uj);
                ddao.saveData(uj);
                rents = ddao.getData();
                kocsi = new TreeMap<>();
                Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
                boolean sikeres = Feltolt(rents);
            }
            foglalasAllapota.setText("Sikeres foglalás");
        }else{
        foglalasAllapota.setText("Sikertelen foglalás");
        }
        /*for(Data item : rents){
            System.out.println(String.valueOf(item.getRentStart()) + " ; " + String.valueOf(item.getCar_id()) + " ; " + String.valueOf(item.getRentEnd()));
        }*/
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
    
    @FXML
    void handleBetoltes(ActionEvent event) {
        List<Data> rents = ddao.getData();
        Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
        boolean siker = Feltolt(rents);
        idopontLabel.setText("");
        idopontLabel1.setText("");
        var valasztas = melyikAuto.getValue();
        //System.out.println(valasztas);
        
        int index = -1;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equals(valasztas))
            {
                index = i;
            }
        }
        
        index++;
        
        StringBuilder msg = new StringBuilder();
        if(kocsi.get(index).size() == 0){
            msg.append("Ez az autó még nem volt lefoglalva.\n");
            idopontLabel.setText(msg.toString());
        }
        int counter = 1;
        for (int i = 0; i < kocsi.get(index).size(); i++) {          
            msg.append(kocsi.get(index).get(i));
            if (counter%2 == 0)
            {
                msg.append("\n");
            }else{
                msg.append("    ");
            }//this is the new line you need
            idopontLabel.setText(msg.toString());
            counter++;
        }
        
        List<String> otherDate = idopontListazas(index);
        StringBuilder msg1 = new StringBuilder();
        
        for (int i = 0; i < otherDate.size(); i++) {           
            msg1.append(otherDate.get(i));
            msg1.append("\n");  //this is the new line you need
            idopontLabel1.setText(msg1.toString());
        }
        
        
        /*if(kocsi.get(index).isEmpty()){
            idopontLabel.setText("Erre az autóra még nincs foglalt időpont!");
        }else{
            for(int j=0; j < kocsi.get(index).size(); j++){
            idopontLabel.setText(kocsi.get(index).get(j));
        }
        }*/
            
        
        
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
        melyikAuto.setItems(list);
        // TODO
    }   
    
    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    } 

}
