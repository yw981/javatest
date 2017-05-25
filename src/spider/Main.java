package spider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Main {
	public static void main(String[] args) throws IOException {
		String fn = "1.html";
		File output = new File("output.html");
		OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(output, true), "utf-8");
        
        System.out.print("o");
        
		File f = new File(fn);
		FileInputStream fis = new FileInputStream(f);
		//fis.read()
		//Scanner s = new Scanner(f);
		String str = InputStream2String(fis, "utf-8");
		//System.out.println(str);
		int start = str.indexOf("<tbody>");
		int end = str.indexOf("</tbody>");
		//s.close();
		System.out.println("pos "+start+" "+end);
		outs.write(str.substring(start+7, end));
		fis.close();
		outs.close();
	}
	
	public static String InputStream2String(InputStream in_st,String charset) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
            res.append(line);
        }
        return res.toString();
    }


	
}