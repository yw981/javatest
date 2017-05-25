package alphabeta;
import java.util.Scanner;

public class AlphaBeta {
	// 默认搜索深度
	private static final int DEPTH = 3;
	private static final int WIDTH = 8;
	private static final int INF = 99999999;
	
	private char[][] board;
	
	private boolean isBlack;;
	
	public AlphaBeta() {
		board = new char[WIDTH+1][WIDTH+1];
		newGame();
		
//		board[1][2]='x';
//		board[2][1]='x';
//		board[2][2]='x';
//		board[2][3]='x';
//		board[3][0]='x';
//		board[0][0]='o';
//		board[1][1]='o';
//		board[3][1]='o';
//		board[3][2]='o';
//		board[3][3]='o';
//		if(search()){
//			System.out.println("("+x+","+y+")");
//		}
//		else{
//			System.out.println("#####");
//		}
//		Scanner in = new Scanner(System.in);
//		while(!in.nextLine().equals("$")){
//			
//			for(int i=0;i<4;i++){
//				String line = in.nextLine();
//				board[i] = line.toCharArray();
//			}
//			if(search()){
//				System.out.println("("+x+","+y+")");
//			}
//			else{
//				System.out.println("#####");
//			}
//		}
//		in.close();
		
	}
	
	private int bestValue;
	private int bestX;
	private int bestY;
	
