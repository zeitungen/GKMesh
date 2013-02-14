package lp.iem.io;

public class IOName {

	private IOFileInfo handle;
	private String name;
	
	public IOName(){
		this.name = "";
		this.handle = new IOFileInfo();
	}
	
	public IOName(IOFileInfo handle, String name){
		this.name = name;
		this.handle = handle;
	}
	
	public IOFileInfo getHandle(){ return this.handle; }
	public String getName(){ return this.name; }
	public String getFileName() throws Exception{ return this.handle.getFileName(); }
	
	/**
	 * comparison of the name only
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public boolean inferior(IOName n) throws Exception{
		if(this.handle.getFile() == null) throw new Exception("handle.file is null");
		if(n.handle.getFile() == null) throw new Exception("n.handle.file is null");
		
		int cmp = this.handle.getFile().getFileName().compareTo(n.handle.getFile().getFileName());
		if(cmp < 0) return true;
		else if(cmp > 0) return false;
		
		cmp = this.name.compareTo(n.name);
		return cmp < 0;
	}

}
