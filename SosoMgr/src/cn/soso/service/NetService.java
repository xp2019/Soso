package cn.soso.service;

import cn.soso.entity.MobileCard;

/**
 * 上网服务
 * @author 北大青鸟
 *
 */
public interface NetService {
	//上网
     public int netPlay(int flow,MobileCard card) throws Exception;
}
