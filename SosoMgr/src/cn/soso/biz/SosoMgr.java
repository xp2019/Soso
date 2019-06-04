package cn.soso.biz;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import cn.soso.entity.ConsumInfo;
import cn.soso.utils.ObjectFile;
import cn.soso.entity.MobileCard;
import cn.soso.entity.ServicePackage;

import cn.soso.common.Common;
import cn.soso.utils.CardUtil;

/**
 * ҵ����
 * 
 * @author ��������
 * 
 */
public class SosoMgr {
	Scanner input = new Scanner(System.in);
	CardUtil utils = new CardUtil();
	
	public static void main(String[] args) {
		SosoMgr soso = new SosoMgr();
		soso.mainMenu();
		System.out.println("ллʹ�ã�");
	}

	/**
	 * ������
	 */
	public void mainMenu() {
		int menuChoose = 0;
		String mobileNumber= "";
		String password = "";
		utils.init();
		utils.initScenes();
		//Common.typesInit();
		do {
			System.out.println("\n*************��ӭʹ�����ƶ�ҵ�����***************");
			System.out.println("1.�û���¼   2.�û�ע��   3.ʹ����   4.���ѳ�ֵ  5.�ʷ�˵��  6.�˳�ϵͳ");
			System.out.print("��ѡ��");
			while(!(input.hasNextInt())){
				System.out.print("�����������������룺");
				input.next();
			}
			menuChoose = input.nextInt();
			while(menuChoose<1 || menuChoose>6){
				System.out.print("�����������������룺");
				while(!(input.hasNextInt())){
					System.out.print("�����������������룺");
					input.next();
				}
				menuChoose = input.nextInt();
			}
			// ��֧��䣺���ݹ��ܱ��ִ����Ӧ����
			switch (menuChoose) {
			case 1:
				//�û���¼
				System.out.print("�������ֻ����ţ�");
				mobileNumber = input.next();
				System.out.print("���������룺");
				password = input.next();
				if (utils.isExistCard(mobileNumber, password)) {
					cardMenu(mobileNumber);
				}else{
					System.out.println("�Բ����˺Ż�������������޷���¼��");
				}
				continue;
			case 2:
				//�û�ע��
				registCard();
				continue;
			case 3:
				
				//ʹ����
				System.out.print("�������ֻ����ţ�");
				mobileNumber = input.next();				
				//����֤�˺��Ƿ����
				if (utils.isExistCard(mobileNumber)) {
					try {
						utils.userSoso(mobileNumber);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}else{
					System.out.println("�Բ��𣬸ÿ���δע�ᣬ����ʹ�ã�");
				}
				
				/*System.out.println("****ʹ��֮��****");
				utils.showRemainDetail(mobileNumber);
				utils.showAmountDetail(mobileNumber);*/
				continue;
			case 4:
				//���ѳ�ֵ
				System.out.print("�������ֵ���ţ�");
				mobileNumber = input.next();
				if (utils.isExistCard(mobileNumber)) {
				System.out.print("�������ֵ��");
				
				double money = 0.0;
				while(!(input.hasNextDouble())){
					System.out.print("�����������������룺");
					input.next();
				}
				money = input.nextDouble();
				while(money<0 || money>1000){
					System.out.print("��ֵ����������������룺");
					while(!(input.hasNextDouble())){
						System.out.print("�����������������룺");
						input.next();
					}
					money= input.nextDouble();
				}
				utils.chargeMoney(mobileNumber, money);
				}else{
					System.out.println("�Բ���Ҫ��ֵ�Ŀ���δע�ᣬ�޷���ֵ��");
				}
				continue;				
			case 5:
				System.out.println("\n******�ʷ�˵��******");
				utils.showDescription();
				continue;	
			case 6:
				//�˳�ϵͳ
				System.out.println("********************���˳�ϵͳ********************");
				ObjectFile obj=new ObjectFile();
				Map<String, MobileCard> cards = CardUtil.cards;
				MobileCard mobileCard = cards.get(mobileNumber);
				CardUtil util=new CardUtil();
				if(util.cards.get(mobileNumber)!=null){
					//�������ѽ��
					mobileCard.setConsumAmount((cards.get(mobileNumber).getConsumAmount()));
					mobileCard.getSerPackage().price=cards.get(mobileNumber).getSerPackage().price;
//					for (int i = 0; i < CardUtil.consumInfos.size(); i++) {
//						List<ConsumInfo> info = CardUtil.consumInfos.get(mobileNumber);
//						for (ConsumInfo consumInfo : info) {
//							CardUtil.cards.get(mobileNumber).setRealTalkTime(consumInfo.getConsumData());
//							CardUtil.cards.get(mobileNumber).setRealFlow(consumInfo.getConsumData());
//						}
//					}
				}
				obj.objectOutputFile(cards);
				System.exit(0);
				break;
			default:
				//ѡ�����������˳�ϵͳ
				break;
			}
			break;
		} while (true);
	}

	/**
	 * �ֻ������ܲ˵�
	 * 
	 * @param number
	 * @return
	 */
	public int cardMenu(String mobileNumber) {
		   int menuChoose = 0;
		do {
			System.out.println("\n*****���ƶ��û��˵�*****");
			System.out.println("1.�����˵���ѯ");
			System.out.println("2.�ײ�������ѯ");
			System.out.println("3.��ӡ�����굥");
			System.out.println("4.��ʾ���Ѽ�¼");
			System.out.println("5.�ײͱ��");
			System.out.println("6.��������");
			System.out.println("7.�޸�����");
			System.out.print("��ѡ��(����1~7ѡ���ܣ�������������һ��)��");
			while(!(input.hasNextInt())){
				System.out.print("��������,���������룺");
				input.next();
			}
			menuChoose = input.nextInt();
			switch (menuChoose) {
			case 1:
				System.out.println("\n*****�����˵���ѯ******");
				utils.showAmountDetail(mobileNumber);
				continue;
			case 2:
				System.out.println("\n*****�ײ�������ѯ******");
				utils.showRemainDetail(mobileNumber);
				continue;
			case 3:
				System.out.println("\n*****��ӡ���Ѽ�¼******");
				//utils.showAmountDetail(mobileNumber);
				utils.printConsumInfo(mobileNumber);
				continue;
			case 4:
				System.out.println("\n*****��ѯ���Ѽ�¼******");
				utils.showConsumMeg(mobileNumber);
				continue;
			case 5:
				System.out.println("\n*****�ײͱ��******");
				System.out.print("1.�����ײ�  2.�����ײ�  3.�����ײ�  ��ѡ����ţ���");				
				utils.changingPack(mobileNumber, input.next());
				continue;
			case 6:
				System.out.println("\n*****��������******");
				utils.delCard(mobileNumber);
				System.out.println("ллʹ�ã�");
				System.exit(1);	 //�����������˳�ϵͳ		
			case 7:
				System.out.println("\n*****�޸�����******");
				System.out.print("�������µ����룺");
				String pwd = input.next();
				System.out.print("���ٴ��������룺");
				String pwds = input.next();
				if(pwds.equals(pwd)){
					utils.updatePwd(mobileNumber, pwd);
					System.out.println("�����޸ĳɹ���");
				}else if(!pwds.equals(pwd)){
					System.out.println("������������벻һ��");
					continue;
				}
				
			}
			break;
		} while (true);
        return menuChoose;
	}
	
	/**
	 * ע���¿�����
	 */
	public void registCard(){
		String[] newNumbers = utils.getNewNumbers(9);
		//��ʾ�ɹ�ѡ����ֻ����б�
		System.out.println("*****��ѡ��Ŀ���*****");
		
		for(int i=0;i<9;i++){
			System.out.print((i+1)+"."+newNumbers[i]+"\t\t");
			//ÿ�δ�ӡ�������뻻��
			if((i+1)%3==0){
				System.out.println();
			}
		}
		//ѡ���ֻ���
		System.out.print("��ѡ�񿨺ţ�����1~9����ţ���");	
		int xuhao = 0;
		while(!(input.hasNextInt())){
			System.out.print("�����������������룺");
			input.next();
		}
		xuhao = input.nextInt();
		while(xuhao<1 || xuhao>9){
			System.out.print("�����������������룺");
			while(!(input.hasNextInt())){
				System.out.print("�����������������룺");
				input.next();
			}
			xuhao = input.nextInt();
		}
		String number = newNumbers[xuhao-1];
		
		//ѡ���ײ�����
		System.out.print("1.�����ײ�  2.�����ײ�  3.�����ײͣ�  ");
		System.out.print("��ѡ���ײ�(�������)��");
		//utils.getPackList();
		//��ȡ�ײͶ��� 
		int packId = 0;
		//ServicePackage pack = null;
		while(!(input.hasNextInt())){
			System.out.print("�����������������룺");
			input.next();
		}
		packId = input.nextInt();
		while(packId<1 || packId>3){
			System.out.print("�����������������룺");
			while(!(input.hasNextInt())){
				System.out.print("������������������");
				input.next();
			}
			packId = input.nextInt();
		}
		ServicePackage pack = utils.createPack(packId);
		//ServicePackage 
		//�����û���
		System.out.print("������������");
		String name = input.next();
		
		//��������
		System.out.print("���������룺");
		String password = input.next();
		
		//����Ԥ�滰�ѽ��
		double money =0;
		System.out.print("������Ԥ�滰�ѽ�");
		while(!(input.hasNextDouble())){
			System.out.print("�����������������룺");
			input.next();
		}
		money = input.nextDouble();
		while(money<0 || money>1000 ){
			System.out.print("��ֵ����������������룺");
			while(!(input.hasNextDouble())){
				System.out.print("�����������������룺");
				
				while(input.nextDouble()<pack.getPrice()){
					System.out.print("��Ԥ��Ļ��ѽ�����֧�����¹̶��ײ��ʷѣ������³�ֵ��");
					money = input.nextDouble();
				}
			}
			money = input.nextDouble();
		}
//		while(money<pack.getPrice()){
//			System.out.print("��Ԥ��Ļ��ѽ�����֧�����¹̶��ײ��ʷѣ������³�ֵ��");
//			
//		}
				
		//�����¿��������
		MobileCard newCard = new MobileCard(name,password,number,pack,pack.getPrice(),money-pack.getPrice());
		utils.addCard(newCard);		
	}
}
