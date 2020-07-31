package gr.codehub.rsapi.io;


import java.io.FileNotFoundException;
import java.util.List;

public interface Reader<T> {
    List<T> readFromExcel() throws FileNotFoundException;

}
