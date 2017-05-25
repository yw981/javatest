package poj1568;

import java.util.Scanner;

public class Main {

	private static final int INF = 1;
	
	private char[][] board;
	
	private int x;
	private int y;
	
	public Main() {
		board = new char[5][5];
		Scanner in = new Scanner(System.in);
		while(!in.nextLine().equals("$")){
			
			for(int i=0;i<4;i++){
				String line = in.nextLine();
				board[i] = line.toCharArray();
			}
			if(search()){
				System.out.println("("+x+","+y+")");
			}
			else{
				System.out.println("#####");
			}
		}
		in.close();
		
	}
	
	private boolean search(){
//		int pieces = 0; // 棋子数
//		for(int j=0;j<4;j++)
//			for(int k=0;k<4;k++)
//				if(board[j][k]!='.')
//					pieces++;
	
		for(int j=0;j<4;j++){
			for(int k=0;k<4;k++){
				if(board[j][k]=='.'){
					board[j][k]='x';
					// TODO 层数 至多 15-pieces
					int value = minimaxWithAlphaBetaPruning(8,-INF,INF,false);
					board[j][k]='.';
					if(value==INF){
						x=j;
						y=k;
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
	private int check(){
		//横向
		for(int i=0;i<4;i++){
			if(board[i][0]!='.'
					&&board[i][0]==board[i][1]
					&&board[i][0]==board[i][2]
					&&board[i][0]==board[i][3]){
				return board[i][0]=='x'?INF:-INF;
			}
		}
		//纵向
		for(int i=0;i<4;i++){
			if(board[0][i]!='.'
					&&board[0][i]==board[1][i]
					&&board[0][i]==board[2][i]
					&&board[0][i]==board[3][i]){
				return board[0][i]=='x'?INF:-INF;
			}
		}
		//斜向
		if(board[0][0]!='.'
			&&board[1][1]==board[0][0]
			&&board[2][2]==board[0][0]
			&&board[3][3]==board[0][0]){
			return board[0][0]=='x'?INF:-INF;
		}
		if(board[0][3]!='.'
			&&board[1][2]==board[0][3]
			&&board[2][1]==board[0][3]
			&&board[3][0]==board[0][3]){
			return board[0][3]=='x'?INF:-INF;
		}
		return 0;
	}
	
	/*
	 * player true 为 x
	 */
	public int minimaxWithAlphaBetaPruning(int depth,int alpha,int beta,boolean player){
		//System.out.println(check());
		int evaluate = check();
		if(depth==0 || evaluate!=0) return evaluate;
		if(player){
			// x MAX
			alpha = -INF;
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(board[i][j]=='.'){
						board[i][j]='x';
						int value = minimaxWithAlphaBetaPruning(depth-1,alpha,beta,!player);
						board[i][j]='.';
						if(value>alpha) alpha = value;
						if(beta<=alpha) return alpha;
					}
				}
			}
		}
		else{
			// o min
			beta = INF;
			for(int i=0;i<4;i++){
				for(int j=0;j<4;j++){
					if(board[i][j]=='.'){
						board[i][j]='o';
						int value = minimaxWithAlphaBetaPruning(depth-1,alpha,beta,!player);
						board[i][j]='.';
						if(value<beta) beta = value;
						if(beta<=alpha) return beta;
					}
				}
			}
		}
		return player?alpha:beta;
	}

	public static void main(String[] args) {
		Main m = new Main();
	}
}
