package lp.iem.io;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressLint("UseSparseArrays")
public class IOManager {
	
	private Map<IOManager.Name, Integer> names;
	private Map<Object, Integer> objects;	
	private List<IOManager.Info> infos;
	
	public IOManager(){
		this.names = new HashMap<IOManager.Name, Integer>();
		this.objects = new HashMap<Object, Integer>();
		this.infos = new ArrayList<IOManager.Info>();
	}
	
	protected IOManager.Info findInfo(String path, String name){
		Set<IOManager.Name> sn = names.keySet();
		Iterator<IOManager.Name> isn = sn.iterator();
		while(isn.hasNext()){
			IOManager.Name n = isn.next();
			if(path.equals(n.filename) && name.equals(n.name)){
				Integer i = names.get(n);
				return infos.get(i.intValue());
			}
		}
		return null;
	}
	
	protected IOManager.Info findInfo(Object obj){
		Set<Object> sn = objects.keySet();
		Iterator<Object> isn = sn.iterator();
		while(isn.hasNext()){
			Object n = isn.next();
			if(obj.equals(n)){
				Integer i = names.get(n);
				return infos.get(i.intValue());
			}
		}
		return null;
	}
	
	public Object insert(Object obj, IOFileInfo handle, String name) throws Exception{
		if(obj == null) return null;
		int pos = infos.size();
		names.put(new IOManager.Name(handle.getFileName(), name), pos);
		objects.put(obj, pos);
		infos.add(new IOManager.Info(new IOName(handle, name), obj));
		return obj;
	}
	
	public Object insert(Object obj, IOFileInfo handle) throws Exception{
		return this.insert(obj, handle, "");
	}
	
	public Object insert(Object obj, String filename, String name) throws Exception{
		IOFileInfo handle = IOFileManager.manager().file(filename);
		if(!handle.isValid()) throw new Exception("handle is not valid");
		return insert(obj, handle, name);
	}
	
	public Object insert(Object obj, String filename) throws Exception{
		return insert(obj, filename, "");
	}

	public Object find(IOFileInfo handle, String name) throws Exception{
		IOManager.Info info = findInfo(handle.getFileName(), name);
		return (info == null) ? null : info.obj;
	}
	
	public Object find(IOFileInfo handle) throws Exception{
		return find(handle, "");
	}
	
	public Object find(String filename, String name){
		IOManager.Info info = findInfo(filename, name);
		return (info == null) ? null : info.obj;
	}
	
	public Object find(String filename){
		return find(filename, "");
	}

	public IOName find(Object obj){
		IOManager.Info info = findInfo(obj);
		return (info == null) ? null : info.name;
	}
	
	public boolean modified(Object obj) throws Exception{
		IOManager.Info info = findInfo(obj);
		if(info == null && !info.name.getHandle().isValid())
			return false;
		return info.getName().getHandle().isModified();
	}
	
	public boolean modified(String filename, String name) throws Exception{
		IOManager.Info info = findInfo(filename, name);
		if(info == null && !info.name.getHandle().isValid())
			return false;
		return info.getName().getHandle().isModified();
	}
	
	public class Name{
		private String filename;
		private String name;
		
		public Name(String file, String name){
			this.filename = file;
			this.name = name;
		}
		
		public String getFileName(){ return filename; }
		public String getName(){ return name; }
		
		public void setFileName(String n){ filename = n; }
		public void seName(String n){ name = n; }
		
		public boolean inferior(IOManager.Name b){
			int cmp = filename.compareTo(b.filename);
			if(cmp < 0) return true;
			else if(cmp > 0) return false;
			else return name.compareTo(b.name) < 0;
		}
	}

	public class Info{
		private IOName name;
		private Object obj;
		
		public Info(IOName name, Object object){
			this.name = name;
			this.obj = object;
		}
	
		public IOName getName(){ return name; }
		public Object getObject(){ return obj; }
	}
}
