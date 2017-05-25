package spider;
import java.io.File;

public class TraverseFile {
	
	// 遍历删除无用的字幕文件
	public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        
                        String fileName = file2.getName();
                        String l1 = fileName.substring(fileName.lastIndexOf('.'));
                        String fn = fileName.substring(0,fileName.lastIndexOf('.'));
                        if(fn.lastIndexOf('.')>0){
                        	String l2 = fn.substring(fn.lastIndexOf('.'));
                        	if(l1.equals(".txt")||l1.equals(".srt")){
                        		if(!l2.equals(".en")&&!l2.equals(".zh-CN")){
                        			file2.delete();
                        			System.out.println("删除文件:" + file2.getName());
                        		}
                        	}
  
                            
                        }
                        
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TraverseFile tf = new TraverseFile();
		tf.traverseFolder2("C:\\PROJECT\\Python\\Cryptocurrency 普林斯顿大学");
	}

}
