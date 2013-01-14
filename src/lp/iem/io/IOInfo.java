package lp.iem.io;

public class IOInfo {

	private long size;	// length in byte
	private long time;	// file date
	
	public IOInfo(){
		this.size = 0;
		this.time = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        
        final IOInfo i = (IOInfo) o;
        return this.size == i.size && this.time == i.time;  
	}
	
	public long getSize() { return size; }
	public long getTime() { return time; }

	public void setSize(long size) { this.size = size; }
	public void setTime(long time) { this.time = time; }
}
