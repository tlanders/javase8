package effectivejava.chapter6;

import static org.junit.Assert.*;

import org.junit.Test;

public class EnumTests {
	@Test
	public void planetTest() {
		Planet e = Planet.EARTH;
		assertEquals(Planet.EARTH, e);
		assertNotEquals(Planet.MARS, e);
		
		Planet j = Planet.JUPITER;
		
		assertTrue(j.mass() > e.mass());
		assertTrue(j.surfaceWeight(100.0) > 0);
		
		System.out.println("surface weight of 100 kg");
		for(Planet x : Planet.values()) {
			final double w = x.surfaceWeight(100.0);
			assertTrue(w > 0);
			System.out.println(x.name() + ": weight (N)=" + w 
				+ ", weight(lbs)=" + (w * .22));
		}
	}
	
	@Test
	public void testOperation() {
		Operation add = Operation.ADD;
		
		assertEquals(5, add.apply(2, 3), .0001);
		assertEquals(-1, Operation.SUBTRACT.apply(2, 3), .0001);
		assertEquals(6, Operation.MULTIPLY.apply(2, 3), .0001);
		assertEquals(.6666666, Operation.DIVIDE.apply(2, 3), .0001);
		
		assertEquals(Operation.ADD, Operation.valueOf("ADD"));
		
		double a = 1.23;
		double b = -2.34;
		for(Operation op : Operation.values()) {
			System.out.printf("%f %s %f = %f\n", a, op, b, op.apply(a, b));
		}
		
		for(Operation op : Operation.values()) {
			assertEquals(op, Operation.fromSymbol(op.toString()).get());
			assertEquals(op, Operation.valueOf(op.name()));
			
			// fromSymbol only looks up by the symbol and not the operation name
			assertFalse(Operation.fromSymbol(op.name()).isPresent());
		}
	}

	@Test
	public void testBasicOperation() {
		assertEquals(5, BasicOperation.ADD.apply(2, 3), .0001);
		assertEquals(-1, BasicOperation.SUBTRACT.apply(2, 3), .0001);
		
		testIOperation(BasicOperation.class, 1.23, -2.34);
	}

	@Test
	public void testExtendedOperation() {
		assertEquals(8, ExtendedOperation.POWER.apply(2, 3), .0001);
		assertEquals(2, ExtendedOperation.MODULUS.apply(11, 3), .0001);
		
		testIOperation(ExtendedOperation.class, 8.23, 2.45);
	}
	
	/**
	 * This method tests all enums that implement IOperation
	 * @param opType
	 * @param a
	 * @param b
	 */
	protected <T extends Enum<T> & IOperation> void testIOperation(Class<T> opType, double a, double b) {
		for(ExtendedOperation op : ExtendedOperation.values()) {
			System.out.printf("%f %s %f = %f\n", a, op, b, op.apply(a, b));
			assertEquals(op, ExtendedOperation.valueOf(op.name()));
		}		
	}
}
