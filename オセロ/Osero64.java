import java.io.*;
public class Osero64 {
	static Player64 p1,p2,p_now;//�v���C���[
	static int[][] ban;//�Ֆʂ̐錾
	static int[][] omomi;//�Ֆʂ̏d��
	static int[][] omomi_last;
	static int te_kazu = 0;
	
	public static void main(String[] args) throws IOException {
		//�Ֆʂ̐ݒ�
		ban = new int[8][8];
		for(int i = 0; i < 8;i++){
			for(int j = 8; j < 8; j++){
				ban[i][j] = 0;
			}
		}
		//�Ֆʂ̏����z�u
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
		
		
		
		p1 = new Player64(1);//�v���C���[1 ��
		p2 = new Player64(2);//�v���C���[2 ��
		p_now = p1;//�i�s�p�I�u�W�F�N�g
		String[] kazu = {"�P","�Q","�R","�S","�T","�U","�V","�W"};//�Ֆʂ̑����p
		int[] status = new int[4];//�Ֆʂ̏󋵂𐔎��ɂ��Đ������� 0:�����Ȃ� 1:�� 2:�� 3:�u����
		String[] color = {"","��","��","���ݒu����ꏊ"};//�v���C���鎞�̕⏕
		int tate,yoko = 0;//�R�}��u���c�ʒu�A���ʒu�̏����ݒ�
		int pass_count = 0; //2�񂨂��Ȃ�������Q�[���I��=�p�X�̐��𐔂���
		
		
		
		while(true){//�Q�[���̃��C�����[�v
			for(int i = 0 ; i < status.length ; i++){//�����z��̏�����
				status[i] = 0;
			}
			for(int i=0 ; i < ban.length ; i++){ //�u����ʒu�̏����� �v���C���[���u����ꏊ�����o����������
				for(int j = 0 ; j < ban[i].length ; j++){
					if(ban[i][j] == 3){
						ban[i][j] = 0;
					}
				}
			}
			//�u����ꏊ�`�F�b�N
			for(int i = 0 ; i < ban.length ; i++){
				for(int j = 0 ; j < ban[i].length ; j++){
					if(ban[i][j] == 0){//�R�}���u����Ă��Ȃ��ꏊ���`�F�b�N
						if(check_change(i,j,p_now.color,"check")){//�`�F�b�N�֐��Ŋm�F���A�u����ꏊ�͕\����ς���
							ban[i][j] = 3;
						}
					}
				}
			}
			//�Ֆʕ\��
			System.out.println(" �� = �Z �� = ��");
			System.out.print("  ");
			for(int i = 0 ; i < ban.length ; i++){
				System.out.print(kazu[i]);
			}
			System.out.println();
			for(int i = 0 ; i < ban.length ; i++){
				System.out.print(kazu[i]);
				for(int j = 0 ; j < ban[i].length ; j++){
					switch(ban[i][j]){
					case 0://�Ȃɂ�����
						System.out.print("�[");
						break;
					case 1://���̎�
						System.out.print("�Z");
						break;
					case 2://���̎�
						System.out.print("��");
						break;
					case 3://�u���鎞
						System.out.print("�~");
						break;
					}
				}
				System.out.println();
			}
			//�Ֆʂɂ��ꂼ��̏�Ԃ��������邩�̃`�F�b�N 0:�����Ȃ� 1:�� 2:�� 3:�u����
			for(int i = 0 ; i < ban.length; i++){
				for(int j = 0 ; j < ban[i].length ; j++){
					status[ban[i][j]]++;
				}	
			}
			if(status[3] == 0){//�u����ꏊ�����邩�`�F�b�N
				pass_count++;//�u���Ȃ��J�E���g
				System.out.println(color[p_now.color]+"�͒u����ꏊ������܂���B");
				changePlayer();//�v���C���[���
			}else{
				pass_count = 0;
			}
			int remains = status[0] + status[3];//�c��̃}�X�{�u����ꏊ�̐�
			if(remains == 0 || pass_count == 2){
				break;//�Q�[�����[�v���甲����
			}
			//Player or CP�̎�̓���
			int[] cursor = p_now.getCursor();
			tate = cursor[0];
			yoko = cursor[1];
			if(ban[tate][yoko] != 3){
				System.out.println("�w�肳�ꂽ�ʒu�ɂ̓R�}��u���܂���B");
				continue;
			}else{//�R�}��u���A�Ђ�����Ԃ�
				ban[tate][yoko] = p_now.color;
				check_change(tate,yoko,p_now.color,"change");
			}
			te_kazu++;
			changePlayer();//�v���C���[���
		}
		
		
		
		//�I�����̏���
		System.out.println("�Q�[���I��");
		for(int i = 1 ; i < 3 ; i++){//�R�}�̏�Ԃ�\��
			System.out.print(color[i]+":"+status[i]+" ");
		}
		if(status[1] > status[2]){
			System.out.println("���̏���");
		}else if(status[1] < status[2]){
			System.out.println("���̏���");
		}else if(status[1] == status[2]){
			System.out.println("��������");
		}
		System.out.println(te_kazu + "��ŏI��");
	}
	//���C�����[�v�I��
	
	
	
	//���u�����̃`�F�b�N�{�Ђ�����Ԃ����� �`�F�b�N���`�F���W�֐�
	
	static boolean check_change(int i,int j,int color,String mode){
		for(int row = -1 ; row < 2 ; row++){
			for(int cow = -1 ; cow < 2 ; cow++){
				int row_now = i + row;//�^�e�̃`�F�b�N�ʒu
				int cow_now = j + cow;//���R�̃`�F�b�N�ʒu
				if(row_now < 0 || cow_now < 0 || row_now > 8 || cow_now > 8){//�z�񂩂�݂͂����ĂȂ����`�F�b�N
					continue;
				}
				if(row_now == i && cow_now == j){//�������g�̈ʒu�̓`�F�b�N���Ȃ�
					continue;
				}
				boolean loop_flg = true;//���[�v�p���t���O
				int distance = 0;//����̃R�}���A�������
				do{
					if(row_now >= 0 && cow_now >= 0 && row_now <= 7 && cow_now <= 7){
						if(ban[row_now][cow_now] != 0 && ban[row_now][cow_now] != color && ban[row_now][cow_now] != 3){
							row_now += row;
							cow_now += cow;
							distance++;
						}else{
							//�`�F�b�N�ƂЂ�����Ԃ��͓��삪�قړ������ߓr���܂ł͈ꏏ�ɂ��Ă����ŕ��򂳂���
							if(mode.equals("check")){//�u����ꏊ�̃`�F�b�N
								if(ban[row_now][cow_now] == color){//�����̃R�}����������
									if(distance != 0){
										return true;
									}
								}
								loop_flg = false;
							}
							if(mode.equals("change")){//�Ђ�����Ԃ�����
								if(distance != 0 && ban[row_now][cow_now] == p_now.color){//�����̃R�}����������
									while(row_now != i || cow_now != j){//�Ђ�����Ԃ��Ȃ���߂��Ă���
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
	//�v���C���[���֐�
	static void changePlayer(){
		if(p_now.color == 1){
			p_now = p2;
		}else if(p_now.color == 2){
			p_now = p1;
		}
	}
}
