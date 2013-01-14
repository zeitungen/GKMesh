package lp.iem.io;

import java.io.File;

public abstract class IOFileSystem {

	/**
	 * return the path access of a file always end by a "/" or "\"
	 * @param pathname
	 * @return return the parent folder path
	 */
	public static String pathname(String pathname){
		File f = new File(pathname);
		String slash = "/";
		String oldslash = "\\";
		if(!pathname.contains(slash)){
			slash = "\\";
			oldslash = "/";
			if(!pathname.contains(slash))
				return "./";
		}
		
		String parent = f.getParent();
		if(parent.contains(slash)) return parent + slash;
		else return parent.replace(oldslash, slash) + slash;
	}

	/**
	 * return the path file without the extension
	 * @param pathname
	 * @return
	 */
	public static String basename(String pathname){
		int pos = pathname.indexOf(".");
		if(pos == 0){
			String tmp = pathname.substring(1);
			pos = tmp.indexOf(".");
			if(pos >= 0) pos++;
		}
		if(pos < 0) return new String(pathname);
		return pathname.substring(0, pos);
	}

	/**
	 * return the extension of a file
	 * @param pathname
	 * @return file extension
	 */
	public static String getSuffix(String pathname){
		int pos = pathname.indexOf(".");
		if(pos == 0){
			String tmp = pathname.substring(1);
			pos = tmp.indexOf(".");
			if(pos >= 0) pos++;
		}
		if(pos < 0) return "";
		return pathname.substring(pos+1, pathname.length());
	}
	
	/**
	 * check the file is a suffix type
	 * @param filename
	 * @param suffix
	 * @return
	 */
	public static boolean isType(String filename, String suffix){
		if(!suffix.startsWith(".")) suffix = "." + suffix;
		return filename.endsWith(suffix);
	}

	/**
	 * change the file extension of the file
	 * @param filename
	 * @param suffix
	 * @return the new file path
	 */
	public static String changeType(String filename, String suffix){
		String newpath = basename(filename);
		if(!suffix.startsWith(".")) suffix = "." + suffix;
		return newpath + suffix;
	}

	/**
	 * check the existence of a file 
	 * @param path
	 * @return
	 */
	public static boolean exists(String path){ return new File(path).exists(); }

	/**
	 * 
	 * @param path
	 * @return 
	 */
	public static IOInfo infos(String path){
		File f = new File(path);
		IOInfo info = new IOInfo();
		info.setSize(f.length());
		info.setTime(f.lastModified());
		return info;
	}

	/**
	 * the file has been modified since it was read 
	 * @param path
	 * @param info
	 * @return
	 */
	public static boolean modified(String path, IOInfo info){
		IOInfo inf = infos(path);
		return inf.getTime() != info.getTime();
	}
}
