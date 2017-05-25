/*
 Hiho #1304 : ����һ��24��
ʱ������:10000ms
����ʱ��:1000ms
�ڴ�����:256MB
����

��ĩ��СHi��СHo���ڼҴ��š�

����ʰ�귿��ʱ��СHożȻ������һ���˿ˣ��������˿������⸱�˿�����ʱ�䡣

СHo�����ʲô���أ�

СHi�������˰�����������24����ô�������������Ϳ�ʵ������Ϸ��

СHo���ð����ð���

<�������ɾ���Ϸ֮��>

СHo��СHi����˵���Ҫд����������24��᲻��ܸ��Ӱ���

СHi���������롣

<���˼�����>

СHi����֪���ˣ���ʵ�ܼ��

��ʾ��24��

����

��1�У�1��������, t����ʾ����������2��t��100��

��2..t+1�У�4��������, a,b,c,d��1��a,b,c,d��10��

���

��1..t�У�ÿ��һ���ַ�������i�б�ʾ��i���ܷ�����24�㡣���ܹ����"Yes"���������"No"��

��������
2
5 5 5 1
9 9 9 9
�������
Yes
No
 */
package javatest.search24;
import java.util.Arrays;
import java.util.Scanner;
public class Main {
	
	private static final int DEPTH = 4;
	private static final float THRESHOLD = 0.0001f;
	private int[] numbers;
	private boolean[] book;
	private int[] nowNumber;
	private int[] nowOps;
	//{"+","-","*","/","s-","s/"}
	private static final String[] OPERATOR_NAMES = {"+","-","*","/","-","/"};
	
	public Main(int[] nums){
		setNumbers(nums);
		book = new boolean[4];
		nowNumber = new int[4];
		nowOps = new int[3];
	}
	
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	public boolean calculate(){
		Arrays.fill(book,false);
		return makeNumber(0);		
	}
	
	public void showNum(){
		for(int i=0;i<numbers.length;i++){
			System.out.print(numbers[i]+"");
		}
		System.out.println();
	}
	
	public boolean makeNumber(int depth){
		if(depth>=DEPTH){
			// ����ö���꣬��ö�ٷ���
			// ���������
			//showNow();
			return makeOps(0);
		}
		else{
			for(int i=0;i<DEPTH;i++){
				if(!book[i]){
					nowNumber[depth] = numbers[i];
					book[i]=true;
					if(makeNumber(depth+1)) return true;
					book[i]=false;
				}
			}
		}
		return false;
		
	}
	
	public boolean makeOps(int depth){
		if(depth>=3){
			// ��ʱ�Ѿ�ö����a,b,c,d�����������
			// ������(((a �� b) �� c ) �� d)��ʽ�µ�ֵ
			try{
				if(Math.abs(calcType1() - 24)<THRESHOLD){
					// ********* ���������������
					//System.out.print("caling1 : ");
					showNow();
					return true;
				}
			}
			catch(java.lang.ArithmeticException e){
				return false;
			}
			// ������((a �� b) �� (c �� d))��ʽ�µ�ֵ
			try{
				if(Math.abs(calcType2() - 24)<THRESHOLD){
					// ********* ���������������
					//System.out.print("caling2 : ");
					showNow2();
					return true;
				}
			}
			catch(java.lang.ArithmeticException e){
				return false;
			}
		}
		else{
			for(int i=0;i<6;i++){
				nowOps[depth] = i;
				if(makeOps(depth+1)) return true;
			}
		}		
		return false;		
	}
	
	// ������(((a �� b) �� c ) �� d)��ʽ�µ�ֵ
	private float calcType1() {
		return cal(nowOps[2],
			cal(nowOps[1],
				cal(nowOps[0],
					(float) nowNumber[0],
					(float) nowNumber[1]
				),
				(float) nowNumber[2]
			),
			(float) nowNumber[3]
		);
	}
	
	// ������((a �� b) �� (c �� d))��ʽ�µ�ֵ
	private float calcType2() {
		return cal(
			nowOps[1],
			cal(nowOps[0],(float) nowNumber[0],(float) nowNumber[1]),
			cal(nowOps[2],(float) nowNumber[2],(float) nowNumber[3])
		);
	}
	
	//{"+","-","*","/","s-","s/"}
	private float cal(int op,float a,float b){
		switch(op){
		case 1:
			return a-b;
		case 2:
			return a*b;
		case 3:
			return a/b;
		case 4:
			return b-a;
		case 5:
			return b/a;
		default:
			return a+b;
		}
	}

