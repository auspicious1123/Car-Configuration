package util;

import java.sql.*;
import java.util.ArrayList;


import model.Automobile;
import model.OptionSet.Option;

/**
 * @author-Rui Wang rw1
 */
public class DatabaseIO {
    private static final String URL = "jdbc:mysql://localhost:3306?useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";

    // used for checking whether connect to database at the beginning.
    public void testConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if (connection != null) {
                System.out.println("Connect to Database successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connect failed");

        } finally {

        }
    }

    // create a database
    public void creatDatabase() {
        Statement statement = null;
        
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
           
                if (connection != null) {
                    System.out.println("Connect to Database successfully");
                }
                statement = connection.createStatement();
                String command = null;
                try {
                    command = "DROP DATABASE AutomobileDB;";
                    statement.executeUpdate(command);
                    System.out.println("Successfully delete datebase");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // operation for creating a database
                String[] setupCommands = { 
                        "CREATE DATABASE AutomobileDB;", 
                        "USE AutomobileDB;", 
                        "CREATE TABLE Automobile(AutoId INT NOT NULL, AutoName varchar(255) NOT NULL, BasePrice INT NOT NULL);",
                        "CREATE TABLE OptionSet(OptionSetId INT NOT NULL, OptionSetName varchar(255) NOT NULL, AutoId INT NOT NULL);",
                        "CREATE TABLE Options(OptionId INT NOT NULL,OptionName varchar(255) NOT NULL,OptionPrice INT NOT NULL,OptionSetId INT NOT NULL,AutoID INT NOT NULL);"
                };
                
                for (int i = 0; i < setupCommands.length; i++) {
                    System.out.println(setupCommands[i]);
                    statement.executeUpdate(setupCommands[i]);
                }
                System.out.println("Database created, table created.");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    // add a data into the database
    public int[] addToDatabase(Automobile automobile, int automobileNum, int optionSetIDBegin, int optionIDBegin) {
        int[] aIDs = new int[2];
        int optionSetID = optionSetIDBegin;
        int optionID = optionIDBegin;
        
        ArrayList<Option> options = null;
        String[] optionSet = { "Color", "Transmission", "Brakes/Traction Control", "Side Impact Air Bags",
        "Power Moonroof" };
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if(connection != null) {
                System.out.println("Connect to Database successfully");
            }
            
            String addAutoInfor = "INSERT INTO AutomobileDB.Automobile VALUES (?,?,?);";
            
            // insert data information to automobile table
            PreparedStatement preparedStatement = connection.prepareStatement(addAutoInfor);
            preparedStatement.setInt(1, automobileNum);
            preparedStatement.setString(2, automobile.getName());
            preparedStatement.setInt(3, automobile.getBasePrice());
            preparedStatement.executeUpdate();
            
            String addOptionSetInfor = "INSERT INTO AutomobileDB.OptionSet VALUES (?,?,?);";
            
            for(int i = 0; i < optionSet.length; i++) {
                preparedStatement = connection.prepareStatement(addOptionSetInfor);
                preparedStatement.setInt(1, optionSetID);
                preparedStatement.setString(2, optionSet[i]);
                preparedStatement.setInt(3, automobileNum);
                preparedStatement.executeUpdate();
                
                String addOptionInfor = "INSERT INTO AutomobileDB.Options VALUES (?,?,?,?,?);"; 
//                System.out.println(optionSetName[i]);
                options = automobile.getOptions(optionSet[i]);
                
                for(Option option : options) {
                    preparedStatement = connection.prepareStatement(addOptionInfor);
                    preparedStatement.setInt(1, optionID);
                    preparedStatement.setString(2, option.getName());
                    preparedStatement.setInt(3,  option.getPrice());
                    preparedStatement.setInt(4, optionSetID);
                    preparedStatement.setInt(5, automobileNum);
                    preparedStatement.executeUpdate();
                    optionID++;
                }
                optionSetID++;
            }
            
          //update the new begin optionset and option
            aIDs[0] = optionSetID;
            aIDs[1] = optionID;
            System.out.println("Add automobile:" + automobile.getName() + " to database");
            preparedStatement.close();
            
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return aIDs;
    }
    
    // delete an automobile in a database
    public void deleteAutomobileInDatabase(String AutomobileName) {
        Connection connection = null;
        ResultSet result;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if(connection != null) {
                System.out.println("Connect to Database successfully");
            }
            // find automobile in database
            String infor = "SELECT AutoId FROM AutomobileDB.Automobile WHERE (AutoName=?);";
            PreparedStatement preparedStatement = connection.prepareStatement(infor);
            preparedStatement.setString(1, AutomobileName);
            result = preparedStatement.executeQuery();
            result.next();
            int AutoId = Integer.parseInt(result.getString("AutoId"));
            
            // delete a automobile through delete all of its tables
            // 1. delete option in database
            String deleteOptionInfor = "DELETE FROM AutomobileDB.Options WHERE (AutoId=?);";
            preparedStatement = connection.prepareStatement(deleteOptionInfor);
            preparedStatement.setInt(1, AutoId);
            preparedStatement.executeUpdate();
            System.out.println("Successful delete options in : " + AutomobileName);
            
            // 2. delete optoin set in database
            String deleteOptionSetInfor = "DELETE FROM AutomobileDB.OptionSet WHERE (AutoId=?);";
            preparedStatement = connection.prepareStatement(deleteOptionSetInfor);
            preparedStatement.setInt(1, AutoId);
            preparedStatement.executeUpdate();
            System.out.println("Successful delete option sets in: " + AutomobileName);
            
            // 3. delete automobile in database
            String deleteAutomobileInfor = "DELETE FROM AutomobileDB.Automobile WHERE (AutoId=?);";
            preparedStatement = connection.prepareStatement(deleteAutomobileInfor);
            preparedStatement.setInt(1, AutoId);
            preparedStatement.executeUpdate();
            System.out.println("Successful delete automobile: " + AutomobileName);
            
            preparedStatement.close();
            connection.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // update the data in the database
    public void updateAutomobileBasePrice(String automibleName, int newBasePrice) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            if(connection != null) {
                System.out.println("Connect to Database successfully");
            }
            String infor = "UPDATE AutomobileDB.Automobile SET BasePrice =? WHERE (AutoName=?);";
            PreparedStatement preparedStatement = connection.prepareStatement(infor);
            preparedStatement.setInt(1, newBasePrice);
            preparedStatement.setString(2, automibleName);
            preparedStatement.executeUpdate();
            System.out.println("Successfully update " + automibleName + " base price");
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) { 
            System.out.println("Not found " + automibleName + " in database");
        }
    }
    
    // print out automobile information
    public void printDBAuto() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            
            // choose out the automobile through automobile name
            String infor = "SELECT * FROM AutomobileDB.Automobile;";  
            statement = connection.createStatement();
            result = statement.executeQuery(infor);
            System.out.format("%20s%40s%20s\n", "Automobile Number", "Automobile Name", "BasePrice");
            while(result.next()) {
                System.out.format("%20s%40s%20s\n", result.getString("AutoId"), result.getString("AutoName"), result.getString("BasePrice"));
            }
            System.out.println();
           
            statement.close();
            result.close();
            connection.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    
    public void printDBOptionSet() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            
            // choose out the automobile option set through automobile name
            String infor = "SELECT * FROM AutomobileDB.OptionSet;";
            statement = connection.createStatement();
            result = statement.executeQuery(infor);
            System.out.format("%20s%40s%20s\n", "Option Set Number", "Option Set Name", "Automobile ID");
            while(result.next()) {
                System.out.format("%20s%40s%20s\n", result.getString("OptionSetId"), result.getString("OptionSetName"), result.getString("AutoID"));
            }
            System.out.println();
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public  void printDBOptions() {
        Connection connection = null;
        Statement statement = null;
        ResultSet result;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
            // choose out the automobile options through automobile name
            String infor = "SELECT * FROM AutomobileDB.Options;";
            statement = connection.createStatement();
            result = statement.executeQuery(infor);
            System.out.format("%10s%40s%20s%15s%20s\n", "Option Number", "Option Name", "Option Price", "Option Set ID", "Auto Number");
            while(result.next()) {
                System.out.format("%10s%40s%20s%15s%20s\n", result.getString("OptionId"), result.getString("OptionName"), 
                        result.getString("OptionPrice"), result.getString("OptionSetId"), result.getString("AutoId"));
            }
            System.out.println();
            statement.close();
            result.close();
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
