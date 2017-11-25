package app;

import exception.DataDoesNotExistException;
import exception.DataDuplicateException;
import exception.EmptySkipListException;
import support.SkipList;

import java.util.Random;

/**
 * Created by Agile-flow on 16-Mar-17.
 */

public class Main {

    public static void main(String[] args) throws DataDuplicateException, DataDoesNotExistException, EmptySkipListException {

        SkipList<Integer> list = new SkipList<>();

        list.insert(43);
        list.insert(12);
        list.insert(8);
        list.insert(90);
        list.insert(89);

        list.displayList();

        list.remove(43);

        System.out.println("Find 43 " + list.find(43));

//        System.out.println("Next Element to 3 " + list.find(3).getData());

        list.displayList();
        System.out.println("Size = " + list.size());
    }
}
