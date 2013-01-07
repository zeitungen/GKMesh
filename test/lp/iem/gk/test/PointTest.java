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
		
		System.out.println((test.additionVector(v)).toString());
		
		System.out.println("\ntest fonction additionVector avec affectation:");
		point3.addition(v);
		System.out.println("P3 apres addition vecteur(2,2,2) affectation:" + point3.toString());
	}
	
	@Test
	public void testSubstractionPointVector()
	{
		System.out.println("\ntest fonction SubstractionVector:");
		Point test = new Point(1,1,1);
		Point test2 = new Point(4,4,4);
		Vector v = new Vector (2,1,2);
		
		Vector res = test2.substractionPoint(test);
		System.out.println("Vector res :"+ res.toString());
		
		System.out.println("point - vecteur :");
		Point res2 = test2.substractionVector(v);
		System.out.println("res 2 : "+res2);
		System.out.println("test 2 : "+test2);
		
		System.out.println("Substraction point - vecteur avec affectation dans point ");
		test2.substraction(v);
		System.out.println(test2.toString());
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
