package cn.soso.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import cn.soso.entity.*;


public class ObjectFile {
	/*
	 * 序列化
	 */
	public void objectOutputFile(Map<String, MobileCard> cards){
		File file=new File("Card/cardUser.txt");
		OutputStream out = null;
		ObjectOutputStream obs = null;
		try {
			out = new FileOutputStream(file);
			obs = new ObjectOutputStream(out);
			obs.writeObject(cards);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				obs.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	/*
	 * 反序列化
	 */
	@SuppressWarnings("unchecked")
	public Map<String, MobileCard>  objectInputFile(){
		Map<String, MobileCard>  cards=null;
		File file=new File("Card/cardUser.txt");
		InputStream in = null;
		ObjectInputStream oni = null;
		try {
			in=new FileInputStream(file);
			oni=new ObjectInputStream(in);
			cards=(Map<String, MobileCard>) oni.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				oni.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return cards;
	}
	
	//消费信息序列化
	public void comsuobjectOutputFile(Map<String, List<ConsumInfo>> consumInfos){
		File file=new File("Card/cardUser.txt");
		OutputStream out = null;
		ObjectOutputStream obs = null;
		try {
			out = new FileOutputStream(file);
			obs = new ObjectOutputStream(out);
			obs.writeObject(consumInfos);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				obs.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//消费信息反序列化
	@SuppressWarnings("unchecked")
	public Map<String, List<ConsumInfo>> comsuobjectInputFile () {
		Map<String, List<ConsumInfo>> consumInfos = null;
		File file=new File("Card/cardUser.txt");
		InputStream in = null;
		ObjectInputStream oni = null;
		try {
			in=new FileInputStream(file);
			oni=new ObjectInputStream(in);
			consumInfos= (Map<String, List<ConsumInfo>>) oni.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				oni.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return consumInfos;
	}
}
