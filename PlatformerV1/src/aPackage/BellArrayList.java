package aPackage;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;




/**
 * Some bellarmine students attempt at creating a list
 * @author Kiya.Zadeh22
 *
 */
public class BellArrayList<E> implements List<E> {
	
	private Object[] arr;
	private int size = 0;
	/**
	 * Creates a new BellArrayList Object
	 */
	public BellArrayList() {
		arr = new Object[10];
	}
	/**
	 * Creates a new BellArrayList Object
	 * @param Int size of the list
	 * @throws IllegalArgumentException if the size is negative
	 * 
	 */
	public BellArrayList(int size) {
		if (size < 0) throw new IllegalArgumentException();
		arr = new Object[size];
	}
	/**
	 * Creates a new BellArrayList out of an existing array
	 * @param arg the array to use as the base of this arraylist
	 */
	public BellArrayList(E[] arg) {
		arr = arg;
		this.size = arg.length;
	}
	/**
	 * gets the size of this BellArrayList
	 * @return the number of objects stored in the list
	 */
	public int size() {
		return size;
	}
	private void doubleCapacity() {
		Object[] newArr = new Object[arr.length*2];
		for (int i = 0; i < arr.length; i++) {
			newArr[i] = arr[i];
		}
		arr = newArr;
	}
	/**
	 * Appends a String to the end of this array
	 * @param s The object to add to the string
	 */
	public boolean add (E s) {
		if (size == arr.length) doubleCapacity();
		arr[size] = s;
		size++;
		return true;
	}
	/**
	 * Appends a String to a specific posision of this array
	 * @param index The position to insert the String
	 * @param s the String to be added
	 */
	public void add (int index, E s) {
		while (index >= arr.length || size == arr.length) doubleCapacity();
		
		if (arr[index] == null) {
			arr[index] = s;
			if ((size <= index)) size = index + 1;
		}
		else if (arr.length+1 == index) {
			Object temp = arr[index];
			
			arr[index] = s;
			for (int i = size; i <= index; i--) {
				arr[i+1] = arr[i];
			}
			arr[index+1] = temp;
			size++;
		}
		else {
			doubleCapacity();
			Object temp = arr[index];
			
			arr[index] = s;
			int i;
			for (i = size; i > index; i--) {
				arr[i+1] = arr[i];
			}
			arr[i+1] = temp;
			size++;
		}
		
		
	}
	/**
	 * Replaces a String at a position
	 * @param index The position to perform the replacement at
	 * @param s The new string to replace
	 * @return The former String stored at that index
	 */
	@SuppressWarnings("unchecked")
	public E set (int index, String s) {
		while (index >= arr.length || size == arr.length) doubleCapacity();
		Object temp = arr[index];
		arr[index] = s;
		return (E) temp;
	}
	
	@SuppressWarnings("unchecked")
	E elementAt (int index) {
		return (E) arr[index];
	}
	/**
	 * Gets the object stored at this index
	 * @param index the position to get an object at
	 * @return The object stored at this position
	 * @throws ArrayIndexOutOfBoundsException if the the index parameter is greater than the size of the array
	 */
	public E get(int index) {
		if (index >= arr.length) throw new ArrayIndexOutOfBoundsException();
		else return elementAt(index);
	}
	/**
	 * Removes an object at a position
	 * @param index the position to remove an object from
	 * @return The object formerly stored at this position
	 * @throws ArrayIndexOutOfBoundsException if the the index parameter is greater than the size of the array
	 */
	@SuppressWarnings("unchecked")
	public E remove(int index) {
		if (index > arr.length) throw new ArrayIndexOutOfBoundsException("Tried to remove an element out of capacity");
		if (arr[index] == null) return null;
		else {
			Object temp = arr[index];
			arr[index] = null;
			size--;
			return (E) temp;
		}
		
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public E set(int index, E element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ListIterator<E> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<E> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}


