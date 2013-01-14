package lp.iem.io;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IOFileManager {

	private static IOFileManager INSTANCE;
	
	private Map<String, Integer> mfiles;
	private List<IOFile> files;
	
	public IOFileManager(){
		this.mfiles = new HashMap<String, Integer>();
		this.files = new ArrayList<IOFile>();
	}
	
	/**
	 * return the file description of 'name'
	 * @param name
	 * @return a file description
	 */
	public IOFileInfo file(String name){
		int size = files.size();
		mfiles.put(name, size);
		IOInfo info = IOFileSystem.infos(name);
		files.add(new IOFile(name, info));
		return new IOFileInfo(files.get(size));
	}

	public static boolean modified(IOFileInfo b){
		if(b.getFile() == null) return false;
		return b.getInfo().equals(b.getFile().getInfo());
	}
	
	public static IOFileInfo update(IOFileInfo b) throws Exception{
		if(b.getFile() == null) throw new Exception("b.getFile() is null");
		return new IOFileInfo(b.getFile());
	}
	
	/**
	 * update files descriptors of the app
	 */
	public void reload(){
		for(IOFile iof : files){
			IOInfo info = IOFileSystem.infos(iof.getFileName());
			iof.setInfo(info);
		}
	}

	public static IOFileManager manager(){
		if(INSTANCE == null) INSTANCE = new IOFileManager();
		return INSTANCE;
	}
}
