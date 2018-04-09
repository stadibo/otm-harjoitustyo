/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dao.Database;
import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author peje
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File path = new File("db", "tracker.db");
        try {
            Database db = new Database(path);
            TrackerTextUi ui = new TrackerTextUi(scanner, db);
            ui.run();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Critical error. Contact creator for fix. Thank you.");
        }
        
    }

}
