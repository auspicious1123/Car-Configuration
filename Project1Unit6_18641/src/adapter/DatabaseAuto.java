package adapter;

/**
 * @author-Rui Wang rw1
 */
public interface DatabaseAuto {
    public void buildDBAuto(String filename);
    public void deleteDBAuto(String automobileName);
    public void updateDBAutoPrice(String automobileName, int newPrice);
}
