package com.mycompany.projekt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.h2.tools.Server;

public class Application {

    public static void main(String[] args) throws Exception {
        startDatabase();
        
        //Foglalás adatainak felvétele alap konsttruktorral. Az autó id-ját később adom hozzá, mind két példa foglalásnál. Egyébként obj.setCar_id(id).
        //A car_id-t mindenképp meg kell adni az összekapcsolás miatt, másképp hibára fut a program.
        Data foglal = new Data();
        foglal.setName("Kala Pal");
        foglal.setPhone("06704569874");
        foglal.setE_mail("kalap@uto.hu");
        LocalDate start = LocalDate.parse("2021-05-10");
        foglal.setRentStart(start);
        LocalDate end = LocalDate.parse("2021-05-20");
        foglal.setRentEnd(end);
        foglal.setNote("abcd");
        //constrctor: car_id name, phone, e-mail, rentstart, rentend, note
        Data foglal2 = new Data(6, "Mekk Elek", "06701234567", "melek@kecske.hu", LocalDate.parse("2021-05-05"), LocalDate.parse("2021-05-10"), "10 ora");//constrctor: name, phone, e-mail, rentstart, rentend, note
        /*
        //Autók adatainak felvétele objektumokba.
        //Csak akkor kell levenni a kommentelést, ha először futtatod a gépen. A foglalások mentését ki kell kommentelni, mert másképp nem tölti fel (nem tudom miért...). 
        Car car = new Car("Audi", "A4", "Fekete", "Dizel", 5, 5, 7.0, 5000);
        Car car2 = new Car("Opel", "Zafira B", "Ezust", "Benzin", 7, 5, 9.4, 6500);
        Car car3 = new Car("Citroen", "C1 1.0", "Piros", "Benzin",  4, 5, 4.6, 4000);
        Car car4 = new Car("Ford", "Focus III", "Ezust", "Benzin", 5, 5, 5.9, 5500);
        Car car5 = new Car("Audi", "A6", "Sotetkek", "Dizel", 5, 5, 7.15, 6000);
        Car car6 = new Car("Chevrolet", "Aveo II", "Kek", "Benzin", 5, 4, 6.6, 4500);
        Car car7 = new Car("Daewoo", "Tosca", "Feher", "Benzin", 5, 4, 9.3, 5000);
        Car car8 = new Car("Fiat", "Punto 2012", "Ezust", "Dizel", 5, 5, 3.5, 4000);
        Car car9 = new Car("Honda", "Civic XI", "Fekete", "Dizel", 5, 5, 4.4, 5000);
        //Autó objektumok mentése az adatnázisba
        try (CarDAO cdao = new JPACarDAO();) {
            cdao.saveCar(car);
            cdao.saveCar(car2);
            cdao.saveCar(car3);
            cdao.saveCar(car4);
            cdao.saveCar(car5);
            cdao.saveCar(car6);
            cdao.saveCar(car7);
            cdao.saveCar(car8);
            cdao.saveCar(car9);
        }*/
        
        //Autó id-jának hozzárendelése
       foglal.setCar_id(3);
       try (DataDAO ddao = new JPADataDAO();){
            ddao.saveData(foglal);
            ddao.saveData(foglal2);
       }
       CarDAO cdao = new JPACarDAO();
       List<Car> cars = cdao.getCar();
       DataDAO ddao = new JPADataDAO();
       List<Data> rents = ddao.getData();
       
        System.out.println("Open your browser and navigate to http://localhost:8082/");
        System.out.println("JDBC URL: jdbc:h2:file:./kolcsonzes");
        System.out.println("User Name: sa");
        System.out.println("Password: ");
        
        System.out.println("");
        System.out.println("Cars:");
        for(Car item : cars){
            String id = String.valueOf(item.getId());
            String seats = String.valueOf(item.getSeats());
            String doors = String.valueOf(item.getDoors());
            String consumption = String.valueOf(item.getConsumption());
            String price = String.valueOf(item.getPrice());
            System.out.println(id +  ";" + item.getBrand() + ";" + item.getModel() + ";" + item.getColor() + ";" + item.getFuel() + ";" + seats + ";" + doors + ";" + consumption + ";" + price);
        }
        
        System.out.println("");
        System.out.println("Rents:");
        for(Data item : rents){
            String id = String.valueOf(item.getId());
            String rentstart = String.valueOf(item.getRentStart());
            String rentend = String.valueOf(item.getRentEnd());
            String car_id = String.valueOf(item.getCar_id());
            System.out.println(id + ";" + car_id + ";" + item.getName() + ";" + item.getPhone() + ";" + item.getE_mail() + ";" + rentstart + ";" + rentend + ";" + item.getNote());
            
        }
    }

    private static void startDatabase() throws SQLException {
        new Server().runTool("-tcp", "-web", "-ifNotExists");
    }   
}
