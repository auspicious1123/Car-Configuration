package rw1.cmu.edu.assignment3part1.model;

/**
 * Created by Rui on 3/31/16.
 */
public class Student {
    private int ID;
    private double[] Scores;

    public Student(int id, double[] s){
        ID = id;
        Scores = s;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(ID);
        for (int i = 0; i < Scores.length; i++) {
            sb.append(String.format(",\t\t%.1f",Scores[i]));
        }
        sb.append("\n");
        return sb.toString();
    }

    public double[] getScores() {
        return Scores;
    }
}
