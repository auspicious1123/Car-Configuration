package rw1.cmu.edu.mortgagecalculator.model;

/**
 * Created by Rui on 3/23/16.
 */

public class MortgageCalculator {
    // set parameters.
    private int beginYear;
    private int beginMonth;
    private String firstPaymentDate;
    private double monthlyPayment;
    private double totalPayment;
    private String payOffDate;
    public String priceString;
    public String termString;
    public String rateString;
    private double purchasedPrice;
    private double termYear;
    private double interestRate;

    // constructor.
    public MortgageCalculator(String price, String term, String rate, int year, int month) {
        priceString = price;
        termString = term;
        rateString = rate;
        beginYear = year;
        beginMonth = month;
        firstPaymentDate = String.valueOf(beginYear) + "/" + String.valueOf(beginMonth);
    }

    /**
     * @return the beginMonth
     */
    public int getBeginMonth() {
        return beginMonth;
    }

    /**
     * @return the firstPaymentDate
     */
    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    /**
     * @return the monthlyPayment
     */
    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    /**
     * @return the totalPayment
     */
    public double getTotalPayment() {
        return totalPayment;
    }

    /**
     * @return the payOffDate
     */
    public String getPayOffDate() {
        return payOffDate;
    }

    // check the input whether is valid.
    public boolean validInput() {
        // get the valid input
        if (priceString.length() == 0 || termString.length() == 0 || rateString.length() == 0) {
            return false;
        } else {
            purchasedPrice = Double.parseDouble(priceString);
            termYear = Double.parseDouble(termString);
            interestRate = Double.parseDouble(rateString);
        }
        if (purchasedPrice <= 0) {
            return false;
        }
        if (interestRate <= 0 || interestRate >= 99) {  //using interger when calculate need to divide by 100
            return false;
        }
        if (termYear < 1.0 || termYear > 99) {
            return false;
        }




        return true;
    }

    public void calculate() {
        // change input rate into 0.000%.
        interestRate /= 100.0;

        // change term into year length.
        double termInMonths = termYear * 12.0;

        // change rate into month rate.
        double monthlyRate = interestRate / 12.0;
        // month pay = month rate * price / (1 - (1+ month rate)^(-term rate))
        this.monthlyPayment = (monthlyRate * purchasedPrice) / (1 - Math.pow(1 + monthlyRate, -termInMonths));
        // total pay = month pay * 12 * term year.
        this.totalPayment = 12 * this.monthlyPayment * termYear;

        // calculate the pay off date
        calculateDate();
    }

    // calculate date
    private void calculateDate() {
        int endYear = this.beginYear + (int) termYear;
        int endMonth;
        switch (this.beginMonth) {
            case 1:
                endMonth = 12;
                endYear--;
                break;
            default:
                endMonth = this.beginMonth - 1;
                break;
        }
        //show the result pay date
        if (endMonth < 9) {
            payOffDate = endYear + " / " + endMonth;
        } else {
            payOffDate = endYear + " / " + endMonth;
        }
    }

}
