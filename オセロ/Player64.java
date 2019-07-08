import java.io.*;

public class Player64{
	int color; // 1 = 黒 2 = 白
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Player64(int color){
		this.color = color;
	}
	int[] getCursor() throws IOException{
		int[] cursor = {0,0};//cursor[0] = タテ cursor[1] = ヨコ
		//プレイヤー動作の作成
		if(color == 2){
			System.out.println("Playerの番です");
			String str;
			do{
					System.out.print("タテ位置の入力:1～8:");
					str = br.readLine();
					if(str.isEmpty()){//何も入力されなかった時にエラーを起こさないように
						cursor[0]=1;
					}else{
						cursor[0] = Integer.parseInt(str);
					}
					cursor[0]--;
			}while(cursor[0] < 0 || cursor[0] > 7);
			do{
					System.out.print("ヨコ位置の入力:1～8:");
					str = br.readLine();
					if(str.isEmpty()){//何も入力されなかった時にエラーを起こさないように
						cursor[1]=1;
					}else{
						cursor[1] = Integer.parseInt(str);
					}
					cursor[1]--;
			}while(cursor[1] < 0 || cursor[1] > 7);
		}
		//CP動作の作成  重みに従っておいていく。
		else if(color == 1){
			System.out.println("CPの番です。CPが指します");
			int banmen[][] = Osero64.ban;
			int omomimen[][] = Osero64.omomi;
			int omomimen_last[][] = Osero64.omomi_last;
			int tekazu = Osero64.te_kazu;
			int omomi_now = -100;
			if(tekazu <= 52){
				for(int i = 0; i < 8;i++){
					for(int j = 0; j < 8; j++){
						if((banmen[i][j] == 0) || (banmen[i][j] == 1) || (banmen[i][j] == 2)){
							continue;
						}else if(banmen[i][j] == 3){
							if(omomi_now <= omomimen[i][j]){
								omomi_now = omomimen[i][j];
								cursor[0] = i;
								cursor[1] = j;
							}
						}
					}
				}
			}else if(tekazu > 52){
				for(int i = 0; i < 8;i++){
					for(int j = 0; j < 8; j++){
						if((banmen[i][j] == 0) || (banmen[i][j] == 1) || (banmen[i][j] == 2)){
							continue;
						}else if(banmen[i][j] == 3){
							if(omomi_now <= omomimen_last[i][j]){
								omomi_now = omomimen_last[i][j];
								cursor[0] = i;
								cursor[1] = j;
							}
						}
					}
				}
			}
		}
	return cursor;
	}
}