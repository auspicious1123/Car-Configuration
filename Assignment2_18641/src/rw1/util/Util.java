package rw1.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rw1.entity.Student;
import rw1.exception.OutNumberException;

/**
 * @author-Rui Wang rw1
 */
public class Util {

    public static Student[] readFile(String filename, Student[] stu) {
        //Read the file and builds student array.
        //open the file using FileReader Object.
        //In a loop read a line using readLine method
        //Tokenize each line using StringTokenizer Object
        //Each token is converted from String to Integer using parseInt method
        //Value is then saved in the right property of Student Object.

        final int MAX_STUDENT_NUM = 40;

        try {
            FileReader file = new FileReader(filename); // put input.txt into project directory
            @SuppressWarnings("resource")
            BufferedReader buff = new BufferedReader(file);
            boolean eof = false;
            String title = buff.readLine();
            System.out.println(title);
            int num = 0;
            while (!eof) {
                String line = buff.readLine();
                if (line == null)
                    eof = true;
                else {
                    //System.out.println(line);  // each time print one line
                    String[] readin = line.split(" "); // read into one line and save it into as a student
                    // object array, remember new a object and save it
                    Student student = new Student();
                    student.setSID(Integer.parseInt(readin[0]));
                    int[] scores = new int[readin.length - 1];
                    // scores used to save the Quiz scores
                    for (int i = 1; i < readin.length; i++) {
                        scores[i - 1] = Integer.parseInt(readin[i]);
                    }
                    student.setScores(scores);
                    student.printInfor();
                    stu[num++] = student;
                    if (num == MAX_STUDENT_NUM) { // set exception boundary
                        throw new OutNumberException();
                    }
                }
            }
            buff.close();
        } catch (OutNumberException outNum) {
            System.out.println("Error­:­ ");
            outNum.print();
        } catch (IOException e) {
            System.out.println("Error­­ " + e.toString());
        }
        return stu;
    }

}