package net.jack.jcairlines;


import net.jack.jcairlines.database.Database;
import net.jack.jcairlines.handler.PassengerHandler;

import java.sql.SQLException;
import java.util.Scanner;

public class JcAirlines {

    static Scanner scan = new Scanner(System.in);
    static Database database = new Database();
    static PassengerHandler passengerHandler = new PassengerHandler();
    static String forename;

    public static void main(String[] args) throws SQLException {
        try {
            database.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not connected");
        }

        passengerHandler.passengerRegister(scan, database);
        passengerHandler.idNumberCheck(database, scan);

    }


}
