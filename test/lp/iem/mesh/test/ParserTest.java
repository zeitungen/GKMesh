package lp.iem.mesh.test;

import static org.junit.Assert.*;
import lp.iem.mesh.Parser;

import org.junit.Test;

public class ParserTest {

	static Parser parser;
	
	@Test
	public void testOpen(){
		String path = "src/lp/iem/mesh/test/test.txt";
		parser = new Parser(path);
		assertTrue(parser.isValid());
		
		parser = new Parser("tesat.txt");
		assertTrue(!parser.isValid());
	}
	
	@Test
	public void testReadToken(){
		parser = new Parser("src/lp/iem/mesh/test/test.txt");
		parser.readToken();
		assertEquals("aaaaa", parser.getToken());
		
		int i = parser.readToken();
		assertEquals("", parser.getToken());
		assertTrue(i == '\n');
		
		parser.readToken();
		assertEquals("bbbb", parser.getToken());
		
		i = parser.readToken();
		assertEquals("", parser.getToken());
		assertTrue(i == '\n');
		
		parser.readToken();
		assertEquals("uui", parser.getToken());

	}

}
