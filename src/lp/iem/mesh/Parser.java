package lp.iem.mesh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;

import lp.iem.gk.Point2;
import lp.iem.gk.Vector;

public class Parser {

	public static int EOF = -1;
	
	private File in;
	private FileReader fin;
	private String token;

	public Parser(String filename){
		this.in = new File(filename);
		this.fin = null;
		try { this.fin = new FileReader(this.in); } catch (FileNotFoundException e) { }
		this.token = "";
	}
	
	/**
	 * return the last 'word' read by readToken()
	 * @return
	 */
	public String getToken(){ return token; }
	
	/**
	 * return the last char of the last word read by readToken()
	 * @return
	 */
	public char getLastChar(){
		if(token.length() == 0) return 0;
		return token.charAt(token.length()-1);
	}
	
	/**
	 * return a char of the last work read by readToken()
	 * @return
	 */
	public char getChar(int i){ return token.charAt(i); }
	
	/**
	 * convert the last work read by readToken();
	 * @return
	 */
	public float getFloat(){
		return Float.parseFloat(token);
	}
	
	/**
	 * convert the last work read by readToken();
	 * @return
	 */
	public int getInt(){
		String tmp = token;
		if(tmp.endsWith("/")) tmp = token.substring(0, token.length()-1);
		return Integer.parseInt(tmp);
	}
	
 	private static boolean isalnum(String c){
		String str = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		return str.contains(c);
	}
	
	private boolean isToken(char c){
		return isalnum(""+c) || c == '_' || c == '-' || c == '.';
	}
	
	/*
	private void ungetc(char c) throws IOException{
			FileWriter writer = new FileWriter(in, true);
			writer.write(""+c);
			writer.flush();
			writer.close();
	}
	//*/
	
	private int getc() throws IOException{
		int c = fin.read();
		if(c == -1) return EOF;
		return c;
	}
	
	public boolean isValid(){
		return in.exists();
	}

	/**
	 *  Sequential read a 'word' in the file.
	 *  a word is a set of characters delimited by any separators.
	 *  returns the separator, if the reading of a word is impossible.
	 * @return
	 */
	public int readToken(){
		if(!isValid()) return EOF;
		try {
			// pass the white
			int intc = getc();
			if(intc == EOF) return EOF;
			char c = (char)intc;
			while(c == ' ' || c == '\t'){
				intc = getc();
				if(intc == EOF) return EOF;
				c = (char)intc;
			}
			
			if(c == '\r') c = '\n'; // same end of line
			
			token = "";
			while(isToken(c)){
				token += c;
				intc = getc();
				if(intc == EOF) return EOF;
				c = (char)intc;
				if(c == '\r') c = '\n';
			}
			
			if(c == '#'){
				if(token.length() == 0) token += c;
				//else ungetc(c);
			}else if(c == '/') token += c;
			
			// indicate the end of line or file
			//if(token.length() == 0){
				if(intc == EOF) return EOF;
				if(c == '\n') return (int)'\n';
				//else return c;
			//}
			
			//force a end of line before a end of line
			//if(c == -1) ungetc('\n');
			//else if(c == '\n') ungetc(c);	
		} catch (IOException e) { }
		return 0;
	}

	/**
	 *  reading the end of the line, returns the character string.
	 * @return
	 */
	public int readString(){
		String str = "";
		int code = readToken();
		while(code != '\n'){
			str += token;
			if(code != 0) str += code;
			code = readToken();
		}
		
		if(str.length() > 0) token = str;
		
		if(token.length() == 0) return -1;
		else return 0;
	}

	/**
	 * read a vector 3
	 * @return
	 * @throws Exception 
	 */
	public Vector readVector3() throws Exception{
		Vector v = new Vector();
		int i = 0;
		boolean again = true;
		for(i = 0; again; i++){
			if(readToken() == '\n') again = false;
			if(token.length() == 0) continue;
			if(i > 2) throw new Exception("error reading vertex position.");
			float f = getFloat();
			try { v.set(i, f); } catch (Exception e) { }
		}
		if(i < 3) throw new Exception("error reading vertex position.");
		return v;
	}

	/**
	 * read a vector 2
	 * @return
	 * @throws Exception 
	 */
	public Point2 readVector2() throws Exception{
		Point2 p = new Point2();
		int i = 0;
		for(i = 0; readToken() != '\n'; i++){
			if(i > 1) throw new Exception("error reading vector2");
			float f = getFloat();
			if(i == 0) p.setX(f);
			else if(i == 1) p.setY(f);
		}
		if(i < 2) throw new Exception("error reading vector2");
		return p;
	}

	/**
	 * skip a line
	 * read all of the 'word' until the end of line
	 */
	public void skipLine(){
		while(readToken() != '\n') ;
	}

	/**
	 * determine the index of an attribute vertex in the mesh.
	 * @param parser
	 * @param attr
	 * @param attr
	 * @return 0 if not exist, 1 if exist, -1 if error
	 */
	public static int getAttributeIndex(Parser parser, int attr, int attributes_n){  
		if ( parser.getChar( 0 ) == '/' ) return 0;
		try{ attr = parser.getInt(); }
		catch(Exception e){ return -1; }

	    if ( attr < 0 ) attr += attributes_n;
	    else attr -= 1;
   
	    if ( attr < 0 || attr >= attributes_n ) return -1;
	    else  return 1;
	}
	
	/**
	 * 
	 * @param parser
	 * @param attr
	 * @param attributes_n
	 * @return the new attr
	 */
	public static int getAttribute(Parser parser, int attr, int attributes_n){
		
		if ( parser.getChar( 0 ) == '/' ) return attr;
		try{attr = parser.getInt();}
		catch(Exception e){ return attr; }
	       
	    if ( attr < 0 ) attr += attributes_n;
	    else attr -= 1;
	    
	    return attr;
	}
}
