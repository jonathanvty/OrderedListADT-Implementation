//	PROGRAMMER: Jonathan Ty
//	ACCOUNT: masc0208
//	RED ID: 812942657
//	DATE MODIFIED: July 28, 2015

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class OrderedVector<E> implements OrderedListADT<E> {

	private E[] storage;

	private int arraySize, currentSize;

	public OrderedVector() {

		arraySize = DEFAULT_MAX_CAPACITY; 	// default = [100]

		currentSize = 0;

		storage = (E[]) new Object[arraySize];

	}

	// Adds the Object obj to the list in the correct position as determined by
	// the Comparable interface.

	public void insert(E obj) {
		
		if (currentSize == arraySize) {
			arrayIncrease();
		}

		int index = FIP(obj, 0, currentSize - 1);
		
		for (int i = currentSize - 1; i >= index; i--) {
			storage[i + 1] = storage[i];
		}

		storage[index] = obj;
		currentSize++;
	}

	// Removes and returns the object located at the parameter index position
	// (zero based).
	// Throws IndexOutOfBoundsException if the index does not map to a valid
	// position within the list.
	
	public E remove(int index) {
		
		if(currentSize < arraySize/4)	
			arrayDecrease(); 
		
			if(index < 0 || index > currentSize-1)	
			throw new IndexOutOfBoundsException();

			E temp = storage[index];
			
			for(int i =index; i < currentSize-1; i++) 
				storage[i] = storage[i+1];
				
			currentSize--;

			return temp;	

		}

	// Removes and returns the parameter object obj from the list if the list
	// contains it, null otherwise.
	
	public E remove(E obj) {

		if (isEmpty()) 
			return null;
		if(find(obj)==-1)
			return null;
		return remove(find(obj));
		
	}

	// Removes and returns the smallest element in the list and null if the it
	// is empty.
	
	public E removeMin() {
		
		if(isEmpty())
			return null;
		
		return remove(0);
		
	}

	// Removes and returns the largest element in the list and null if the it is
	// empty.
	
	public E removeMax() {

		if(isEmpty())
			return null;
		
		return remove(currentSize-1);
		
	}

	// Returns the parameter object located at the parameter index position
	// (zero based).
	// Throws IndexOutOfBoundsException if the index does not map to a valid
	// position within the underlying array

	public E get(int index) {

		if (index < 0 || index > currentSize-1)
			throw new IndexOutOfBoundsException();

		return storage[index];

	}

	// Returns the list object that matches the parameter, and null if the list
	// is empty.
	// This method is stable, if obj matches more than one element, the element
	// that
	// has been in the list longest is returned.

	public E get(E obj) {

		int result = binSearch(obj,0,currentSize-1);
		
    	if(result==-1)	
    		return null;
    	
    	return storage[result];

	}

	// Returns the index of the first element that matches the parameter obj
	// and -1 if the item is not in the list.

	public int find(E obj) {

		int index = binSearch(obj, 0, currentSize-1);

			return index;		
	} 		
		
	// Returns true if the parameter object obj is in the list, false otherwise.
	
	public boolean contains(E obj) {

		if (binSearch(obj, 0, currentSize-1) == -1)

			return false;

		return true;
	}

	// The list is returned to an empty state.
	
	public void clear() {
		
		currentSize = 0; // empty array
	}

	// Returns true if the list is empty, otherwise false
	
	public boolean isEmpty() {
		
		if (currentSize == 0) {
			
			return true;
		}

		return false;
	}

	// Returns the number of Objects currently in the list.
	
	public int size() {

		return currentSize;

	}

	// Returns an Iterator of the values in the list, presented in
	// the same order as the list.

	public Iterator<E> iterator() {

		return new IteratorHelper();

	}

	public class IteratorHelper<E> implements Iterator<E> {

		int iterIndex; 				// pg.88 lecture notes

		public IteratorHelper() {

			iterIndex = 0;

		}

		public boolean hasNext() {	// is there another item in the list?

			return iterIndex < currentSize;

		}

		public E next() {			// go to next item in list and return it.

			if (!hasNext())

				throw new NoSuchElementException();

			return (E) storage[iterIndex++];

		}

		public void remove() {		// not needed.just returns error.

			throw new UnsupportedOperationException();
		}

	}

	private void arrayIncrease() {

		arraySize *= 2; 				// mult by 2
		
		E[] temp = (E[]) new Object[arraySize];
		
		for (int i = 0; i < currentSize; i++) {

			temp[i] = storage[i];
		}
		storage = temp;
	}

	private void arrayDecrease() {

		arraySize = (arraySize / 2); 	// div by 2
		
		E[] temp = (E[]) new Object[arraySize];
		
		for (int i = 0; i < currentSize; i++) {

			temp[i] = storage[i];
		}
		storage = temp;
	}

	private int binSearch(E obj, int lo, int hi) {
		
		if (hi<lo) {
			
			if(lo > currentSize-1) return -1;
			
    		if ((((Comparable<E>) obj).compareTo(storage[lo]))==0)
    		
    			return lo;
    		
    		return -1;
    	}

    	int mid = (lo+hi)>>1;
	
		if (((Comparable<E>) obj).compareTo(storage[mid]) <= 0) 
			
			return binSearch(obj, lo, mid - 1); 	// go left
		
		return binSearch(obj, mid + 1, hi);		 // go right
	}

	private int FIP(E obj, int lo, int hi) {

		if (hi < lo)
			
			return lo;

		int mid = (lo + hi) / 2;

		if (((Comparable<E>) obj).compareTo(storage[mid]) < 0)

			return FIP(obj, lo, mid - 1);

		return FIP(obj, mid + 1, hi);

	}

} 	// end class
