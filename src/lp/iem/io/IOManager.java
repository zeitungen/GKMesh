package lp.iem.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IOManager<E> {
	
	private Map<Name, Integer> names;
	private Map<E, Integer> objects;	
	private List<Info<E>> infos;
	
	public IOManager(){
		this.names = new HashMap<Name, Integer>();
		this.objects = new HashMap<E, Integer>();
		this.infos = new ArrayList<Info<E>>();
	}
	
	protected Info<E> findInfo(String path, String name){
		Set<Name> sn = names.keySet();
		Iterator<Name> isn = sn.iterator();
		while(isn.hasNext()){
			Name n = isn.next();
			if(path.equals(n.filename) && name.equals(n.name)){
				Integer i = names.get(n);
				return infos.get(i.intValue());
			}
		}
		return null;
	}
	
	protected Info<E> findInfo(E obj){
		Set<E> sn = objects.keySet();
		Iterator<E> isn = sn.iterator();
		while(isn.hasNext()){
			E n = isn.next();
			if(obj.equals(n)){
				Integer i = names.get(n);
				return infos.get(i.intValue());
			}
		}
		return null;
	}
	
	public E insert(E obj, IOFileInfo handle, String name) throws Exception{
		if(obj == null) return null;
		int pos = infos.size();
		names.put(new Name(handle.getFileName(), name), pos);
		objects.put(obj, pos);
		infos.add(new Info<E>(new IOName(handle, name), obj));
		return obj;
	}
	
	public E insert(E obj, IOFileInfo handle) throws Exception{
		return this.insert(obj, handle, "");
	}
	
	public E insert(E obj, String filename, String name) throws Exception{
		IOFileInfo handle = IOFileManager.manager().file(filename);
		if(!handle.isValid()) throw new Exception("handle is not valid");
		return insert(obj, handle, name);
	}
	
	public E insert(E obj, String filename) throws Exception{
		return insert(obj, filename, "");
	}

	public E find(IOFileInfo handle, String name) throws Exception{
		Info<E> info = findInfo(handle.getFileName(), name);
		return (info == null) ? null : info.obj;
	}
	
	public E find(IOFileInfo handle) throws Exception{
		return find(handle, "");
	}
	
	public E find(String filename, String name){
		Info<E> info = findInfo(filename, name);
		return (info == null) ? null : info.obj;
	}
	
	public E find(String filename){
		return find(filename, "");
	}

	public IOName find(E obj){
		Info<E> info = findInfo(obj);
		return (info == null) ? null : info.name;
	}
	
	public boolean modified(E obj) throws Exception{
		Info<E> info = findInfo(obj);
		if(info == null && !info.name.getHandle().isValid())
			return false;
		return info.getName().getHandle().isModified();
	}
	
	public boolean modified(String filename, String name) throws Exception{
		Info<E> info = findInfo(filename, name);
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
		
		public boolean inferior(Name b){
			int cmp = filename.compareTo(b.filename);
			if(cmp < 0) return true;
			else if(cmp > 0) return false;
			else return name.compareTo(b.name) < 0;
		}
	}

	public class Info<E>{
		private IOName name;
		private E obj;
		
		public Info(IOName name, E object){
			this.name = name;
			this.obj = object;
		}
	
		public IOName getName(){ return name; }
		public E getObject(){ return obj; }
	}
}
