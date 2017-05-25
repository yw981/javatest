package poj3278;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	public static final int MAX = 100000;
	
	private Queue<Step> open;
	private boolean book[];
	public int start;
	public int goal;

	public Main() {
		open = new LinkedList<Step>();
		book = new boolean[MAX+1];
	}
	
	private class Step {
		public int pos;
		public int steps;
		
		public Step(int p,int s){
			pos = p;
			steps = s;
		}
	}

	private void search() {
		open.offer(new Step(start,0));
		Step cur;
		while((cur = open.poll()) != null){
			//System.out.println(cur.pos+","+cur.steps);
			book[cur.pos] = true;
			if(cur.pos == goal){
				System.out.println(""+cur.steps);
				return;
			}
			if(cur.pos+1<=MAX && !book[cur.pos+1]) open.offer(new Step(cur.pos+1,cur.steps+1));
			if(cur.pos-1>=0 && !book[cur.pos-1]) open.offer(new Step(cur.pos-1,cur.steps+1));
			if(cur.pos<=MAX/2 && !book[cur.pos*2]) open.offer(new Step(cur.pos*2,cur.steps+1));
		}
		
		
	}
	

	public static void main(String[] args) {
		Main bfs = new Main();
		Scanner in = new Scanner(System.in);
		//bfs.start = 3;
		//bfs.goal = 5;
		bfs.start = in.nextInt();
		bfs.goal = in.nextInt();
		bfs.search();
		in.close();
	}
}
