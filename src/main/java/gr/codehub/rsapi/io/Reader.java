package gr.codehub.rsapi.io;


/**
 * The interface Read for reading file
 */
import java.io.FileNotFoundException;
import java.util.List;

public interface Reader<T> {
    public  List<T> readFromExcel() throws FileNotFoundException;

}
