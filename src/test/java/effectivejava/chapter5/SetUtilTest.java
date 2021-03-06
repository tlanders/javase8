package effectivejava.chapter5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.*;

import org.junit.Test;

public class SetUtilTest {

	@Test
	public void testMaxSimple() {
		Set<Integer> si1 = new HashSet<>();
		si1.add(1);
		si1.add(2);
		si1.add(3);
		si1.add(4);

		Optional<Integer> optMax = SetUtil.max(si1);
		
		assertTrue(optMax.isPresent());
		assertEquals(Integer.valueOf(4), optMax.get());
	}

	@Test
	public void testMaxSimpleComparator() {
		Set<Number> si1 = new HashSet<>();
		si1.add(1);
		si1.add(2.2);
		si1.add(3.3);
		si1.add(4);

		Optional<Number> optMax = SetUtil.max(si1, Comparator.comparing(Number::toString));
//		Optional<Number> optMax = SetUtil.max(si1, (Number n1, Number n2) -> n1.toString().compareTo(n2.toString()));

		assertTrue(optMax.isPresent());
		assertEquals(Integer.valueOf(4), optMax.get());
	}

	@Test
	public void testUnionSimple() {
		Set<Integer> si1 = new HashSet<>();
		si1.add(1);
		si1.add(2);
		si1.add(3);
		si1.add(4);
		
		Set<Integer> si2 = new HashSet<>();
		si2.add(1);
		si2.add(21);
		si2.add(31);
	
		Set<Integer> sfinal = SetUtil.union(si1, si2);
		
		assertEquals(si1.size() + si2.size() - 1, sfinal.size());
	}
	
	@Test
	public void testUnionSubclass() {
		Set<Integer> si1 = new HashSet<>();
		si1.add(1);
		si1.add(2);
		si1.add(3);
		si1.add(4);
		
		Set<Double> si2 = new HashSet<>();
		si2.add(1.1);
		si2.add(2.1);
		si2.add(3.1);
		si2.add(4.1);
		
		Set<Number> sfinal = SetUtil.union(si1, si2);
		
		// this is how to explicitly call the Number version if the compiler can't figure it out
//		Set<Number> sfinal = SetUtil.<Number>union(si1, si2);
		
//		System.out.println(sfinal);
		assertEquals(si1.size() + si2.size(), sfinal.size());
	}	
}
