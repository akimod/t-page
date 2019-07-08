import java.io.*;
public class Osero64 {
	static Player64 p1,p2,p_now;//プレイヤー
	static int[][] ban;//盤面の宣言
	static int[][] omomi;//盤面の重み
	static int[][] omomi_last;
	static int te_kazu = 0;
	
	public static void main(String[] args) throws IOException {
		//盤面の設定
		ban = new int[8][8];
		for(int i = 0; i < 8;i++){
			for(int j = 8; j < 8; j++){
				ban[i][j] = 0;
			}
		}
		//盤面の初期配置
		ban[3][4] = 2;
		ban[4][3] = 2;
		ban[3][3] = 1;
		ban[4][4] = 1;
		te_kazu = 0;
		
		omomi = new int[][]{
		{ 30,-12, 0,-2,-1, 0,-12, 30},
		{-12,-15,-5,-6,-6,-5,-15,-12},
		{  0, -5, 1,-3,-3, 1, -5,  0},
		{ -2, -6,-3,-1,-1,-3, -6, -1},
		{ -1, -6,-3,-1,-1,-3, -6, -2},
		{  0, -5, 1,-3,-3, 1, -5,  0},
		{-12,-15,-5,-6,-6,-5,-15,-12},
		{ 30,-12, 0,-1,-2, 0,-12, 30}
		};
		
		omomi_last = new int[][]{
		{ 30,-12, 0,-2,-1, 0,-12, 30},
		{-12,-15,-5,-6,-6,-5,-15,-12},
		{  0, -5, 1,-3,-3, 1, -5,  0},
		{ -2, -6,-3,-1,-1,-3, -6, -1},
		{ -1, -6,-3,-1,-1,-3, -6, -2},
		{  0, -5, 1,-3,-3, 1, -5,  0},
		{-12,-15,-5,-6,-6,-5,-15,-12},
		{ 30,-12, 0,-1,-2, 0,-12, 30}

		};
		
		
		
		p1 = new Player64(1);//プレイヤー1 黒
		p2 = new Player64(2);//プレイヤー2 白
		p_now = p1;//進行用オブジェクト
		String[] kazu = {"１","２","３","４","５","６","７","８"};//盤面の装飾用
		int[] status = new int[4];//盤面の状況を数字にして整理する 0:何もなし 1:黒 2:白 3:置ける
		String[] color = {"","黒","白","現在置ける場所"};//プレイする時の補助
		int tate,yoko = 0;//コマを置く縦位置、横位置の初期設定
		int pass_count = 0; //2回おけなかったらゲーム終了=パスの数を数える
		
		
		
		while(true){//ゲームのメインループ
			for(int i = 0 ; i < status.length ; i++){//整理配列の初期化
				status[i] = 0;
			}
			for(int i=0 ; i < ban.length ; i++){ //置ける位置の初期化 プレイヤーが置ける場所を視覚化したため
				for(int j = 0 ; j < ban[i].length ; j++){
					if(ban[i][j] == 3){
						ban[i][j] = 0;
					}
				}
			}
			//置ける場所チェック
			for(int i = 0 ; i < ban.length ; i++){
				for(int j = 0 ; j < ban[i].length ; j++){
					if(ban[i][j] == 0){//コマが置かれていない場所をチェック
						if(check_change(i,j,p_now.color,"check")){//チェック関数で確認し、置ける場所は表示を変える
							ban[i][j] = 3;
						}
					}
				}
			}
			//盤面表示
			System.out.println(" 黒 = 〇 白 = ●");
			System.out.print("  ");
			for(int i = 0 ; i < ban.length ; i++){
				System.out.print(kazu[i]);
			}
			System.out.println();
			for(int i = 0 ; i < ban.length ; i++){
				System.out.print(kazu[i]);
				for(int j = 0 ; j < ban[i].length ; j++){
					switch(ban[i][j]){
					case 0://なにも無い
						System.out.print("ー");
						break;
					case 1://黒の時
						System.out.print("〇");
						break;
					case 2://白の時
						System.out.print("●");
						break;
					case 3://置ける時
						System.out.print("×");
						break;
					}
				}
				System.out.println();
			}
			//盤面にそれぞれの状態がいくつあるかのチェック 0:何もなし 1:黒 2:白 3:置ける
			for(int i = 0 ; i < ban.length; i++){
				for(int j = 0 ; j < ban[i].length ; j++){
					status[ban[i][j]]++;
				}	
			}
			if(status[3] == 0){//置ける場所があるかチェック
				pass_count++;//置けないカウント
				System.out.println(color[p_now.color]+"は置ける場所がありません。");
				changePlayer();//プレイヤー交代
			}else{
				pass_count = 0;
			}
			int remains = status[0] + status[3];//残りのマス＋置ける場所の数
			if(remains == 0 || pass_count == 2){
				break;//ゲームループから抜ける
			}
			//Player or CPの手の入力
			int[] cursor = p_now.getCursor();
			tate = cursor[0];
			yoko = cursor[1];
			if(ban[tate][yoko] != 3){
				System.out.println("指定された位置にはコマを置けません。");
				continue;
			}else{//コマを置き、ひっくり返す
				ban[tate][yoko] = p_now.color;
				check_change(tate,yoko,p_now.color,"change");
			}
			te_kazu++;
			changePlayer();//プレイヤー交代
		}
		
		
		
		//終了時の処理
		System.out.println("ゲーム終了");
		for(int i = 1 ; i < 3 ; i++){//コマの状態を表示
			System.out.print(color[i]+":"+status[i]+" ");
		}
		if(status[1] > status[2]){
			System.out.println("黒の勝ち");
		}else if(status[1] < status[2]){
			System.out.println("白の勝ち");
		}else if(status[1] == status[2]){
			System.out.println("引き分け");
		}
		System.out.println(te_kazu + "手で終了");
	}
	//メインループ終了
	
	
	
