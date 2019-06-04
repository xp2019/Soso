package cn.soso.service;

import cn.soso.entity.MobileCard;

/**
 * 通话服务接口
 * @author 北大青鸟
 *
 */
public interface CallService {
	//打电话
      public int call(int minCount,MobileCard card) throws Exception;
}
