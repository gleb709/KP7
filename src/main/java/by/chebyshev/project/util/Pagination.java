package by.chebyshev.project.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Pagination<T> {
    private static final int SIZE = 1;

    public List<T> pageSelect(int page, List<T> list){
        List<T> finalList = new ArrayList<>();
        int firstPosition = SIZE * page;
        int count = list.size() - firstPosition;
        for(int i = 0; count > 0 && i < SIZE; i++, count--){
            finalList.add(list.get(firstPosition++));
        }
        return finalList;
    }

    public int pageCount(List<T> list){
        return list.size()/SIZE - 1;
    }
}
