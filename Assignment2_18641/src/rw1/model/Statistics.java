package rw1.model;

import rw1.entity.Student;
import rw1.util.Printable;
/**
 * @author-Rui Wang rw1
 */
public class Statistics implements Printable {
    private int[] lowscores = new int[5];
    private int[] highscores = new int[5];
    private float[] avgscores = new float[5];
    public void findlow(Student[] a){
        /*This mehtod will find the lowest score and store it in an array names lowscores.*/
        for(int i = 0; i < 5; i++){
            lowscores[i] = 100;
            for(int j = 0 ; j < a.length; j++){
                if(a[j].getScores()[i] < lowscores[i]){
                    lowscores[i] = a[j].getScores()[i]; 
                }
            }
        }
    }
    
    public void findhigh(Student[] a){
        /*This mehtod will find the highest score and store it in an array names lowscores.*/
        for(int i = 0; i < 5; i++){
            highscores[i] = 0;
            for(int j = 0 ; j < a.length; j++){
                if(a[j].getScores()[i] > highscores[i]){
                    highscores[i] = a[j].getScores()[i]; 
                }
            }
        }
    }
    
    public void findavg(Student[] a){
        /*This mehtod will find the avg score and store it in an array names lowscores.*/
        for(int i = 0; i < 5; i++){
            avgscores[i] = 0;
            for(int j = 0 ; j < a.length; j++){
                avgscores[i] += a[j].getScores()[i];
            }
            avgscores[i] = avgscores[i] / a.length;
        }
    }

    /* (non-Javadoc)
     * @see assignment2.rw1.util.Printable#printInfor()
     */
    @Override
    public void printInfor() {
        // TODO Auto-generated method stub
        
        // print lowest scores
        System.out.print("The lowest score of each quiz:  ");
        for(int i = 0; i < lowscores.length; i++){
            System.out.print(lowscores[i] + " ");
        }
        System.out.println();
        
        // print highest score
        System.out.print("The highest score of each quiz:  ");
        for(int i = 0; i < highscores.length; i++){
            System.out.print(highscores[i] + " ");
        }
        System.out.println();
        
        // print average
        System.out.print("The average score of each quiz:  ");
        for(int i = 0; i < avgscores.length; i++){
            System.out.print(avgscores[i] + " ");
        }
        System.out.println();
    }  
}
