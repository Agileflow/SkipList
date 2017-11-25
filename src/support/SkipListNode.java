package support;

/**
 * Created by Agile-flow on 16-Mar-17.
 */

public class SkipListNode<T extends Comparable<T>> {

    private SkipListNode bottom;  //points to the node below it null if the lowest node
    private SkipListNode next;  // points to the node next to it

    private T data;     // data stored in the node
    private int level; // level of this node in the list

    // initializes a skiplist node with a
    // generic type T which is the data in the node
    SkipListNode(T data, int level){
        this.data = data;
        this.level = level;
    }

    SkipListNode(int level, SkipListNode<T> tail){
        this.level = level;
        this.next = tail;
    }

    /*
    * accessors for the node fields
     */

    // setters of the attached nodes
    public void setBottom(SkipListNode<T> node){
        this.bottom = node;
    }

    public void setNext(SkipListNode<T> node){
        this.next = node;
    }

    public void updateLevel(int level){
        this.level = level;
    }

    /*
    *   getters of the node fields
     */

    public T getData(){ return this.data; }

    public SkipListNode<T> getBottom(){ return this.bottom; }

    public SkipListNode<T> getNext(){ return this.next; }

    public int getLevel(){
        return this.level;
    }

    /*
    *   Params SkipListNode Object to compare with
    *   Returns int
    *           0   if data of current node equals data of object parameter
    *           1   if data of current node greater than data of object parameter
    *           -1 if data of current node less than data of object parameter
     */
    public int compareTo(SkipListNode<T> node){
        if(node == null)
            return -1;
        else
            return getData().compareTo(node.getData());
    }

}
