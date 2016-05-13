package util;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exception.EnumExceptions;
import exception.Fix1to100;
import exception.MissingAutoPrice;
import exception.MissingFileName;
import exception.MissingOptionPrice;
import exception.MissingOptionSetName;
import exception.WrongInputFormat;
import model.Automobile;

/**
 * @author-Rui Wang rw1
 */
public class FileIO {
    public Automobile buildAuto(String filename){
        Automobile a1 = new Automobile();
        
        try {
            if(filename.length() <= 4 || (!filename.substring(filename.length() - 4).equals(".txt"))){
                throw new MissingFileName(EnumExceptions.MissingFileName.getErrno(), 
                        EnumExceptions.MissingFileName.toString());
             }
        } catch (MissingFileName e) {
            e.print();
            e.log(e.getErrno());
            Fix1to100 fix = new Fix1to100();
            filename = fix.fix(e.getErrno());
        }
        
        try {
            FileReader file = new FileReader(filename);
            BufferedReader buff = new BufferedReader(file);
            boolean eof = false;
            String autoName = buff.readLine();         //txt first-line automotive name
            String inputBasePrice = buff.readLine();    //txt second-line automotive baseName
//            System.out.println(inputBasePrice);
            try {
                if(inputBasePrice == null || inputBasePrice.equals("")) {
                    throw new MissingAutoPrice(EnumExceptions.MissingAutoPrice.getErrno(), 
                            EnumExceptions.MissingAutoPrice.toString());
                }
            } catch (MissingAutoPrice e) {
                e.print();
                Fix1to100 fix = new Fix1to100();
                //System.out.println(fix.fix3());
                //inputBasePrice = fix.fix3(); 
                inputBasePrice = fix.fix(e.getErrno());
                e.log(e.getErrno());
            }
            
            float autoBasePrice = Float.parseFloat(inputBasePrice);
            
            // following lines optionSet:option1 option2 option3 save into optionSet, each line is  an optionSet
            int num = 0;      // optionSet number
            int optionSetNum = 5;
            a1 = new Automobile(autoName, autoBasePrice, optionSetNum);
            while(!eof && num < optionSetNum) {
                String line = buff.readLine();
                // System.out.println(line);
                if(line == null)
                    eof = true;
                else {
                    if(!line.contains(":")) {
                        throw new WrongInputFormat(EnumExceptions.WrongInputFormat.getErrno(), 
                                EnumExceptions.WrongInputFormat.toString());
                    }
                    if(!line.contains(",")) {
                        throw new WrongInputFormat(EnumExceptions.WrongInputFormat.getErrno(),
                                EnumExceptions.WrongInputFormat.toString());
                    }
                    
                    String[] readin = line.split(":"); // split into optionSet 和 options String
                    if(readin.length != 2) {
                        throw new WrongInputFormat(EnumExceptions.WrongInputFormat.getErrno(), 
                                EnumExceptions.WrongInputFormat.toString());
                    }
                   // System.out.println(readin[0]);
                    try {
                        if(readin[0].equals("")) {
                            throw new MissingOptionSetName(EnumExceptions.MissingOptionSetName.getErrno(), 
                                    EnumExceptions.MissingOptionSetName.toString());
                        }
                    } catch (MissingOptionSetName e) {
                        e.print();
                        Fix1to100 fix = new Fix1to100();
                        e.log(e.getErrno());
                        readin[0] = fix.fix(e.getErrno());
                    } 
                    String[] str = readin[1].split(",");  // include option name*price
                    
                    a1.setOptionSet(num, readin[0], str.length);
                    //OptionSet optionSet = new OptionSet(readin[0], str.length);
                    for(int i = 0 ; i < str.length; i++){
//                        System.out.println(str[i]);
                        if(!str[i].contains("#")) {
                            throw new WrongInputFormat(EnumExceptions.WrongInputFormat.getErrno(), 
                                    EnumExceptions.WrongInputFormat.toString());
                        }
                        
                        String optionPrice;
                        String name = "Missing price option name";
                        try {
                            String[] optionAndPrice = str[i].split("#");  //first is option name, second is price
                            name = optionAndPrice[0];
//                            System.out.println(name);
                            if(optionAndPrice.length < 2) {
                                throw new MissingOptionPrice(EnumExceptions.MissingOptionPrice.getErrno(), 
                                        EnumExceptions.MissingAutoPrice.toString());
                            } else {
                                optionPrice = optionAndPrice[1];
                            }
                        } catch (MissingOptionPrice e) {
                            e.print();
                            Fix1to100 fix = new Fix1to100();
                            e.log(e.getErrno());
                            optionPrice = fix.fix(e.getErrno());
//                            System.out.println(optionPrice);
                        }
                        
                        float price = Float.parseFloat(optionPrice);
                        a1.setOption(num, i, name, price);
                    }
                    num++;            
                }
            }
            buff.close();
        }  catch (WrongInputFormat e) {
            e.print();
            e.log(e.getErrno());
            Fix1to100 fix = new Fix1to100();
            String default_filename = fix.fix(e.getErrno());
            FileIO default_model = new FileIO();
            return default_model.buildAuto(default_filename);
        }   catch (IOException e) {
            System.out.println("IO exception.");
            Fix1to100 fix = new Fix1to100();
            String default_model = fix.fix2();
            FileIO io = new FileIO();
            return io.buildAuto(default_model);
        }
        return a1;
    }   
    
    // Serializable
    public void seriablizableAuto(Automobile a1) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("automotive.dat"));
            out.writeObject(a1);
            out.close();
        } catch (Exception e) {
            System.out.print("Seriablizable error: " + e);
            System.exit(1);
        }
    }
    
    public Automobile deseriablizableAuto(String filename) {
        Automobile a1 = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("automotive.dat"));
            a1 = (Automobile)in.readObject();
            in.close();
        } catch(Exception e) {
            System.out.print("Error " + e);
            System.exit(1);
        }
        return a1;
    }
}