	//(a �� b) �� (c �� d)
	public void showNow(){
		// (((a �� b) �� c ) �� d)
		String num1 = nowNumber[0]+"";
		String num2;
		for(int i=0;i<nowOps.length;i++){
			num2 = nowNumber[i+1]+"";
			num1 = getNote(nowOps[i],num1,num2);
		}
		System.out.println(num1);
	}
	
	public void showNow2(){
		String left = getNote(nowOps[0],nowNumber[0]+"",nowNumber[1]+"");
		String right = getNote(nowOps[2],nowNumber[2]+"",nowNumber[3]+"");
		System.out.println(getNote(nowOps[1],left,right));
	}
	
	public String getNote(int op,String a,String b){
		switch(op){
		case 4:
		case 5:
			// ����
			return "("+b+OPERATOR_NAMES[op]+a+")";
		default:
			return "("+a+OPERATOR_NAMES[op]+b+")";
		}
	}
	
	public static void main(String[] args){
		int[] nums = new int[4];
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		Main m = new Main(nums);
		while(t>0){
			for(int i=0;i<4;i++){
				nums[i] = sc.nextInt();
			}
			//long startTime = System.currentTimeMillis();
			m.setNumbers(nums);
			if(m.calculate()){
				System.out.println("Yes");
			}
			else{
				System.out.println("No");
			}
			t--;
			//System.out.println("Time Cost : "+(System.currentTimeMillis()-startTime)+" ms");
		}
		sc.close();
	}
}

/*
��ʾ��24��
СHi��СHo������ϸ�۲����Ǽ���24��ķ��������ܽ��м��������

���������áѱ�ʾ���㣬�ѳ��˿��Ա�ʾ������"+","-","*","/"�⡣���ǻ����������µ����㣬"��-",��"��/"��

����(a ��/ b)����˼��(b / a)���������(c / (a + b))����ʽ���Ϳ��Եȼ۵�����Ϊ((a + b) ��/ c)��

������6�����㣬���Խ����п��ܵļ�����̹��Ϊ2�ࣺ

(((a �� b) �� c ) �� d)
((a �� b) �� (c �� d))
СHo����..(СHo˼����һ��)..����ȷʵ��������

СHi����Ȼ�����Ѿ��ҵ��˹̶���ģʽ����ôʣ�µľͱȽϼ��ˡ�

��4���Ƶ�ֵ���ֱ����a,b,c,d���ٰѿ��ܵ������Ҳ���롣�Ϳ��Եõ���Ӧ�ļ���ʽ�ӣ��������������ټ�����Ƿ����24��

��ôСHo��������ж���������أ�

СHo������������4���������Զ���a,b,c,d�Ķ�Ӧ��ϵ��4!=24�������3���������ÿ�������������6��������Ǿ���6^3=216���ٿ��ǵ�2�ֲ�ͬ��ģʽ������һ����2 * 24 * 216 = 10368�������

СHi����ļ����в�û�п��ǵȼ۵����������a + b �� b + a������ʵ�ʵ��������ʵ��С��10368�ֵġ�

�������ڶԼ�������ԣ�10368�����������Ҳ���Ǻܶ࣬��Ҫ���ǵȼ۷����ԵñȽ��鷳���������ǿ��Բ�Ҫȥ���Ǽӷ��ͳ˷��Ŀ����ԣ�ֱ��ö�����е������

��ô�������СHo���������ο���α����ɡ�

СHo���ţ���ε�α���룺

used[] = false
nowNumber[] = {0,0,0,0}
ops[] = {0,0,0}
opType = {+,-,*,/,��-,��/}

makeNumber(depth):
	If (depth >= 4) Then
		// ��ʱ�Ѿ�ö����a,b,c,d
		// ��ʼö�������
		Return makeOperation(0)
	End If
	For i = 1 .. 4
		If (not used[i]) Then	// ÿ������ֻ��ʹ��һ��
			nowNumber[ depth ] = number[i]
			used[i] = true
			If (makeNumber(depth + 1)) Then
				Return True
			End If
			used[i] = false
		End If
	End For
	Return False
	
makeOperation(depth):
	If (depth >= 3) Then
		// ��ʱ�Ѿ�ö����a,b,c,d�����������
		// ������(((a �� b) �� c ) �� d)��ʽ�µ�ֵ
		If (calcType1(nowNumber, ops) == 24) Then
			Return true;
		End If
		// ������((a �� b) �� (c �� d))��ʽ�µ�ֵ
		If (calcType2(nowNumber, ops) == 24) Then
			Return true;
		End If
		Return false
	End If
	For i = 1 .. 6
		ops[ depth ] = opType[i]
		If (makeOperation(depth + 1)) Then
			Return True
		End If
	End For
	Return False

Main:
	input(number)
	used[] = false
	makeNumber(0)
*/