package effectivejava.chapter5;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

public class Stack<E> {
	private E [] items;
	private static final int DEFAULT_CAPACITY = 3;
	private int index = 0;
	
	@SuppressWarnings("unchecked")
	public Stack(int initialCapacity) {
		if(initialCapacity > 0) {
			items = (E[]) new Object[DEFAULT_CAPACITY];
		} else {
			items = (E[]) new Object[initialCapacity];
		}
	}
	
	public Stack() {
		this(DEFAULT_CAPACITY);
	}
	
	public E pop() {
		if(index <= 0) {
			throw new EmptyStackException();
		}
		
		E item = items[--index];
		items[index] = null;
		return item;
	}
	
	public void push(E obj) {
		Objects.requireNonNull(obj);
		
		ensureCapacity();
		items[index++] = obj;
	}
	
	public void pushAll(Iterable<? extends E> iter) {
		for(E e : iter) {
			push(e);
		}
	}
	
	private void ensureCapacity() {
		if(index >= items.length) {
			items = Arrays.copyOf(items, items.length * 2 + 1);
		}
	}
}
