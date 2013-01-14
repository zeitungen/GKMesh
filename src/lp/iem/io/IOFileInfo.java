package lp.iem.io;

public class IOFileInfo{

	private IOFile file;	// file identifier
	private IOInfo info;	// file version read by app
	
	public IOFileInfo(){
		this.file = null;
		this.info = new IOInfo();
	}
	
	public IOFileInfo(IOFile file, IOInfo info){
		this.file = file;
		this.info = info;
	}
	
	public IOFileInfo(IOFile file){
		this.file = file;
		this.info = new IOInfo();
		if(this.file != null) this.info = this.file.getInfo();
	}
	
	public IOFileInfo(IOFileInfo finfo){
		this.file = finfo.file;
		this.info = finfo.info;
	}
	
	public static IOFileInfo copy(IOFileInfo finfo){ return new IOFileInfo(finfo); }
	
	public boolean isValid(){ return this.file != null; }
	
	public boolean isModified() throws Exception{
		if(!this.isValid()) throw new Exception("file is null");
		return info.equals(file.getInfo());
	}
	
	public void update() throws Exception{
		if(!this.isValid()) throw new Exception("file is null");
		this.info = this.file.getInfo();
	}

	public IOFile getFile(){ return this.file; }
	public IOInfo getInfo(){ return this.info; }
	
	public String getFileName() throws Exception{ 
		if(!this.isValid()) throw new Exception("file is null");
		return this.file.getFileName();
	}
	
	public void setFile(IOFile f){ this.file = f; }	
}
