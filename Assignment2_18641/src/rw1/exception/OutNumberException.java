package rw1.exception;

/**
 * @author-Rui Wang rw1
 */
public class OutNumberException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void print() {
        System.out.println("Student number is out of max number: 40");
    }
}
