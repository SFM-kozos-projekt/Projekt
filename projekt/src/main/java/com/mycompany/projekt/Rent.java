import java.time.LocalDate;
import java.util.*;

//a végső adatbázisba kiírandó adatok
class Data {
    String name;
    String phone;
    String e_mail;
    String car;
    LocalDate rentStart;
    LocalDate rentEnd;
    String note;

//konstruktor az adatokhoz megjegyzéssel
    public Data(String name, String phone, String e_mail, String car, LocalDate rentStart, LocalDate rentEnd, String note)
    {
        this.name = name;
        this.phone = phone;
        this.e_mail = e_mail;
        this.car = car;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.note = note;
    }

//konstruktor megjegyzés nélkül
    public Data(String name, String phone, String e_mail, String car, LocalDate rentStart, LocalDate rentEnd)
    {
        this.name = name;
        this.phone = phone;
        this.e_mail = e_mail;
        this.car = car;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        if(note != null) {
            return name + "; " +
                    phone + "; " +
                    e_mail + "; " +
                    car + "; " +
                    rentStart + "; " +
                    rentEnd + "; " +
                    note;
        }else{
            return name + "; " +
                    phone + "; " +
                    e_mail + "; " +
                    car + "; " +
                    rentStart + "; " +
                    rentEnd;
        }
    }
}

public class Rent {
    //globális map a kocsikhoz(car, rentStart/rentEnd)
    static Map<String,List<String>> kocsi = new TreeMap<>();

    //a kocsi elérhetőségét vizsgáló függvény
    public static boolean isAvailable(String car, LocalDate szam1, LocalDate szam2)
    {
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
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Data> adat = new ArrayList<>(); // az adat lista ami az adatbázisban lesz

        // a while-ig csak teszt adatok a teszteléshez.
        // Ezeket az adatokat az adatbázisból fogja szedni, tehát később kellesz módosítani a kódon

        List<String> honda = new ArrayList<>();

        //!!!csak rendezve lehetnek az időpontok!!! ha egy hamarabbi időpont lesz egy későbbi után, hibás lesz
        honda.add("2021-04-24/2021-04-26");
        honda.add("2021-04-27/2021-04-30");
        honda.add("2021-05-05/2021-05-09");
        kocsi.put("Honda", honda); //egyenlőre csak a honda és a honda időpontjai vannak hozzáadva a kocsikhoz


        System.out.println("Add meg a kocsit és az időpont intervallumot! ;-al elválasztva");
        while(scan.hasNext())
        {
            String[] sor = scan.nextLine().split(";");
            if(isAvailable(sor[0], LocalDate.parse(sor[1]), LocalDate.parse(sor[2])))
            {
                System.out.println("Add meg az adataid!(név;telefon;e-mail;megjegyzés(opcionális)");
                String[] sor2 = scan.nextLine().split(";");
                if(sor2.length == 4) // ha van megjegyzés
                {
                    adat.add(new Data(sor2[0], sor2[1], sor2[2], sor[0], LocalDate.parse(sor[1]), LocalDate.parse(sor[2]), sor2[3]));
                }
                else if(sor2.length == 3) // ha nincs megjegyzés
                    adat.add(new Data(sor2[0], sor2[1], sor2[2], sor[0], LocalDate.parse(sor[1]), LocalDate.parse(sor[2])));
            }
        }

        //rendezi  név és tel. szerint, de lehet jobb lesz ha dátum szerint lesz rendezve
        adat.sort(Comparator.comparing(Data::getName).thenComparing(Data::getPhone));

        //kiírja az adatokat amik vannak
        for(Data i : adat)
        {
            System.out.println(i.toString());
        }
    }
}

//MÉG NEM TÖKÉLETES LEHETNEK BUGOK