package cn.soso.service;

import cn.soso.entity.MobileCard;

/**
 * ���ŷ���
 * @author ��������
 *
 */
public interface SendService {
	//������
     public int sendMessage(int count,MobileCard card) throws Exception;
}
