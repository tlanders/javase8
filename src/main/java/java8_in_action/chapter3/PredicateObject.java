package java8_in_action.chapter3;

public class PredicateObject {
	protected int count = 0;
	
	protected int getCount() {
		//System.out.println("count=" + count);
		return count++;
	}
}
