import java.sql.*;
import java.util.*;

public class MetodyTabulek {
    Scanner sc = new Scanner(System.in);

    public void vytvoritAuta(Connection con) {
        try {
            Statement stmt = con.createStatement();
            System.out.println("Připojení k databázi bylo úspěšné!");

            // Vytvoření nové tabulky
            String createTableSQL = """
            
            CREATE TABLE IF NOT EXISTS auta (
            id_auto INT AUTO_INCREMENT PRIMARY KEY,
            spz VARCHAR(100),
            model VARCHAR(100),
            datum_vyroby DATE,
            pocet_km DECIMAL(10,2))
            """;

            stmt.executeUpdate(createTableSQL);
            System.out.println("Tabulka byla vytvořena.");
        } catch (SQLException e) {
            System.err.println("Nastala chyba při práci s databází.");
            e.printStackTrace();
        }
    }


    public void hledatAutaKriteria(Connection con) {
        try {
            Statement stmt = con.createStatement();
            System.out.println("Připojení k databázi bylo úspěšné!");

            System.out.println("Hledat podle kriterií: (zmackni enter pro preskoceni kategorie)");
            System.out.print("model: ");
            String model = sc.nextLine();

            System.out.print("značka: ");
            String SPZ = sc.nextLine();
            sc.nextLine();

            System.out.print("max počet km: ");
            String km = sc.nextLine();


            String selectSQL = """
            SELECT *
            FROM auta
            WHERE 1=1
            """;

            List<Object> parametry = new ArrayList<>();

            if (!SPZ.isEmpty()) {
                selectSQL = selectSQL + (" AND znacka = ?");
                parametry.add(SPZ);
            }

            if (!model.isEmpty()) {
                selectSQL = selectSQL + (" AND model = ?");
                parametry.add(model);
            }

            if (!km.isEmpty()) {
                try {
                    double maxKm = Double.parseDouble(km);
                    selectSQL = selectSQL + (" AND pocet_km <= ?");
                    parametry.add(maxKm);
                } catch (NumberFormatException e) {
                    System.out.println("Špatný formát kilometrů. Kritérium bude ignorováno.");
                }
            }


                // Provedení dotazu
                try (PreparedStatement pstmt = con.prepareStatement(selectSQL.toString())) {
                    // Dosazení parametrů místo otazníků
                    for (int i = 0; i < parametry.size(); i++) {
                        pstmt.setObject(i + 1, parametry.get(i));
                    }

                    // Vypsání výsledků (místo executeUpdate() se pro SELECT používá executeQuery())
                    ResultSet rs = pstmt.executeQuery();
                    boolean nalezeno = false;

                    System.out.println("\nNalezená auta:");
                    System.out.println("---------------------------------------------------------------");
                    while (rs.next()) {
                        nalezeno = true;
                        System.out.printf("ID: %d | SPZ: %s | Značka: %s | Model: %s | Vyrobeno: %s | Najeto: %.2f km\n",
                                rs.getInt("id_auto"),
                                rs.getString("spz"),
                                rs.getString("znacka"),
                                rs.getString("model"),
                                rs.getDate("datum_vyroby"),
                                rs.getDouble("pocet_km"));
                    }
                    System.out.println("---------------------------------------------------------------");

                    if (!nalezeno) {
                        System.out.println("Žádná auta neodpovídají zadaným kritériím.");
                    }

                } catch (SQLException e) {
                    System.err.println("Nastala chyba při vyhledávání v databázi.");
                    e.printStackTrace();
                }




        } catch (SQLException e) {
            System.err.println("Nastala chyba při práci s databází.");
            e.printStackTrace();
        }
    }









}
