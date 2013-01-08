package lp.iem.test.test;

import static org.junit.Assert.*;
import lp.iem.gk.Point;
import lp.iem.gk.Vector;
import org.junit.*;

public class PointTest
{
	static Point point1;
	static Point point2;
	static Point point3;
	static Point point4;

	@BeforeClass
	public static void setUpClass() throws Exception
	{
		point1 = new Point();
		point2 = new Point((float) 12.5);
		point3 = new Point((float) 12.2,(float) 19.5,(float) 32.7);
		point4 = new Point(new Vector((float) 14.2,(float) 12.2,(float) 18.7));
	}

	/*@Test
	public void testConstructeur()
	{
		// fail("Not yet implemented");
		System.out.println("test Des Constructeurs de Point :");
		System.out.println("P1 : "+point1.toString());
		System.out.println("P2 : "+point2.toString());
		System.out.println("P3 : "+point3.toString());
		System.out.println("P4 : "+point4.toString());
	}
	*/
	@Test
	public void testAdditionPointVector()
	{
		System.out.println("\ntest fonction additionVector:");
		Point test = new Point(1,1,1);
		Vector v = new Vector (2,2,2);
		
		System.out.println("test : "+test);
		System.out.println("Vector : "+v);
		
		System.out.println("test + v : "+(test.additionVector(v)).toString());
		
		System.out.println("\ntest fonction additionVector avec affectation:");
		System.out.println("point3 : "+point3);
		System.out.println("Vector : " +v);
		point3.addition(v);
		System.out.println("P3 apres addition vecteur(2,2,2) affectation:" + point3.toString());
	}
	
	@Test
	public void testSubstractionPointVector()
	{
		System.out.println("\ntest fonction SubstractionVector:");
		Point test = new Point(1,1,1);
		Point test2 = new Point((float) 2.5,9,4);
		Vector v = new Vector (2,1,2);
		
		Vector res = test2.substractionPoint(test);
		System.out.println("\ntest2 : "+test2);
		System.out.println("test : " + test);
		System.out.println("Vector res (test2 - test):"+ res.toString());
		
		System.out.println("\npoint - vecteur :");
		System.out.println("test 2 : "+test2);
		System.out.println("Vector : "+v);
		Point res2 = test2.substractionVector(v);
		System.out.println("res 2 (test2 - v) : "+res2);
		
		
		System.out.println("\nSubstraction point - vecteur avec affectation dans point test2-v ");
		test2.substraction(v);
		System.out.println(test2.toString());
	}
	
	@Test
	public void testEqual()
	{
		System.out.println("\ntest fonction equals :");
		Point p1 = new Point(10,45,(float) 12.3);
		Point p2 = new Point(10,45,(float) 12.3);
		
		Point p3 = new Point(13,45,(float) 12.3);
		assertEquals(p1,p2);
		
	}
	
	@Test
	public void testDevid()
	{
		Point p1 = new Point(25,12,15);
		float f = (float) 2;
		
		p1.dividByFloat2(f);
		Point p2 = new Point((float)12.5,6,(float)7.5);
		
		assertEquals(p1,p2);
	}
	

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

}
