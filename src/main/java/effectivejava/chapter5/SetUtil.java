package effectivejava.chapter5;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SetUtil {
	private SetUtil() { }

	/**
	 * All Comparables are consumers so they use "? super E".
	 * 
	 * @param s1
	 * @return
	 */
	public static <E extends Comparable<? super E>> Optional<E> max(Set<? extends E> s1) {
		E res = null;
		for(E val : s1) {
			if(res == null || val.compareTo(res) > 0) {
				res = val;
			}
		}
		
		return Optional.ofNullable(res);
	}

	/**
	 * All Comparator are consumers so they use "? super E".
	 *
	 * @param s1
	 * @return
	 */
	public static <E> Optional<E> max(Set<? extends E> s1, Comparator<? super E> comparator) {
		E res = null;
		for(E val : s1) {
			if(res == null || comparator.compare(val, res) > 0) {
				res = val;
			}
		}

		return Optional.ofNullable(res);
	}

	public static <E> Set<E> union(Set<? extends E> s1, Set<? extends E> s2) {
		Set<E> result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}
}
