package rw1.entity;

import rw1.util.Printable;

/**
 * @author-Rui Wang rw1
 */
public class Student extends People implements Printable {
    private int SID;
    private int scores[] = new int[5];
    public Student(){ }
    public Student(int Stud, int[] sorces){
        this.SID = Stud;
        this.scores = sorces;
    }
    
    @Override
    public void setName(){ }    // implement abstract method
    
    //write public get and set methods for SID and scores
    public void setSID(int sid){
        this.SID = sid;
    }
    public int getSID(){
        return this.SID;
    }
    public void setScores(int[] scores){
        this.scores = scores;
    }
    public int[] getScores(){
        return this.scores;
    }
    
    /* (non-Javadoc)
     * @see assignment2.rw1.util.Printable#printInfor()
     */
    @Override
    public void printInfor() {
        // TODO Auto-generated method stub
        System.out.print(SID + " ");
        for(int i = 0; i < scores.length; i++){
            System.out.print(scores[i]+" ");
        }
        System.out.println();
    }
}
