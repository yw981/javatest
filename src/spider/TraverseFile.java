package spider;
import java.io.File;

public class TraverseFile {
	
	// ����ɾ�����õ���Ļ�ļ�
	public void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("�ļ����ǿյ�!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("�ļ���:" + file2.getAbsolutePath());
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
                        			System.out.println("ɾ���ļ�:" + file2.getName());
                        		}
                        	}
  
                            
                        }
                        
                    }
                }
            }
        } else {
            System.out.println("�ļ�������!");
        }
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TraverseFile tf = new TraverseFile();
		tf.traverseFolder2("C:\\PROJECT\\Python\\Cryptocurrency ����˹�ٴ�ѧ");
	}

}
