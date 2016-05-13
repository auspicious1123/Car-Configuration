package rw1.cmu.edu.assignment3part1.model;

import java.util.ArrayList;

/**
 * Created by Rui on 3/31/16.
 */
public class CalQuizScore {
    private ArrayList<Student> studentList;
    private double[] highScores;
    private double[] lowScores;
    private double[] avgScores;

    public CalQuizScore(ArrayList<Student> sl) {
        studentList = sl;
        highScores = new double[studentList.get(0).getScores().length];
        lowScores = new double[studentList.get(0).getScores().length];
        avgScores = new double[studentList.get(0).getScores().length];
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    // calculate values.
    public void calculate() {
        for (int i = 0; i < studentList.get(0).getScores().length; i++) {
            double[] temp = studentList.get(0).getScores();
            highScores[i] = temp[i];
            lowScores[i] = temp[i];
            avgScores[i] = temp[i];
        }

        for (int i = 1; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            double[] temp = s.getScores();
            for (int j = 0; j < studentList.get(0).getScores().length; j++) {
                if (temp[j] > highScores[j]) {
                    highScores[j] = temp[j];
                }
                if (temp[j] < lowScores[j]) {
                    lowScores[j] = temp[j];
                }
                avgScores[j] += temp[j];
            }
        }
        for (int i = 0; i < studentList.get(0).getScores().length; i++) {
            avgScores[i] = avgScores[i] / studentList.size();
        }
    }

    // show result of highScore and lowSoce and avgScore
    public String resulString() {
        StringBuffer sb = new StringBuffer();
        sb.append("High Score");
        for (int i = 0; i < highScores.length; i++) {
            sb.append(String.format(",\t\t%.1f", highScores[i]));
        }
        sb.append("\n");

        sb.append("Low Score");
        for (int i = 0; i < lowScores.length; i++) {
            sb.append(String.format(",\t\t%.1f", lowScores[i]));
        }
        sb.append("\n");

        sb.append("Average");
        for (int i = 0; i < avgScores.length; i++) {
            sb.append(String.format(",\t\t%.1f", avgScores[i]));
        }
        sb.append("\n");
        return sb.toString();
    }
}
