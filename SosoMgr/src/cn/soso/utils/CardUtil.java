package cn.soso.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;




import cn.soso.common.Common;
import cn.soso.entity.ConsumInfo;
import cn.soso.entity.MobileCard;
import cn.soso.entity.NetPackage;
import cn.soso.entity.Scene;
import cn.soso.entity.ServicePackage;
import cn.soso.entity.SuperPackage;
import cn.soso.entity.TalkPackage;
import cn.soso.service.CallService;
import cn.soso.service.NetService;
import cn.soso.service.SendService;

/**
 * 手机卡工具类
 * 
 * @author 北大青鸟
 * 
 */

public class CardUtil {
	static ObjectFile obj=new ObjectFile();
	
	public static Map<String, MobileCard> cards = new HashMap<String, MobileCard>(); // 所有手机卡的列表
	public static Map<String, List<ConsumInfo>> consumInfos = new HashMap<String, List<ConsumInfo>>(); // 所有手机卡消费记录的列表
	List<Scene> scenes = new ArrayList<Scene>();

	// 初始化用户
	public void init() {
//		MobileCard card1 = new MobileCard("何玲玲", "123", "13965756432",
//				new TalkPackage(), 58.0, 42.0);
//		MobileCard card2 = new MobileCard("黄露露", "123", "13956712467",
//				new NetPackage(), 68.0, 32.0);
//		MobileCard card3 = new MobileCard("朱蓉蓉", "123", "13911568956",
//				new SuperPackage(), 78.0, 22.0);
//		MobileCard card4 = new MobileCard("桃跑跑", "123", "13924221868",
//				new TalkPackage(), 78.0, 2.0);
//		card4.setConsumAmount(98.0);
//		card4.setRealTalkTime(500);
//		card4.setRealSMSCount(100);
//		cards.put("13965756432", card1);
//		cards.put("13956712467", card2);
//		cards.put("13911568956", card3);
//		cards.put("13924221868", card4);
//		
		Map<String, MobileCard> objectInputFile = obj.objectInputFile();
		for(Map.Entry<String, MobileCard>maps:objectInputFile.entrySet()){
			String callNum = maps.getKey();
			MobileCard card = maps.getValue();
			cards.put(callNum, card);
		}		
	}
	
	/**
	 * 使用场景初始化
	 */
	public void initScenes(){	
		scenes.add(new Scene("通话",90,"问候客户，谁知其如此难缠 通话90分钟"));
		scenes.add(new Scene("通话",30,"询问妈妈身体状况 本地通话30分钟"));
		scenes.add(new Scene("短信",5,"参与环境保护实施方案问卷调查 发送短信5条"));
		scenes.add(new Scene("短信",50,"通知朋友手机换号，发送短信50条"));
		scenes.add(new Scene("上网",1*1024,"和女友微信视频聊天   使用流量1G"));
		scenes.add(new Scene("上网",2*1024,"晚上手机在线看韩剧，不留神睡着啦！ 使用流量 2G"));		
	}

