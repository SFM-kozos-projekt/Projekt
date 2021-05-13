package hu.unideb.inf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.h2.tools.Server;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;





public class FXMLController implements Initializable {
    static Map<Integer,List<String>> kocsi = new TreeMap<>();
    CarDAO kocsik_adat = new JPACarDAO();
    List<Car> kocsi_lista = kocsik_adat.getCar();
    static DataDAO ddao = new JPADataDAO();
    List<Data> rents = ddao.getData();
    boolean siker = Feltolt(rents);
   
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
    @FXML
    void ajanlatKereseButton(ActionEvent event) {
        List<Data> rents = ddao.getData();
        Collections.sort(rents, Comparator.comparing(Data::getCar_id).thenComparing(Data::getRentStart));
        boolean siker = Feltolt(rents);

        LocalDate date1 = firstDate.getValue();
        LocalDate date2 = lastDate.getValue();
        
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
        CheckRent cr = new CheckRent();
        if(cr.isRentable(valasztott_auto.getId(), date1, date2)){
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
    }
     
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        var valasztas = valasztek.getValue();
        int index = -1;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).equals(valasztas))
            {
                index = i;
            }
        }
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
                msg.append("        ");
            }
            idopontLabel.setText(msg.toString());
            counter++;
        }
        CheckRent cr = new CheckRent();
        List<String> otherDate = cr.idopontListazas(index);
        StringBuilder msg1 = new StringBuilder();
        for (int i = 0; i < otherDate.size(); i++) {           
            msg1.append(otherDate.get(i));
            msg1.append("\n");
            idopontLabel1.setText(msg1.toString());
        }
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
    }   
    private static Server server = new Server();
    private static void startDatabase() throws SQLException {
        server.runTool("-tcp", "-web", "-ifNotExists");
    } 
    private static void stopDataBase() throws SQLException{
        server.shutdown();
    }
}