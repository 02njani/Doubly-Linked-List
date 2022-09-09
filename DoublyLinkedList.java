import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Nitya Jani
 * @version 1.0
 * @userid njani8
 * @GTID 903598748
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index you're trying to add to is "
                    + "bigger than the size or less than zero.");
        }
        if (data == null) {
            throw new IllegalArgumentException("The data you're trying to add is null (it points to nothing).");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else if (index == size) { //this is in case the user wants to add to the back
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else if (size == 1 && index == 1) { //this is in case there is only one node in the list
            newNode.setPrevious(head);
            head.setNext(newNode);
            tail = newNode;
        } else if (size == 1 && index == 0) {
            // same thing here except it considers the other place the user might want to add to
            newNode.setNext(head);
            head.setPrevious(newNode);
            tail = head;
            head = newNode;
        } else if (index == 0) { //this is in case the user wants to add to the front
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else if (((size - 1 - index) > (index - 1))) { // this checks if it should traverse from the left
            DoublyLinkedListNode<T> curr = head;
            int counter = 1;
            while (counter < index) {
                curr = curr.getNext();
                counter++;
            }
            newNode.setNext(curr.getNext());
            newNode.setPrevious(curr);
            curr.getNext().setPrevious(newNode);
            curr.setNext(newNode);
        } else { //it'll traverse from the right otherwise
            DoublyLinkedListNode<T> curr = tail;
            int counter = size - 1;
            while (counter > index) {
                curr = curr.getPrevious();
                counter--;
            }
            newNode.setNext(curr);
            newNode.setPrevious(curr.getPrevious());
            curr.getPrevious().setNext(newNode);
            curr.setPrevious(newNode);
        }
        size = size + 1;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you're trying to put in is null (it points to nothing).");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size = size + 1;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you're trying to put in is null (it points to nothing).");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (size == 0) {
            head = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
        }
        tail = newNode;
        size = size + 1;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you're trying to remove from is too big or is negative.");
        }
        if (size == 1) {
            T data = head.getData();
            head = null;
            tail = null;
            size = size - 1;
            return data;
        } else if (index == 0) {
            T data = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size = size - 1;
            return data;
        } else if (index == size - 1) {
            T data = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size = size - 1;
            return data;
        } else if ((size - 1 - index) > (index - 1)) {
            DoublyLinkedListNode<T> curr = head;
            int counter = 1;
            while (counter < index) {
                curr = curr.getNext();
                counter++;
            }
            T data = curr.getNext().getData();
            curr.getNext().getNext().setPrevious(curr);
            curr.setNext(curr.getNext().getNext());
            size = size - 1;
            return data;
        } else {
            DoublyLinkedListNode<T> curr = tail;
            int counter = size - 1;
            while (counter > index) {
                curr = curr.getPrevious();
                counter--;
            }
            T data = curr.getData();
            curr.getNext().setPrevious(curr.getPrevious());
            curr.getPrevious().setNext(curr.getNext());
            size = size - 1;
            return data;
        }

    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty so you can't remove anything.");
        }
        T data = head.getData();
        if (size == 1) {
            head = null;
        } else {
            DoublyLinkedListNode<T> newHead = head.getNext();
            newHead.setPrevious(null);
            head = newHead;
        }
        size = size - 1;
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty so you can't remove anything.");
        }
        T data = tail.getData();
        if (size == 1) {
            tail = null;
        } else {
            DoublyLinkedListNode<T> newTail = tail.getPrevious();
            newTail.setNext(null);
            tail = newTail;
        }
        size = size - 1;
        return data;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you're trying to get stuff out of is "
                    + "too big or is negative.");
        }
        if (index == 0) {
            T data = head.getData();
            return data;
        }
        if (index == size - 1) {
            T data = tail.getData();
            return data;
        }
        if ((size - 1 - index) > (index - 1)) {
            DoublyLinkedListNode<T> curr = head;
            int counter = 0;
            while (counter < index) {
                curr = curr.getNext();
                counter++;
            }
            T data = curr.getData();
            return data;
        } else {
            DoublyLinkedListNode<T> curr = tail;
            int counter = size - 1;
            while (counter > index) {
                curr = curr.getPrevious();
                counter--;
            }
            T data = curr.getData();
            return data;
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you're trying to find is null (it points to nothing).");
        }
        if (tail.getData().equals(data)) {
            return tail.getData();
        }
        DoublyLinkedListNode<T> curr = tail.getPrevious();
        int counter = size - 2;
        while (counter >= 0) {
            T found = curr.getData();
            if (counter == 0) {
                if (found.equals(data)) {
                    curr.getNext().setPrevious(null);
                    head = curr.getNext();
                    size = size - 1;
                    return found;
                }
            } else if (found.equals(data)) {
                curr.getNext().setPrevious(curr.getPrevious());
                curr.getPrevious().setNext(curr.getNext());
                size = size - 1;
                return found;
            }
            curr = curr.getPrevious();
            counter--;
        }
        throw new NoSuchElementException("The element you're trying to find is not in the list.");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        }
        Object[] arr = new Object[size];
        DoublyLinkedListNode<T> curr = head;
        arr[0] = curr.getData();
        for (int i = 1; i < size; i++) {
            curr = curr.getNext();
            arr[i] = curr.getData();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /*public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addAtIndex(0, "one");
        list.addToBack("two");
        list.addToBack("three");
        list.addToBack("four");
        list.addToBack("five");
        list.addToBack("six");
        list.addToBack("four");
        list.addToBack("17");
        String s = list.removeLastOccurrence("four");
        System.out.println(s);
        System.out.println(list.getTail().getPrevious().getNext().getData());
    }*/
}
