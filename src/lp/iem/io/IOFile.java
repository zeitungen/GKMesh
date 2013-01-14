package lp.iem.io;

public class IOFile {

	private String fileName;
	private IOInfo info;
	
	public IOFile(){
		this.fileName = "";
		this.info = new IOInfo();
	}
	
	public IOFile(String fileName, IOInfo info){
		this.fileName = fileName;
		this.info = info;
	}
	
	public IOFile(IOFile f){
		this.fileName = f.fileName;
		this.info = f.info;
	}
	
	public String getFileName(){ return fileName; }
	public IOInfo getInfo(){ return info; }
	
	public void setInfo(IOInfo info){ this.info = info; }
	
	public static IOFile copy(IOFile f){ return new IOFile(f); }
}
