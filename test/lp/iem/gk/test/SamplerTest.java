package lp.iem.test.test;

import static org.junit.Assert.*;
import lp.iem.gk.Sampler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SamplerTest
{
	static Sampler sample;
	
	@BeforeClass
    public static void setUpClass() throws Exception 
    {
		sample = new Sampler();
    }

	@Test
	public void testUniformFloat()
	{
		float rand = sample.uniformFloat();
		float rand2 = sample.uniformInt(5);
		
		System.out.println("rand : "+rand);
		System.out.println("rand2: " + rand2);
		
		assertTrue(0 <= rand && rand <=1);
		assertTrue(0 <= rand2 && rand2 <=5);
	}
	
	@Test
	public void testInitRand()
	{
		sample.init(1);
	}
}
