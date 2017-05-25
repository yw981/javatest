/*
 Hiho #1304 : 搜索一·24点
时间限制:10000ms
单点时限:1000ms
内存限制:256MB
描述

周末，小Hi和小Ho都在家待着。

在收拾完房间时，小Ho偶然发现了一副扑克，于是两人考虑用这副扑克来打发时间。

小Ho：玩点什么好呢？

小Hi：两个人啊，不如来玩24点怎么样，不靠运气就靠实力的游戏。

小Ho：好啊，好啊。

<经过若干局游戏之后>

小Ho：小Hi，你说如果要写个程序来玩24点会不会很复杂啊？

小Hi：让我想想。

<过了几分钟>

小Hi：我知道了！其实很简单嘛。

提示：24点

输入

第1行：1个正整数, t，表示数据组数，2≤t≤100。

第2..t+1行：4个正整数, a,b,c,d，1≤a,b,c,d≤10。

输出

第1..t行：每行一个字符串，第i行表示第i组能否计算出24点。若能够输出"Yes"，否则输出"No"。

样例输入
2
5 5 5 1
9 9 9 9
样例输出
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
			// 数字枚举完，该枚举符号
			// 先输出看看
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
			// 此时已经枚举完a,b,c,d和三个运算符
			// 计算在(((a ⊙ b) ⊙ c ) ⊙ d)形式下的值
			try{
				if(Math.abs(calcType1() - 24)<THRESHOLD){
					// ********* 计算结果输出在这里
					//System.out.print("caling1 : ");
					showNow();
					return true;
				}
			}
			catch(java.lang.ArithmeticException e){
				return false;
			}
			// 计算在((a ⊙ b) ⊙ (c ⊙ d))形式下的值
			try{
				if(Math.abs(calcType2() - 24)<THRESHOLD){
					// ********* 计算结果输出在这里
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
	
	// 计算在(((a ⊙ b) ⊙ c ) ⊙ d)形式下的值
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
	
	// 计算在((a ⊙ b) ⊙ (c ⊙ d))形式下的值
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

	//(a ⊙ b) ⊙ (c ⊙ d)
	public void showNow(){
		// (((a ⊙ b) ⊙ c ) ⊙ d)
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
			// 交换
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
提示：24点
小Hi：小Ho，你仔细观察我们计算24点的方法，来总结有几种情况。

假设我们用⊙表示运算，⊙除了可以表示基本的"+","-","*","/"外。我们还引入两个新的运算，"反-",和"反/"。

比如(a 反/ b)的意思是(b / a)。则对形如(c / (a + b))的形式，就可以等价的描述为((a + b) 反/ c)。

利用这6种运算，可以将所有可能的计算过程归结为2类：

(((a ⊙ b) ⊙ c ) ⊙ d)
((a ⊙ b) ⊙ (c ⊙ d))
小Ho：恩..(小Ho思考了一下)..好像确实是这样。

小Hi：既然我们已经找到了固定的模式，那么剩下的就比较简单了。

将4张牌的值，分别代入a,b,c,d，再把可能的运算符也代入。就可以得到相应的计算式子，将其计算出来，再检查结果是否等于24。

那么小Ho，你觉得有多少种情况呢？

小Ho：由于我们有4个数，所以对于a,b,c,d的对应关系有4!=24种情况。3个运算符，每个运算符可能有6种情况，那就是6^3=216。再考虑到2种不同的模式，所以一共有2 * 24 * 216 = 10368种情况。

小Hi：你的计算中并没有考虑等价的情况，比如a + b 和 b + a，所以实际的情况数其实是小于10368种的。

不过由于对计算机而言，10368种情况数本来也不是很多，而要考虑等价反而显得比较麻烦。所以我们可以不要去考虑加法和乘法的可逆性，直接枚举所有的情况。

那么最后还是由小Ho你来给出参考的伪代码吧。

小Ho：嗯，这次的伪代码：

used[] = false
nowNumber[] = {0,0,0,0}
ops[] = {0,0,0}
opType = {+,-,*,/,反-,反/}

makeNumber(depth):
	If (depth >= 4) Then
		// 此时已经枚举完a,b,c,d
		// 开始枚举运算符
		Return makeOperation(0)
	End If
	For i = 1 .. 4
		If (not used[i]) Then	// 每个数字只能使用一次
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
		// 此时已经枚举完a,b,c,d和三个运算符
		// 计算在(((a ⊙ b) ⊙ c ) ⊙ d)形式下的值
		If (calcType1(nowNumber, ops) == 24) Then
			Return true;
		End If
		// 计算在((a ⊙ b) ⊙ (c ⊙ d))形式下的值
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