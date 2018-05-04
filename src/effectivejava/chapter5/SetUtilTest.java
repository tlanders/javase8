package effectivejava.chapter5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SetUtilTest {

	@Test
	public void testUnionSimple() {
		Set<Integer> si1 = new HashSet<>();
		si1.add(1);
		si1.add(2);
		si1.add(3);
		si1.add(4);
		
		Set<Integer> si2 = new HashSet<>();
		si2.add(11);
		si2.add(21);
		si2.add(31);
	
		Set<Integer> sfinal = SetUtil.union(si1, si2);
		
		assertEquals(si1.size() + si2.size(), sfinal.size());
	}
}
