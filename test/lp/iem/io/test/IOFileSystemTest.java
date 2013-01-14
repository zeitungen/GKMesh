package lp.iem.io.test;

import static org.junit.Assert.*;
import static lp.iem.io.IOFileSystem.*;

import java.io.File;
import java.text.FieldPosition;

import lp.iem.io.IOInfo;

import org.junit.Test;

public class IOFileSystemTest {

	@Test
	public void testPathname(){
		String path = "/this/is/a/funny/path";
		String expected = "/this/is/a/funny/";
		String actual = pathname(path);
		assertEquals(expected, actual);
		
		path = "\\this\\is\\a\\funny\\path";
		expected = "\\this\\is\\a\\funny\\";
		actual = pathname(path);
		assertEquals(expected, actual);
		
		path = "funny";
		expected = "./";
		actual = pathname(path);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testBasename(){
		String path = "/this/is/a/funny/path.cpp";
		String expected = "/this/is/a/funny/path";
		String actual = basename(path);
		assertEquals(expected, actual);
		
		path = "\\this\\is\\a\\funny\\path.cpp";
		expected = "\\this\\is\\a\\funny\\path";
		actual = basename(path);
		assertEquals(expected, actual);
		
		path = "./this/is/a/funny/path.cpp";
		expected = "./this/is/a/funny/path";
		actual = basename(path);
		assertEquals(expected, actual);
		
		path = "./funny";
		expected = "./funny";
		actual = basename(path);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSuffix(){
		String path = "./this/is/a/funny/path.cpp";
		String expected = "cpp";
		String actual = getSuffix(path);
		assertEquals(expected, actual);
		
		path = "./this/is/a/funny/path";
		expected = "";
		actual = getSuffix(path);
		assertEquals(expected, actual);
		
		path = "this/is/a/funny/path.cpp";
		expected = "cpp";
		actual = getSuffix(path);
		assertEquals(expected, actual);
		
		path = "this/is/a/funny/path";
		expected = "";
		actual = getSuffix(path);
		assertEquals(expected, actual);
	}

	@Test
	public void testIsType(){
		String path = "./this/is/a/funny/path.cpp";
		String suffix = "cpp";
		assertTrue(isType(path, suffix));
		
		path = "this/is/a/funny/path.cpp";
		assertTrue(isType(path, suffix));
		
		suffix = ".cpp";
		assertTrue(isType(path, suffix));
		
		path = "./this/is/a/funny/pathcpp";
		assertFalse(isType(path, suffix));
	}

	@Test
	public void testChangeType(){
		String path = "./this/is/a/funny/path.h";
		String suffix = "cpp";
		String expected = "./this/is/a/funny/path.cpp";
		String actual = changeType(path, suffix);
		assertEquals(expected, actual);
	}

	@Test
	public void testExists(){
		String path = "src/lp/iem/io/test/IOFileSystemTest.java";
		assertTrue(exists(path));
		
		path = "src/lp/iem/io/test/IOFileSystem.java";
		assertFalse(exists(path));
	}

	@Test
	public void testInfos(){
		String path = "src/lp/iem/io/test/IOFileSystemTest.java";
		File f = new File(path);
		IOInfo in = infos(path);
		assertTrue(in.getTime() == f.lastModified());
		assertTrue(in.getSize() == f.length());
	}

	@Test
	public void testModified(){
		String path = "src/lp/iem/io/test/IOFileSystemTest.java";
		IOInfo info = infos(path);
		assertFalse(modified(path, info));
	}
}
