package effectivejava.chapter6;

public class SimpleTestSample {
	@SimpleTest
	public static void m1() { }	// should pass
	
	public static void m2() { }	// not a test
	
	@SimpleTest
	public static void m3() { throw new RuntimeException("m3 threw this"); } // should fail
	
	public static void m4() { } // not a test
	
	@SimpleTest
	public void m5() { } 	// invalid, not a static method
	
	public static void m6() { }	// not a test
	
	@SimpleExceptionTest(RuntimeException.class)
	public static void m7() { throw new RuntimeException("thrown by m7"); }	// should pass
	
	public static void m8() { } // not a test
	
	@SimpleTest
	public static void m9(int a, double b) { }	// should fail because has parameters
	
	@SimpleExceptionTest(IllegalStateException.class)
	public static void m10() { throw new RuntimeException("thrown by m10"); }	// should fail
	
	@SimpleExceptionTest(ArithmeticException.class)
	public static void m11() { int i = 0; i = i / i; }	// should pass
}