	private void search(){
		bestValue = isBlack?-INF:INF;
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				if(isEmpty(i, j)){
					placeStone(false, i, j);
					int value = minimaxWithAlphaBetaPruning(DEPTH,-INF,INF,true);
					removeStone(i,j);
					//System.out.println(j+","+k+" "+value);
					if((isBlack&&value>bestValue)||(!isBlack&&value<bestValue)){
						bestX=i;
						bestY=j;
						bestValue = value;
						//return true;
					}
					//System.out.println(bestX+","+bestY+" "+bestValue);
				}
				
			}
		}
	}
	
	public boolean isGameOver() {
		int value = evaluate();
		return value>=INF || value<=-INF;
	}
	
	
	private static final int[] VALUES = {0,10,40,320,5120,INF};
	/* 
	 * 评估函数 b+ w-
	 * 5 99999999
	 * 4 5120
	 * 3 320
	 * 2 40
	 * 1 10
	 */
	private int evaluate(){
		int value = 0;
		for(int i=0;i<WIDTH;i++){
			for(int j=0;j<WIDTH;j++){
				if(j<WIDTH-4) value += evaluateHorizontally(i, j);
				if(i<WIDTH-4) value += evaluateVertically(i, j);
				if(i<WIDTH-4&&j<WIDTH-4){
					value += evaluateDiagonally(i, j);
					value += evaluateDiagonallyTopRight(i, j);
				}
			}
		}
		return value;
	}
	
	private int evaluateHorizontally(int x,int y){
		int countBlack = 0,countWhite = 0;
		for(int i=y;i<y+5;i++){
			if(board[x][i]=='b') countBlack++;
			if(board[x][i]=='w') countWhite++;
		}
		if(countBlack==0) return -VALUES[countWhite];
		if(countWhite==0) return VALUES[countBlack];
		return 0;
	}
	
	private int evaluateVertically(int x,int y){
		int countBlack = 0,countWhite = 0;
		for(int i=x;i<x+5;i++){
			if(board[i][y]=='b') countBlack++;
			if(board[i][y]=='w') countWhite++;
		}
		if(countBlack==0) return -VALUES[countWhite];
		if(countWhite==0) return VALUES[countBlack];
		return 0;
		
	}
	
	private int evaluateDiagonally(int x,int y){
		int countBlack = 0,countWhite = 0;
		for(int i=0;i<5;i++){
			if(board[x+i][y+i]=='b') countBlack++;
			if(board[x+i][y+i]=='w') countWhite++;
		}
		if(countBlack==0) return -VALUES[countWhite];
		if(countWhite==0) return VALUES[countBlack];
		return 0;
	}
	
	// 坐标仍然是左上角，从右上角开始，向左下方
	private int evaluateDiagonallyTopRight(int x,int y){
		int countBlack = 0,countWhite = 0;
		for(int i=0;i<5;i++){
			if(board[x+i][y+4-i]=='b') countBlack++;
			if(board[x+i][y+4-i]=='w') countWhite++;
		}
		if(countBlack==0) return -VALUES[countWhite];
		if(countWhite==0) return VALUES[countBlack];
		return 0;
	}
	
	
	/*
	function alphabeta(node, depth, α, β, maximizingPlayer)
		if depth = 0 or node is a terminal node
			return the heuristic value of node
		if maximizingPlayer
			α := -INF; //负无穷大
			for each child of node
				α := max(α, alphabeta(child, depth - 1, α, β,
				not(maximizingPlayer)))
			if β ≤ α //β是node的兄弟节点到目前为止的最小估价值
				break // Beta cut-off,剪掉剩下的极小节点
				return α
		else
			β := INF; //无穷大
			for each child of node
				β := min(β, alphabeta(child, depth - 1, α, β,
				not(maximizingPlayer)))
			if β ≤ α //α是node的兄弟节点到目前为止的最大估价值
				break // Alpha cut-off，剪掉剩下的极大节点
				return β
	初始调用: alphabeta(origin, depth, -infinity, +infinity, TRUE)
	 */
	/*
	 * player true 为 x
	 */
	public int minimaxWithAlphaBetaPruning(int depth,int alpha,int beta,boolean player){
		//System.out.println(check());
		int currentValue = evaluate();
		if(depth==0 || currentValue>=INF || currentValue<=-INF) return currentValue;
		if(player){
			// black MAX
			alpha = -INF;
			// TODO 增加走法生成器，不必遍历所有
			for(int i=0;i<WIDTH;i++){
				for(int j=0;j<WIDTH;j++){
					if(isEmpty(i,j)){
						placeStone(true,i,j);
						int value = minimaxWithAlphaBetaPruning(depth-1,alpha,beta,!player);
						removeStone(i, j);
						if(value>alpha) {
							alpha = value;
							// 保存最佳招法
//							bestValue = value;
//							bestX = i;
//							bestY = j;
						}
						if(beta<=alpha) return alpha;
					}
				}
			}
		}
		else{
			// white min
			beta = INF;
			for(int i=0;i<WIDTH;i++){
				for(int j=0;j<WIDTH;j++){
					if(isEmpty(i,j)){
						placeStone(false,i,j);
						int value = minimaxWithAlphaBetaPruning(depth-1,alpha,beta,!player);
						removeStone(i, j);
						if(value<beta) {
							beta = value;
							// 保存最佳招法
//							bestValue = value;
//							bestX = i;
//							bestY = j;
						}
						if(beta<=alpha) return beta;
					}
				}
			}
		}
		return player?alpha:beta;
	}
	
	/*
	 * force place a stone
	 */
	public boolean placeStone(boolean black,int x,int y){
		if(board[x][y]=='.'){
			if(black) 
				board[x][y]='b';
			else
				board[x][y]='w';
			return true;
		}
		return false;
	}
	
	/*
	 * place a stone
	 */
	public boolean place(int x,int y){
		if(board[x][y]=='.'){
			if(isBlack) 
				board[x][y]='b';
			else
				board[x][y]='w';
			isBlack = !isBlack;
			return true;
		}
		return false;
	}
	
	/*
	 * remove a stone
	 */
	public void removeStone(int x,int y){
		board[x][y]='.';
	}
	
	public boolean isEmpty(int x,int y){
		return board[x][y]=='.';
	}
	
	public void newGame(){
		isBlack = true;
		bestValue = 0;
		for(int i=0;i<WIDTH;i++)
			for(int j=0;j<WIDTH;j++)
				board[i][j]='.';
	}
	
	public void show(){
		//System.out.println(" abcdefgh");
		//System.out.println(" 01234567");
		//StringBuilder line = new StringBuilder();
		for(int i=0;i<WIDTH;i++){
			//line.setLength(0);
			//line.append(i);
//			for(int j=0;j<WIDTH;j++){
//				line.append(board[i][j]);
//				
//			}
			System.out.println(board[i]);
		}
		System.out.println();
	}
	
	public String getCurrentPlayer(){
		return isBlack?"BLACK":"WHITE";
	}
	
	public void newConsoleGame(boolean blackAI,boolean whiteAI){
		newGame();
		show();
		scanner = new Scanner(System.in);
		while(!isGameOver()){
			if((isBlack&&blackAI)||(!isBlack&&whiteAI)){
				aiMove();
				System.out.println("AI placed a stone at "+bestX+" "+bestY);
			}
			else{
				playerMove();
			}
			show();
		}
		scanner.close();
		System.out.println("over wins by ");
	}

	private Scanner scanner;

	private void playerMove() {
		int x,y;
		System.out.print(getCurrentPlayer()+" input where you want to place your piece:");
		x = scanner.nextInt();
		y = scanner.nextInt();
		place(x, y);
		// TODO 判断合法性	
	}

	private void aiMove() {
		//bestValue = minimaxWithAlphaBetaPruning(DEPTH,-INF,INF,isBlack);
		//System.out.println(bestX+","+bestY+" v: "+bestValue);
		search();
		place(bestX, bestY);
	}

	public static void main(String[] args) {
		AlphaBeta ab = new AlphaBeta();
		ab.newConsoleGame(false, true);
		//ab.search();
//		System.out.println(ab.minimaxWithAlphaBetaPruning(3,-INF,INF,true));
//		System.out.println(ab.bestX+","+ab.bestY);
//		ab.place(3,4);
////		ab.place(false, 0, 1);
//		for(int i=0;i<WIDTH;i++){
//			for(int j=0;j<WIDTH;j++){
//				if(ab.isEmpty(i, j)){
//					ab.placeStone(false, i, j);
//					System.out.print(ab.evaluate()+" ");
//					ab.removeStone(i, j);
//				}
//				else
//					System.out.print(ab.board[i][j]+" ");
//			}
//			System.out.println();
//		}
	}
}
