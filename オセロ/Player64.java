import java.io.*;

public class Player64{
	int color; // 1 = �� 2 = ��
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Player64(int color){
		this.color = color;
	}
	int[] getCursor() throws IOException{
		int[] cursor = {0,0};//cursor[0] = �^�e cursor[1] = ���R
		//�v���C���[����̍쐬
		if(color == 2){
			System.out.println("Player�̔Ԃł�");
			String str;
			do{
					System.out.print("�^�e�ʒu�̓���:1�`8:");
					str = br.readLine();
					if(str.isEmpty()){//�������͂���Ȃ��������ɃG���[���N�����Ȃ��悤��
						cursor[0]=1;
					}else{
						cursor[0] = Integer.parseInt(str);
					}
					cursor[0]--;
			}while(cursor[0] < 0 || cursor[0] > 7);
			do{
					System.out.print("���R�ʒu�̓���:1�`8:");
					str = br.readLine();
					if(str.isEmpty()){//�������͂���Ȃ��������ɃG���[���N�����Ȃ��悤��
						cursor[1]=1;
					}else{
						cursor[1] = Integer.parseInt(str);
					}
					cursor[1]--;
			}while(cursor[1] < 0 || cursor[1] > 7);
		}
		//CP����̍쐬  �d�݂ɏ]���Ă����Ă����B
		else if(color == 1){
			System.out.println("CP�̔Ԃł��BCP���w���܂�");
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