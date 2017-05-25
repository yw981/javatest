import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int t = sc.nextInt();
		Point[] lr = new Point[n];
		for(int i=0;i<n;i++){
			int tx = sc.nextInt();
			int ty = sc.nextInt();
			lr[i] = new Point(tx,ty);
		}
		ArrayList<Point>[] lp = new ArrayList[t];
		for(int i=0;i<t;i++){
			lp[i] = new ArrayList<Point>();
			for(int j=0;j<n;j++){
				int tx = sc.nextInt();
				int ty = sc.nextInt();
				lp[i].add( new Point(tx,ty));
			}
		}
		
		int cost = 0;
		
		for(int i=0;i<t;i++){
			cost += cal(lr,lp[i]);
		}
		
		
		
		System.out.println(cost + " " );
		sc.close();
	}
	
	private int cal(Point[] lr, ArrayList<Point> lp) {
		int l = lr.length;
		int calcost = 0;
		int[][] costtable = new int[l][lp.size()];
		for(int i=0;i<l;i++){
			int minid = 0;
			int mindis = 2147483647;
			for(int j=0;j<lp.size();j++){
				int caldis = dis(lr[i],lp.get(j));
				if(caldis<mindis){
					minid = j;
					mindis = caldis;
				}
			}
			calcost += mindis;
			lr[i].x = lp.get(minid).x;
			lr[i].y = lp.get(minid).y;
			lp.remove(minid);
		}
		
//		for(int i=0;i<l;i++){
//			int minid = 0;
//			int mindis = 2147483647;
//			for(int j=0;j<lp.size();j++){
//				int caldis = dis(lr[i],lp.get(j));
//				if(caldis<mindis){
//					minid = j;
//					mindis = caldis;
//				}
//			}
//			calcost += mindis;
//			lr[i].x = lp.get(minid).x;
//			lr[i].y = lp.get(minid).y;
//			lp.remove(minid);
//		}
		
		return calcost;
	}
	
	private int dis(Point a,Point b){
		return Math.abs(a.x-b.x)+Math.abs(a.y-b.y);
	}

	public class Point {
		int x;
		int y;
		Point(int x,int y){
			this.x = x;
			this.y = y;
		}
	}

}