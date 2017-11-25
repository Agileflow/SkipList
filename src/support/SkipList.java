package support;

import exception.DataDoesNotExistException;
import exception.DataDuplicateException;
import exception.EmptySkipListException;

/**
 * Created by Agile-flow on 16-Mar-17.
 */

public class SkipList<T extends Comparable<T>> {


    private SkipListNode<T> head;

    private final int DEFAULT_LEVEL = 1;

    private int size;

    public SkipList(){

        head = createSentinel(DEFAULT_LEVEL, null);
        this.size = 0;
    }

    public SkipListNode<T> Head(){
        return this.head;
    }

    private void setHead(SkipListNode<T> newHead){
        this.head = newHead;
    }

    public void insert(T data) throws DataDuplicateException{

        int level = randomLevel(); // randomized LEVEL to insert new element
        System.out.println(level);

        SkipListNode<T> oldHead = Head();

        // condition captures whenever the randomized level is equal the list MAX_LEVEL
        // MAX_LEVEL of the list is stored in the Head() node
        if(oldHead.getLevel() <= level){

            createNewHead(oldHead, level);
            SkipListNode<T> nodeTemp = null;

            for(int i = level; i > 0; i--){

                oldHead.updateLevel(i);
                SkipListNode<T> newNode = createNode(data, i);

                SkipListNode<T> current = oldHead;

                while(current != null){

                    if(newNode.compareTo(current.getNext()) > 0){

                        current = current.getNext();

                    }else if(newNode.compareTo(current.getNext()) < 0){
                        SkipListNode<T> temp = current.getNext();
                        oldHead.setNext(newNode);
                        newNode.setNext(temp);
                        incSize();
                        if(i < level){
                            nodeTemp.setBottom(newNode);
                        }
                        nodeTemp = newNode;
                        current = null;
                    }else{
                            throw new DataDuplicateException("Data already exist");
                    }
                }

                // creates new sentinel node to fill up the new levels
                // Condition to check if bottom is  not null
                //else creates a new -infinity/sentinel node
                if(oldHead.getBottom() != null){
                    oldHead = oldHead.getBottom();
                }else{
                    oldHead.setBottom(createSentinel(i, null));
                    oldHead = oldHead.getBottom();
                }
            }
            // condition captures whenever the randomized level is less than the list MAX_LEVEL
            // MAX_LEVEL of the list is stored in the Head() node of the list
        }else if(oldHead.getLevel() > level) {

            // Get the sentinel at the level equal to the level of the node to be inserted
            while (oldHead.getLevel() != level) {
                oldHead = oldHead.getBottom();
            }

            SkipListNode<T> nodeTemp = null;

            for (int i = level; i > 0; i--) {

                SkipListNode<T> newNode = createNode(data, i);

                SkipListNode<T> current = oldHead;

                while (current != null) {

                    if (newNode.compareTo(current.getNext()) > 0) {

                        current = current.getNext();

                    } else if (newNode.compareTo(current.getNext()) < 0) {
                        SkipListNode<T> temp = current.getNext();
                        oldHead.setNext(newNode);
                        newNode.setNext(temp);
                        incSize();
                        if (i < level) {
                            nodeTemp.setBottom(newNode);
                        }
                        nodeTemp = newNode;
                        current = null;
                    } else {
                        throw new DataDuplicateException("Data already exist");
                    }
                }
                oldHead = oldHead.getBottom();
            }
        }
    }

    /*
    *   Find function that searches the list for the specified data
    *   @param T data to find the location in the list
    *   @return SkipListNode<T> the address of the data
    *               SkipListNode<T>  - if found
    *                           null - otherwise
     */

    public SkipListNode<T> find(T data) throws EmptySkipListException {

        SkipListNode<T> current = Head(); // Gets the head of the list where search will start from
        SkipListNode<T> find = createNode(data, DEFAULT_LEVEL); // Clone a temporary object for the data to search for

        while(current.getBottom() != null){

            if(find.compareTo(current.getNext()) > 0){
                current = current.getNext();
            }else if(find.compareTo(current.getNext()) < 0){
                current = current.getBottom();
            }else{
                return current.getNext();
            }
        }
        return null;
    }

    /*
    *   remove function deletes the node with the specified data
    *   @param T data to delete
    *   @return boolean
    *                   true  - if deleted
    *                   false - otherwise
     */

    public boolean remove(T data) throws DataDoesNotExistException, EmptySkipListException {

        SkipListNode<T> delNode = createNode(data, DEFAULT_LEVEL);   // find the data in the Skip list
        SkipListNode<T> current = Head();
        SkipListNode<T> found = null;

        while(current.getBottom() != null){

            if(delNode.compareTo(current.getNext()) > 0){
                current = current.getNext();
            }else if(delNode.compareTo(current.getNext()) < 0){
                current = current.getBottom();
            }else{
                found = current.getNext();
                current.setNext(found.getNext());
                decSize();
            }
        }

        updateList();

        return find(data) != null? false: true;
    }

    public void displayList(){

        SkipListNode<T> sentinel = Head();
        SkipListNode<T> current = null;
        System.out.println();
        System.out.println("Level");
        while(sentinel != null){

            System.out.print("" + sentinel.getLevel() + ":  null ------> ");
            current = sentinel.getNext();
            while(current != null){
                System.out.print(current.getData() + " ------> ");
                current = current.getNext();
            }
            System.out.print(" null" + "\n");
            sentinel = sentinel.getBottom();
        }
    }

    private void createNewHead(SkipListNode<T> oldHead, int level){
        SkipListNode newHead = new SkipListNode(level + DEFAULT_LEVEL, null);
        setHead(newHead);
        newHead.setBottom(oldHead);
    }

    private SkipListNode<T> createSentinel(int level, SkipListNode<T> tail){
        return new SkipListNode<T>(level, tail);
    }

    private SkipListNode<T> createNode(T data, int level){
        return new SkipListNode<T>(data, level);
    }

    private void updateList(){
        SkipListNode<T> current = Head();

        while(current.getNext() == null){
            current = current.getBottom();
        }
        createNewHead(current, current.getLevel());
    }

    private void incSize() {
        this.size += 1;
    }

    private void decSize() {
        this.size -= 1;
    }

    public int size(){
        return this.size;
    }

    private int randomLevel(){
        int h = DEFAULT_LEVEL;
        int coin = Toss();
        while(coin != 0){
            h += 1;
            coin = Toss();
        }
        return h;
    }

    private int Toss(){
        return (int) Math.round(Math.random());
    }
}