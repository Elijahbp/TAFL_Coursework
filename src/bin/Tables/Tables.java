package bin.Tables;

import java.util.List;

public interface Tables<T> {
    boolean contains(String item);
    List<T> getListElements();
    T getElement(String item) ;
}