	/**
	 * 是否存在此卡用户

	 * 
	 * @param number
	 * @param passWord
	 * @return
	 */
	public boolean isExistCard(String number, String passWord) {
		Set<String> numbers = cards.keySet();
		Iterator<String> it = numbers.iterator();
		while (it.hasNext()) {
			String searchNum = it.next();
			if (searchNum.equals(number)
					&& (cards.get(searchNum)).getPassWord().equals(passWord)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 查找指定卡号是否已注册
	 * 
	 * @param searchNumber
	 * @return 未注册：false 已注册：true
	 */
	public boolean isExistCard(String searchNumber) {
		Set<String> numbers = cards.keySet();
		for (String number : numbers) {
			if (number.equals(searchNumber)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 创建卡号（以139开头 11位）
	 * 
	 * @return 生成的随机手机卡号
	 */
	public String createNumber() {
		Random random = new Random();
		boolean isExist = false; // 记录现有用户中是否存在此卡号用户 是：true 否：false
		String number = "";
		int temp = 0;
		do {
			isExist = false; // 标志位重置为false,用于控制外重循环，当生成了
			// 生成的随机数是8位 不能小于10000000，否则重新生成
			do {
				temp = random.nextInt(100000000);
			} while (temp < 10000000);
			// 生成之前，前面加“139”
			number = "139" + temp;
			// 和现有用户的卡号比较，不能是重复
			Set<String> cardNumbers = cards.keySet();
			for (String cardNumber : cardNumbers) {
				if (number.equals(cardNumber)) {
					isExist = true;
					break;
				}
			}
		} while (isExist);
		return number;
	}

	/**
	 * 生成指定个数的新卡号列表
	 * 
	 * @param count
	 *            指定个数
	 * @return 卡号列表
	 */
	public String[] getNewNumbers(int count) {

		String[] numbers = new String[count];
		for (int i = 0; i < count; i++) {
			numbers[i] = createNumber();
		}
		return numbers;
	}

	/**
	 * 添加新卡
	 * 
	 * @param card
	 *            新卡
	 */
	public void addCard(MobileCard card) {
		cards.put(card.getCardNumber(), card);
		System.out.print("注册成功！");
		card.showMeg();
	}

	/**
	 * 指定卡号办理退网
	 * 删除用户.txt
	 * @param card
	 */
	public void delCard(String delNumber) {
		if (isExistCard(delNumber)) {
			cards.remove(delNumber);
			File file = new File("Card/"+ delNumber + "消费记录.txt");
			
			if(file.exists()){
				file.delete();
				System.out.println("消费记录已删除");
			}
			/**删除用户消费记录*/
			System.out.println("卡号" + delNumber + "办理退网成功！");
		} else {
			System.out.println("对不起，该卡号未注册，不能办退退网！");
		}
	}

	/**
	 * 查询指定卡套餐余量
	 * 
	 * @param number
	 */
	public void showRemainDetail(String searchNumber) {
		MobileCard card; // 要查询的卡
		int remainTalkTime;
		int remainSmsCount;
		int remainFlow;
		StringBuffer meg = new StringBuffer();
			card = cards.get(searchNumber);
			meg.append("您的卡号是" + searchNumber + "，套餐内剩余：\n");
			ServicePackage pack = card.getSerPackage();
			if (pack instanceof TalkPackage) {
				//向下转型为话唠套餐对象
				TalkPackage cardPack = (TalkPackage) pack;
				// 话唠套餐，查询套餐内剩余的通话时长和短信条数
				remainTalkTime = cardPack.getTalkTime() > card
						.getRealTalkTime() ? cardPack.getTalkTime()
						- card.getRealTalkTime() : 0;
				meg.append("通话时长：" + remainTalkTime + "分钟\n");
				remainSmsCount = cardPack.getSmsCount() > card
						.getRealSMSCount() ? cardPack.getSmsCount()
						- card.getRealSMSCount() : 0;
				meg.append("短信条数：" + remainSmsCount + "条");
			} else if (pack instanceof NetPackage) {
				//向下转型为网虫套餐对象
				NetPackage cardPack = (NetPackage) pack;
				// 网虫套餐：查询套餐内剩余的上网流量
				remainFlow = cardPack.getFlow() > card.getRealFlow() ? cardPack
						.getFlow() - card.getRealFlow() : 0;
				meg.append("上网流量：" + Common.dataFormat(remainFlow * 1.0 / 1024)
						+ "GB");
			} else if (pack instanceof SuperPackage) {
				//向下转型为超人套餐对象
				SuperPackage cardPack = (SuperPackage) pack;
				// 超人套餐：查询套餐内剩余的通话时长、短信条数、上网流量
				remainTalkTime = cardPack.getTalkTime() > card
						.getRealTalkTime() ? cardPack.getTalkTime()
						- card.getRealTalkTime() : 0;
				meg.append("通话时长：" + remainTalkTime + "分钟\n");
				remainSmsCount = cardPack.getSmsCount() > card
						.getRealSMSCount() ? cardPack.getSmsCount()
						- card.getRealSMSCount() : 0;
				meg.append("短信条数：" + remainSmsCount + "条\n");
				remainFlow = cardPack.getFlow() > card.getRealFlow() ? cardPack
						.getFlow() - card.getRealFlow() : 0;
				meg.append("上网流量：" + Common.dataFormat(remainFlow * 1.0 / 1024)
						+ "GB");
			}
			System.out.println(meg);
	}

	/**
	 * 查询指定卡当月消费详单
	 * 
	 * @param searchNumber
	 */
	public void showAmountDetail(String searchNumber) {
		MobileCard card; // 要查询的卡
		StringBuffer meg = new StringBuffer();
		card = cards.get(searchNumber);
		meg.append("您的卡号：" + card.getCardNumber() + ",当月账单：\n");
		meg.append("套餐资费：" + card.getSerPackage().getPrice() + "元\n");
		meg.append("套餐合计消费：" + Common.dataFormat(card.getConsumAmount()) + "元\n");
		meg.append("账户余额：" + Common.dataFormat(card.getMoney()) + "元");
		// 显示本月消费详细信息
		System.out.println(meg);
			
	}

	
	/**
	 * 指定卡号换套餐
	 * 
	 * @param number
	 * @param packType
	 */
	public void changingPack(String number, String packNum) {
		MobileCard card; // 指定的手机卡
		ServicePackage pack; // 要换的套餐
		if (isExistCard(number)) {
			card = cards.get(number);
			//拿到原始的套餐价格
			double old = cards.get(number).getSerPackage().getPrice();
			// 获取要换的套餐对象
			switch (Integer.valueOf(packNum)) {
			case 1:
				pack = new TalkPackage();
				break;
			case 2:
				pack = new NetPackage();
				break;
			default:
				pack = new SuperPackage();
				break;
			}		
			if (!(card.getSerPackage().getClass().getName().equals(pack.getClass().getName()))) {
				// 该卡余额中减去当月套餐资费
				if (card.getMoney() >= pack.getPrice()) {
					card.setMoney(card.getMoney() - pack.getPrice());
					// 换套餐
					card.setSerPackage(pack);
					// 当月实际使用数据清零
					card.setRealTalkTime(0);
					card.setRealFlow(0);
					card.setRealSMSCount(0);
					// 当月消费金额设置为新套餐月资费+变更前的月资+套餐外消费
					card.setConsumAmount(old+pack.getPrice());
					System.out.print("更换套餐成功！");
					pack.showInfo();
				} else {
					System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
					return;
				}
			} else {
				System.out.println("对不起，您已经是该套餐用户，无需换套餐！");
			}

		} else {
			System.out.println("对不起，该卡号未注册，不能换套餐！");
		}
	}

	/**
	 * 为指定手机卡充值
	 * 
	 * @param number
	 *            指定充值的卡号
	 * @param money
	 *            充值金额
	 */
	public void chargeMoney(String number, double money) {
		MobileCard card; // 指定的手机卡
		if (money < 50) {
			System.out.println("对不起，最低充值金额为50元！");
			return;
		}
			card = cards.get(number);
			card.setMoney(card.getMoney() + money);
			System.out.println("充值成功，当前话费余额为" + Common.dataFormat(card.getMoney()) + "元。");
	}

	/**
	 * 添加一条指定卡的消费记录
	 * 
	 * @param number
	 *            要添加消费记录的卡
	 * @param info
	 *            要添加的消费记录
	 */
	//打印消费记录
	public void printConsumInfo(String number) {
		Writer fileWriter = null;
		boolean isWrite = false;
		try {
			fileWriter = new FileWriter("Card/" + number + "消费记录.txt",true);
			Set<String> numbers = consumInfos.keySet();
			Iterator<String> it = numbers.iterator();
			List<ConsumInfo> infos = new ArrayList<ConsumInfo>();// 存储指定卡所有消费记录
			boolean isExist = false; // 现有消费列表中是否存在此卡号消费记录，是：true 否：false
			while (it.hasNext()) {
				if (it.next().equals(number)) {
					infos = consumInfos.get(number);
					isExist = true;
					break;
				}
			}
			if (isExist) {
				// 存在 此卡消费记录，写入文本文件
				StringBuffer content = new StringBuffer();
				//StringBuffer cont = new StringBuffer();
				
				Date date = new Date();
				SimpleDateFormat sim = new SimpleDateFormat(
						"yyyy-MM-dd  hh:mm:ss");
				String format = sim.format(date);
				for (int i = 0; i < infos.size(); i++) {
					if(!isWrite){
						ConsumInfo info = infos.get(i);
						content.append("******" + number+ "消费记录******\n");
						content.append("序号\t类型\t数据（通话（条）/上网（MB）/短信（条））\n");
						content.append((i + 1) + ".\t" + info.getType() + "\t"
							+ info.getConsumData() + "\t" + format + "\n");
					}else{
						ConsumInfo info = infos.get(i);
						content.append((i + 1) + ".\t" + info.getType() + "\t"
								+ info.getConsumData() + "\t" + format + "\n");
					}
					
				}
				
				fileWriter.write(content.toString());
				fileWriter.flush();
				//清空集合的内容
				infos.clear();
				isWrite = true;
				System.out.println("消费记录打印完毕！");
			} else {
				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
//	public void printAmountDetail(String number) {
//		MobileCard card=cards.get(number);
//		Writer fileWriter = null;
//		boolean isWrite = false;
//		File file  = new File("Card/"+number + "消费记录.txt");
//		try {
//			fileWriter = new FileWriter(file,true);
//			// 存储指定卡号的所有消费记录
//			// 现有消费列表中是否存在此卡号，是：true 否：false
//			// 从consumlnfos中查找是否存在该卡的消费记录
//			boolean isExist = false;
//			for (Map.Entry<String, List<ConsumInfo>> maps : consumInfos.entrySet()) {
//				for (ConsumInfo con : maps.getValue()) {
//					if ((con.getCardNumber() == null|| (con.getConsumData() == 0) || con.getType() == null)) {
//						
//					} else {
//						isExist = true;
//						break;
//					}
//				}
//				break;
//			}
//			if (isExist) {
//				
//				StringBuffer content = new StringBuffer("******" + number + "消费记录******\n");
//				StringBuffer cont = new StringBuffer();
//				content.append("序号\t类型\t数据\t(分/MB/条)\n");
////				for (int i = 0; i < consumInfos.size();++i) {
////					List<ConsumInfo> info = consumInfos.get(number);
////					Date date=new Date();
////					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
////					String format = sim.format(date);
////					for (ConsumInfo consumInfo : info) {
////						content.append((i + 1) + ".\t" + consumInfo.getType() + "\t"
////								+ consumInfo.getConsumData() + "\t" + format + "\n");
////						
////						ServicePackage serPackage = card.getSerPackage();
////						if(serPackage instanceof CallService){
////							card.setRealTalkTime(consumInfo.getConsumData());
////						}else if(serPackage instanceof NetService){
////							card.setRealFlow(consumInfo.getConsumData());
////						}else{
////							card.setRealSMSCount(consumInfo.getConsumData());
////						}					
////					}
////				}
//				if(!isWrite){
//					int i = 1;
//					List<ConsumInfo> info = consumInfos.get(number);
//					Date date=new Date();
//					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
//					String format = sim.format(date);
//				for(ConsumInfo consumInfo : info){
//					
//					content.append(i+".\t" + consumInfo.getType() + "\t"
//							+ consumInfo.getConsumData() + "\t" + format + "\n");					
////					cont.append(i+".\t" + consumInfo.getType() + "\t"
////								+ consumInfo.getConsumData() + "\t" + format + "\n");
////					
//					ServicePackage serPackage = card.getSerPackage();
//					if(serPackage instanceof CallService){
//						card.setRealTalkTime(consumInfo.getConsumData());
//					}else if(serPackage instanceof NetService){
//						card.setRealFlow(consumInfo.getConsumData());
//					}else{
//						card.setRealSMSCount(consumInfo.getConsumData());
//					}	
//				}
//					System.out.println(content);
//					fileWriter.write(content.toString());
//					i++;
//					fileWriter.flush();
//					//每次存入消费记录后清空集合内容
//					info.clear();
//					isWrite = true;
//					System.out.println("消费记录已打印保存");
//				}else if(isWrite){
//					int i = 1;
//					List<ConsumInfo> info = consumInfos.get(number);
//					Date date=new Date();
//					SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
//					String format = sim.format(date);
//					for(ConsumInfo consumInfo : info){
//						
////						content.append(i+".\t" + consumInfo.getType() + "\t"
////								+ consumInfo.getConsumData() + "\t" + format + "\n");					
//						cont.append(i+".\t" + consumInfo.getType() + "\t"
//									+ consumInfo.getConsumData() + "\t" + format + "\n");
//						
//						ServicePackage serPackage = card.getSerPackage();
//						if(serPackage instanceof CallService){
//							card.setRealTalkTime(consumInfo.getConsumData());
//						}else if(serPackage instanceof NetService){
//							card.setRealFlow(consumInfo.getConsumData());
//						}else{
//							card.setRealSMSCount(consumInfo.getConsumData());
//						}	
//				}
//					System.out.println(cont);
//					fileWriter.write(cont.toString());
//					fileWriter.flush();
//					//每次存入消费记录后清空集合内容
//					info.clear();
//					i++;
//					System.out.println("消费记录已打印保存");
//					
//				//如果头一次写入
////				if(!isWrite){
////					System.out.println(content);
////					fileWriter.write(content.toString());
////					i++;
////					fileWriter.flush();
////					//每次存入消费记录后清空集合内容
////					info.clear();
////					isWrite = true;
////				}else if(isWrite){
////					System.out.println(cont);
////					fileWriter.write(cont.toString());
////					fileWriter.flush();
////					//每次存入消费记录后清空集合内容
////					info.clear();
////					i++;
//				}
//				
//				
//			} else {
//				System.out.println("对不起，不存在此卡号的消费记录，不能打印");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (fileWriter != null) {
//				try {
//					fileWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	public void showConsumMeg(String number) {
		//获取号码
		MobileCard card=cards.get(number);
		FileReader fr = null;
		BufferedReader br = null;
		//打印文本内容
		try {
			fr = new FileReader("Card/"+number+"消费记录.txt");
			br = new BufferedReader(fr);
			String line = br.readLine();
			while(line!=null){
			System.out.println(line);
			line = br.readLine();
			}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		System.out.println("消费记录显示完毕");
	}
	//打印消费记录1
//	public void printConsumInfo(String number){
//		FileReader fr = null;
//		BufferedReader br = null;
//		try {
//			fr = new FileReader(number+"消费记录.txt");	
//			br = new BufferedReader(fr);
//			String line = br.readLine();
//			while(line!=null){
//				System.out.println(line);
//				line = br.readLine();
//			}
//			Set<String> numbers = consumInfos.keySet();
//			Iterator<String> it = numbers.iterator();
//			List<ConsumInfo> infos = new ArrayList<ConsumInfo>();//存储指定卡所有消费记录
//			boolean isExist = false; // 现有消费列表中是否存在此卡号消费记录，是：true 否：false
//			while (it.hasNext()) {
//				if (it.next().equals(number)) {
//					infos = consumInfos.get(number);
//					isExist = true;
//					break;
//				}
//			}
//			if(isExist){
//				//存在 此卡消费记录，写入文本文件
//				StringBuffer content = new StringBuffer("******"+number+"消费记录******\n");
//				//content.append("序号\t类型\t数据（通话（条）/上网（MB）/短信（条））\n");
//				//Date date=new Date();
//				//SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
//				//String format = sim.format(date);
//				for(int i=0;i<infos.size();i++){
//					ConsumInfo info = infos.get(i);
//					content.append((i+1)+".\t"+info.getType()+"\t"+info.getConsumData()+"\n");	
//				}
////				fileWriter.write(content.toString());
////				fileWriter.flush();
//				System.out.println(content);
//				System.out.println("消费记录打印完毕！");
//			}else{
//				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
//			}			
//		} catch (IOException e) {			
//			e.printStackTrace();
//		}finally{
//			if(fr!=null){
//				try {
//					br.close();
//					fr.close();
//					
//				} catch (IOException e) {					
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	public void addConsumInfo(String number, ConsumInfo info) {
		Set<String> numbers = consumInfos.keySet();
		Iterator<String> it = numbers.iterator();
		List<ConsumInfo> infos = new ArrayList<ConsumInfo>();
		boolean isExist = false; // 现有消费列表中是否存在此卡号消费记录，是：true 否：false
		while (it.hasNext()) {
			if (it.next().equals(number)) {
				// 消费集合中已有该卡号消费记录，则找到该卡号的消费记录集合，添加一条即可
				infos = consumInfos.get(number);
				infos.add(info);
				isExist = true;
				System.out.println("已添加一条消费记录。");
				break;
			}
		}
		// 该集合中没有此卡号消费记录，则添加
		if (!isExist) {
			infos.add(info);
			consumInfos.put(number, infos);
			System.out.println("不存在此卡的消费记录，已添加一条消费记录。");
		}
	}
	/**
	 * 使用嗖嗖
	 * @param number 当前卡号
	 * @throws Exception 
	 */
	public void userSoso(String number)  {		
		MobileCard card = cards.get(number); // 获取此卡对象
		ServicePackage pack = card.getSerPackage(); // 获取此卡所属套餐
		Random random = new Random();
		int ranNum = 0;
		int temp = 0;  //记录各场景中实际消费数据
		do{
			
			ranNum = random.nextInt(6);// 生成一个0~5之前的随机数
			Scene scene = scenes.get(ranNum); //获取该序号所对应的场景
			switch (ranNum) {
			//序号为0或1为通话场景
			case 0:
			case 1:
				// 判断该卡所属套餐是否支持通话功能
				if (pack instanceof CallService) {
					// 执行通话方法
					System.out.println(scene.getDescription());
					CallService callService = (CallService) pack;
					try {
						temp = callService.call(scene.getData(), card);
					} catch (Exception e) {							
						e.printStackTrace();
					}
					// 添加一条消费记录
					addConsumInfo(number, new ConsumInfo(number,scene.getType(), temp));
					break;
				} else {
					// 如果该卡套餐不支持通话功能，则重新生成随机数选择其他场景
					continue;
				}
				//序号为2或3为发短信场景
			case 2:
			case 3:
				// 判断该卡所属套餐是否支持短信功能
				if (pack instanceof SendService) {
					// 执行发短信方法
					System.out.println(scene.getDescription());
					SendService sendService = (SendService) pack;
					try {
						temp = sendService.sendMessage(scene.getData(), card);
					} catch (Exception e) {													
						e.printStackTrace();
					}
					// 添加一条消费记录
					addConsumInfo(number, new ConsumInfo(number,scene.getType(), temp));
					break;
				} else {
					// 如果该卡套餐不支持发短信功能，则重新生成随机数选择其他场景
					continue;
				}
				//序号为4或5为上网场景
			case 4:
			case 5:
				// 判断该卡所属套餐是否支持上网功能
				if (pack instanceof NetService) { 
					System.out.println(scene.getDescription());
					NetService netService = (NetService) pack;
					// 执行上网方法
					try {
						temp = netService.netPlay(scene.getData(), card);
					} catch (Exception e) {						
						e.printStackTrace();
					}
					// 添加一条消费记录
					addConsumInfo(number, new ConsumInfo(number,scene.getType(), temp));
					break;
				} else {
					// 如果该卡套餐不支持上网功能，则重新生成随机数选择其他场景
					continue;
				}				
			}	
			break;
		}while(true);
	}

	/**
	 * 根据套餐序号返回套餐对象
	 * 
	 * @param packNum
	 *            套餐序号
	 * @return 套餐对象
	 */
	public ServicePackage createPack(int packId) {
		ServicePackage pack = null;
		switch (packId) {
		case 1:
			pack = new TalkPackage();
			break;
		case 2:
			pack = new NetPackage();
			break;
		case 3:
			pack = new SuperPackage();
			break;
		}
		return pack;
	}
	
	/**
	 * 显示资费说明
	 */
	public void showDescription(){
		Reader rd = null;
		try{
			rd = new FileReader("套餐资费说明.txt");
			char[] content = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			while((len=rd.read(content))!=-1){
					sb.append(content,0,len);  //拼接字符串
				}
				System.out.println(sb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//修改密码
	public void updatePwd(String mobileNumber,String pwd){
		//ObjectFile file=new ObjectFile();
		//Map<String, MobileCard> objectInputFile = file.objectInputFile();
		for(Map.Entry<String, MobileCard> maps : cards.entrySet()){
			//集合中Key中有该卡对象，
			if(maps.getKey().equals(mobileNumber)){
				//修改Value值
				maps.getValue().setPassWord(pwd);
			}
		}
	}	
}
