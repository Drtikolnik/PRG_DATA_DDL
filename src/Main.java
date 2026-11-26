import java.sql.*;
import java.util.*;

void main() {
    Scanner sc = new Scanner(System.in);

    String url = "jdbc:mysql://localhost:3306/pujcovna_aut"; // URL databáze: server, port, název DB
    String username = "root"; // Uživatelské jméno
    String password = ""; // Heslo k databázi
    MetodyTabulek m =  new MetodyTabulek();

    //System.out.print("Zadej jméno hráče: ");
    //String jmenoZadane = sc.nextLine();
//
    //System.out.print("Zadej level: ");
    //int levelZadany = sc.nextInt();sc.nextLine();
//
    //System.out.print("Zadej zlato: ");
    //int zlatoZadane = sc.nextInt();sc.nextLine();


// Připojení k databázi
    try (Connection conn = DriverManager.getConnection(url, username, password)) {
        System.out.println("Připojení k databázi bylo úspěšné!");
// Výpis dat z tabulky+

        boolean pokracovat = true;
        while (pokracovat) {

            System.out.println("---MENU---");
            System.out.println("1 - Vytvořit databázi");
            System.out.println("2 - Přidat auto");
            System.out.println("3 - Najít auta");
            int menu = sc.nextInt();
            sc.nextLine();

            switch (menu) {
                case 1:
                    m.vytvoritAuta(conn);
                    break;

                case 2:
                    m.insertAuta(conn);
                    break;

                case 3:
                    m.hledatAutaKriteria(conn);
                    break;



                default:
                    break;

            }












        }



    } catch(
            SQLException e)

    {
        System.out.println(e);
        //System.err.println("Problém s připojením.");
    }

}