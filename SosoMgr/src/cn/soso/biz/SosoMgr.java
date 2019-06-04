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
 * 业务类
 * 
 * @author 北大青鸟
 * 
 */
public class SosoMgr {
	Scanner input = new Scanner(System.in);
	CardUtil utils = new CardUtil();
	
	public static void main(String[] args) {
		SosoMgr soso = new SosoMgr();
		soso.mainMenu();
		System.out.println("谢谢使用！");
	}

	/**
	 * 主流程
	 */
	public void mainMenu() {
		int menuChoose = 0;
		String mobileNumber= "";
		String password = "";
		utils.init();
		utils.initScenes();
		//Common.typesInit();
		do {
			System.out.println("\n*************欢迎使用嗖嗖移动业务大厅***************");
			System.out.println("1.用户登录   2.用户注册   3.使用嗖嗖   4.话费充值  5.资费说明  6.退出系统");
			System.out.print("请选择：");
			while(!(input.hasNextInt())){
				System.out.print("输入有误，请重新输入：");
				input.next();
			}
			menuChoose = input.nextInt();
			while(menuChoose<1 || menuChoose>6){
				System.out.print("输入有误，请重新输入：");
				while(!(input.hasNextInt())){
					System.out.print("输入有误，请重新输入：");
					input.next();
				}
				menuChoose = input.nextInt();
			}
			// 分支语句：根据功能编号执行相应功能
			switch (menuChoose) {
			case 1:
				//用户登录
				System.out.print("请输入手机卡号：");
				mobileNumber = input.next();
				System.out.print("请输入密码：");
				password = input.next();
				if (utils.isExistCard(mobileNumber, password)) {
					cardMenu(mobileNumber);
				}else{
					System.out.println("对不起，账号或密码输入错误，无法登录！");
				}
				continue;
			case 2:
				//用户注册
				registCard();
				continue;
			case 3:
				
				//使用嗖嗖
				System.out.print("请输入手机卡号：");
				mobileNumber = input.next();				
				//先验证账号是否存在
				if (utils.isExistCard(mobileNumber)) {
					try {
						utils.userSoso(mobileNumber);
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}else{
					System.out.println("对不起，该卡号未注册，不能使用！");
				}
				
				/*System.out.println("****使用之后****");
				utils.showRemainDetail(mobileNumber);
				utils.showAmountDetail(mobileNumber);*/
				continue;
			case 4:
				//话费充值
				System.out.print("请输入充值卡号：");
				mobileNumber = input.next();
				if (utils.isExistCard(mobileNumber)) {
				System.out.print("请输入充值金额：");
				
				double money = 0.0;
				while(!(input.hasNextDouble())){
					System.out.print("输入有误，请重新输入：");
					input.next();
				}
				money = input.nextDouble();
				while(money<0 || money>1000){
					System.out.print("充值金额有误，请重新输入：");
					while(!(input.hasNextDouble())){
						System.out.print("输入有误，请重新输入：");
						input.next();
					}
					money= input.nextDouble();
				}
				utils.chargeMoney(mobileNumber, money);
				}else{
					System.out.println("对不起，要充值的卡号未注册，无法充值！");
				}
				continue;				
			case 5:
				System.out.println("\n******资费说明******");
				utils.showDescription();
				continue;	
			case 6:
				//退出系统
				System.out.println("********************已退出系统********************");
				ObjectFile obj=new ObjectFile();
				Map<String, MobileCard> cards = CardUtil.cards;
				MobileCard mobileCard = cards.get(mobileNumber);
				CardUtil util=new CardUtil();
				if(util.cards.get(mobileNumber)!=null){
					//保存消费金额
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
				//选择其他数字退出系统
				break;
			}
			break;
		} while (true);
	}

	/**
	 * 手机卡功能菜单
	 * 
	 * @param number
	 * @return
	 */
	public int cardMenu(String mobileNumber) {
		   int menuChoose = 0;
		do {
			System.out.println("\n*****嗖嗖移动用户菜单*****");
			System.out.println("1.本月账单查询");
			System.out.println("2.套餐余量查询");
			System.out.println("3.打印消费详单");
			System.out.println("4.显示消费记录");
			System.out.println("5.套餐变更");
			System.out.println("6.办理退网");
			System.out.println("7.修改密码");
			System.out.print("请选择(输入1~7选择功能，其他键返回上一级)：");
			while(!(input.hasNextInt())){
				System.out.print("输入有误,请重新输入：");
				input.next();
			}
			menuChoose = input.nextInt();
			switch (menuChoose) {
			case 1:
				System.out.println("\n*****本月账单查询******");
				utils.showAmountDetail(mobileNumber);
				continue;
			case 2:
				System.out.println("\n*****套餐余量查询******");
				utils.showRemainDetail(mobileNumber);
				continue;
			case 3:
				System.out.println("\n*****打印消费记录******");
				//utils.showAmountDetail(mobileNumber);
				utils.printConsumInfo(mobileNumber);
				continue;
			case 4:
				System.out.println("\n*****查询消费记录******");
				utils.showConsumMeg(mobileNumber);
				continue;
			case 5:
				System.out.println("\n*****套餐变更******");
				System.out.print("1.话唠套餐  2.网虫套餐  3.超人套餐  请选择（序号）：");				
				utils.changingPack(mobileNumber, input.next());
				continue;
			case 6:
				System.out.println("\n*****办理退网******");
				utils.delCard(mobileNumber);
				System.out.println("谢谢使用！");
				System.exit(1);	 //办理退网后退出系统		
			case 7:
				System.out.println("\n*****修改密码******");
				System.out.print("请输入新的密码：");
				String pwd = input.next();
				System.out.print("请再次输入密码：");
				String pwds = input.next();
				if(pwds.equals(pwd)){
					utils.updatePwd(mobileNumber, pwd);
					System.out.println("密码修改成功！");
				}else if(!pwds.equals(pwd)){
					System.out.println("两次输入的密码不一致");
					continue;
				}
				
			}
			break;
		} while (true);
        return menuChoose;
	}
	
	/**
	 * 注册新卡流程
	 */
	public void registCard(){
		String[] newNumbers = utils.getNewNumbers(9);
		//显示可供选择的手机号列表
		System.out.println("*****可选择的卡号*****");
		
		for(int i=0;i<9;i++){
			System.out.print((i+1)+"."+newNumbers[i]+"\t\t");
			//每次打印三个号码换行
			if((i+1)%3==0){
				System.out.println();
			}
		}
		//选择手机号
		System.out.print("请选择卡号（输入1~9的序号）：");	
		int xuhao = 0;
		while(!(input.hasNextInt())){
			System.out.print("输入有误，请重新输入：");
			input.next();
		}
		xuhao = input.nextInt();
		while(xuhao<1 || xuhao>9){
			System.out.print("输入有误，请重新输入：");
			while(!(input.hasNextInt())){
				System.out.print("输入有误，请重新输入：");
				input.next();
			}
			xuhao = input.nextInt();
		}
		String number = newNumbers[xuhao-1];
		
		//选择套餐类型
		System.out.print("1.话唠套餐  2.网虫套餐  3.超人套餐，  ");
		System.out.print("请选择套餐(输入序号)：");
		//utils.getPackList();
		//获取套餐对象 
		int packId = 0;
		//ServicePackage pack = null;
		while(!(input.hasNextInt())){
			System.out.print("输入有误，请重新输入：");
			input.next();
		}
		packId = input.nextInt();
		while(packId<1 || packId>3){
			System.out.print("输入有误，请重新输入：");
			while(!(input.hasNextInt())){
				System.out.print("输入有误，请重新输入");
				input.next();
			}
			packId = input.nextInt();
		}
		ServicePackage pack = utils.createPack(packId);
		//ServicePackage 
		//输入用户名
		System.out.print("请输入姓名：");
		String name = input.next();
		
		//输入密码
		System.out.print("请输入密码：");
		String password = input.next();
		
		//输入预存话费金额
		double money =0;
		System.out.print("请输入预存话费金额：");
		while(!(input.hasNextDouble())){
			System.out.print("输入有误，请重新输入：");
			input.next();
		}
		money = input.nextDouble();
		while(money<0 || money>1000 ){
			System.out.print("充值金额有误，请重新输入：");
			while(!(input.hasNextDouble())){
				System.out.print("输入有误，请重新输入：");
				
				while(input.nextDouble()<pack.getPrice()){
					System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
					money = input.nextDouble();
				}
			}
			money = input.nextDouble();
		}
//		while(money<pack.getPrice()){
//			System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
//			
//		}
				
		//创建新卡对象并添加
		MobileCard newCard = new MobileCard(name,password,number,pack,pack.getPrice(),money-pack.getPrice());
		utils.addCard(newCard);		
	}
}
