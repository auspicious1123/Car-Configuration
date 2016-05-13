package driver;
import rw1.entity.Student;
import rw1.model.Statistics;
import rw1.util.Util;

/**
 * @author-Rui Wang rw1
 */
public class Driver {

    public static void main(String[] args) {
        // test case 1:
        System.out.println("Test case 1: 15 students data (handout data case)");
        System.out.println();
        int studentNum = 15;                     
        Student stu[] = new Student[studentNum];
        //Populate the student array
        stu = Util.readFile("filename.txt", stu);
        Statistics statistic = new Statistics();
        // call method to calculate lowest, highest and average score for 5 quiz
        statistic.findlow(stu);
        statistic.findhigh(stu);
        statistic.findavg(stu);
        //print the data and statistics
        System.out.println();
        statistic.printInfor();
        
        // test case 2:
        System.out.println();
        System.out.println("Test case 2: 45 student data(Exception case)");
        System.out.println();
        int studentNum1 = 40;                     
        Student stu1[] = new Student[studentNum1];
        //Populate the student array
        stu1 = Util.readFile("filename_45.txt", stu1);
        Statistics statistic1 = new Statistics();
        // call method to calculate lowest, highest and average score for 5 quiz
        statistic1.findlow(stu1);
        statistic1.findhigh(stu1);
        statistic1.findavg(stu1);
        //print the data and statistics
        System.out.println();
        statistic1.printInfor();
        
        // case 3: 5 student data
        System.out.println();
        System.out.println("Test case 3: 5 student data");
        System.out.println();
        int studentNum2 = 5;                     
        Student stu2[] = new Student[studentNum2];
        //Populate the student array
        stu1 = Util.readFile("filename_5.txt", stu2);
        Statistics statistic2 = new Statistics();
        // call method to calculate lowest, highest and average score for 5 quiz
        statistic2.findlow(stu2);
        statistic2.findhigh(stu2);
        statistic2.findavg(stu2);
        //print the data and statistics
        System.out.println();
        statistic2.printInfor();
    }
}
