package net.jack.jcairlines.handler;

import net.jack.jcairlines.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class PassengerHandler {

    Database database = new Database();
    HolidayPackageSelection holidayPackageSelection = new HolidayPackageSelection();

    private String idNumber;
    private String forename;
    private String surname;
    private String dob;
    private String email;
    private String password;
    private int amountOfPassengers;


    public void passengerRegister(Scanner scan, Database database) throws SQLException {
        System.out.println("Hello, are you already registered with us?");
        String registered = scan.nextLine();
        if (registered.equalsIgnoreCase("yes")) {
            idNumberCheck(database, scan);
        } else {
            System.out.println("What is your first name?");
            String userForename = scan.nextLine();
            setForename(userForename);
            System.out.println("What is your surname?");
            String userSurname = scan.nextLine();
            setSurname(userSurname);
            System.out.println("What is your Date of birth?");
            String userDob = scan.nextLine();
            setDob(userDob);
            System.out.println("Please enter an email address");
            String passengerEmail = scan.nextLine();
            setEmail(passengerEmail);
            System.out.println("Now enter your desired password for your account");
            String passengerPassword = scan.nextLine();
            setPassword(passengerPassword);
            System.out.println("Creating a unique ID number. You will use this to log in along with your email and password.");
            String userId = getRandomNumber();
            setIdNumber(userId);

            PreparedStatement ps = database.getConnection().prepareStatement("INSERT INTO useraccounts VALUES(?, ?, ?, ?, ?, ?);");
            ps.setString(1, idNumber);
            ps.setString(2, forename);
            ps.setString(3, surname);
            ps.setString(4, dob);
            ps.setString(5, email);
            ps.setString(6, password);
            ps.executeUpdate();

            PreparedStatement ps2 = database.getConnection().prepareStatement("INSERT INTO holidaypackage VALUES(?, ?, ?, ?);");
            ps2.setString(1, idNumber);

        }
    }

    public void idNumberCheck(Database database, Scanner scan) throws SQLException {

        for (int i = 0; i <= 3; i++) {
            System.out.println("Please enter your id number that was sent you by email");
            String userId = scan.nextLine();
            PreparedStatement ps = database.getConnection().prepareStatement("SELECT * FROM useraccounts WHERE IdNumber = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Now please enter your email address");
                emailCheck(database, scan);
                break;
            } else {
                System.out.println("We can't find you in our database.");
                System.out.println("You have " + (3 - i) + " attempts left.");
            }
            if (i == 3) {
                System.exit(0);
            }
        }
    }

    public void emailCheck(Database database, Scanner scan) throws SQLException {
        for (int i = 0; i <= 3; i++) {
            String userPassword = scan.nextLine();
            PreparedStatement ps = database.getConnection().prepareStatement("SELECT * FROM useraccounts WHERE EmailAddress = ?");
            ps.setString(1, userPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Now please enter your password");
                passwordCheck(database, scan);
                break;
            } else {
                System.out.println("We can't find you in our database.");
                System.out.println("You have " + (3 - i) + " attempts left.");
            }
            if (i == 3) {
                System.exit(0);
            }
        }
    }

    public void passwordCheck(Database database, Scanner scan) throws SQLException {

        for (int i = 0; i <= 3; i++) {
            String userPassword = scan.nextLine();
            PreparedStatement ps = database.getConnection().prepareStatement("SELECT * FROM useraccounts WHERE AccountPassword = ?");
            ps.setString(1, userPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                passengerTicketSystem(database, forename, scan);
                break;
            } else {
                System.out.println("We can't find you in our database.");
                System.out.println("You have " + (3 - i) + " attempts left.");
            }
            if (i == 3) {
                System.exit(0);
            }
        }
    }

    public void passengerTicketSystem(Database database, String forename, Scanner scan) throws SQLException {

        PreparedStatement ps = database.getConnection().prepareStatement("SELECT Forename FROM useraccounts WHERE Forename = ?");
        ps.setString(1, getForename());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String userForename = rs.getString("Forename");
            setForename(userForename);
        }


        System.out.println("Hi " + getForename() + ", How many persons are you looking to fly with?");
        int amountOfPassengers = scan.nextInt();
        PreparedStatement ps2 = database.getConnection().prepareStatement("INSERT INTO holidaypackage VALUES (?, ?, ?, ?);");
        ps2.setInt(4, amountOfPassengers);

    }

    public String getRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public String getIdNumber() {
        return this.idNumber;
    }

    public String getForename() {
        return this.forename;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getDob() {
        return this.dob;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public int getAmountOfPassengers() {
        return this.amountOfPassengers;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAmountOfPassengers(int amountOfPassengers) {
        this.amountOfPassengers = amountOfPassengers;
    }

}