	//次置ける手のチェック＋ひっくり返す動作 チェック＆チェンジ関数
	
	static boolean check_change(int i,int j,int color,String mode){
		for(int row = -1 ; row < 2 ; row++){
			for(int cow = -1 ; cow < 2 ; cow++){
				int row_now = i + row;//タテのチェック位置
				int cow_now = j + cow;//ヨコのチェック位置
				if(row_now < 0 || cow_now < 0 || row_now > 8 || cow_now > 8){//配列からはみだしてないかチェック
					continue;
				}
				if(row_now == i && cow_now == j){//自分自身の位置はチェックしない
					continue;
				}
				boolean loop_flg = true;//ループ継続フラグ
				int distance = 0;//相手のコマが連続する回数
				do{
					if(row_now >= 0 && cow_now >= 0 && row_now <= 7 && cow_now <= 7){
						if(ban[row_now][cow_now] != 0 && ban[row_now][cow_now] != color && ban[row_now][cow_now] != 3){
							row_now += row;
							cow_now += cow;
							distance++;
						}else{
							//チェックとひっくり返しは動作がほぼ同じため途中までは一緒にしてここで分岐させる
							if(mode.equals("check")){//置ける場所のチェック
								if(ban[row_now][cow_now] == color){//自分のコマを見つけた時
									if(distance != 0){
										return true;
									}
								}
								loop_flg = false;
							}
							if(mode.equals("change")){//ひっくり返す動作
								if(distance != 0 && ban[row_now][cow_now] == p_now.color){//自分のコマを見つけた時
									while(row_now != i || cow_now != j){//ひっくり返しながら戻ってくる
										row_now -= row;
										cow_now -= cow;
										ban[row_now][cow_now] = color;
									}
									loop_flg = false;
								}else{
									loop_flg = false;
								}
							}
						}
					}else{
						loop_flg = false;
					}
				}while(loop_flg==true);
			}
		}
		return false;
	}
	//プレイヤー交代関数
	static void changePlayer(){
		if(p_now.color == 1){
			p_now = p2;
		}else if(p_now.color == 2){
			p_now = p1;
		}
	}
}
