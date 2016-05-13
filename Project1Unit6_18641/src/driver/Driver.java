package driver;

import adapter.BuildAuto;
import adapter.DatabaseAuto;
import util.DatabaseIO;

/**
 * @author-Rui Wang rw1
 */
public class Driver {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Test Database connection");
        DatabaseIO databaseIO = new DatabaseIO();
        // First part: test creat a data base and add information to database.
        System.out.println("---------------------------------------------");
        System.out.println("           ***Test create Database***        ");
        System.out.println("---------------------------------------------");
        databaseIO.creatDatabase();
        
        System.out.println("***Test update Database***");
        DatabaseAuto databaseAuto = new BuildAuto();
        databaseAuto.buildDBAuto("Model.txt");
        System.out.println("Model is added database to data successfully.");
        databaseAuto.buildDBAuto("Model2.txt");
        System.out.println("Model is added database to data successfully.");
        databaseAuto.buildDBAuto("Model3.txt");
        System.out.println("Model is added database to data successfully.");
        databaseAuto.buildDBAuto("Model4.txt");
        System.out.println("Model is added database to data successfully.");
        databaseAuto.buildDBAuto("Model5.txt");
        System.out.println("Model is added database to data successfully.");
        
        System.out.println("Test add Database");
        System.out.println();
        
        System.out.println("Result of Automobile database");
        System.out.println();
        databaseIO.printDBAuto();
        System.out.println();
        
        System.out.println("Result of Automobile optionset database");
        System.out.println();
        databaseIO.printDBOptionSet();
        
        System.out.println("Result of Automobile options database");
        System.out.println();
        databaseIO.printDBOptions();
        System.out.println();
        System.out.println("Add database data successfully.");
        System.out.println();
        
        // Part two: delete automobile in a database
        System.out.println("---------------------------------------------");
        System.out.println("***Test delete a automobile in database***");
        System.out.println("---------------------------------------------");
        databaseAuto.deleteDBAuto("Test Model1");
        databaseIO.printDBAuto();
        
        // Part three: update automobile in a database
        System.out.println("---------------------------------------------");
        System.out.println("***Test update a automobile in database***");
        System.out.println("---------------------------------------------");
        databaseAuto.updateDBAutoPrice("Test Model2", 1314);
        databaseIO.printDBAuto();
        System.out.println();
    }
    
}
