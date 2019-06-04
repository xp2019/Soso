package cn.soso.entity;
/**
 * 嗖嗖移动卡套餐
 * @author 北大青鸟
 *
 */
public abstract class ServicePackage {	
	public double price;  //套餐月资费(元)
     
    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//显示套餐数据
    public abstract void showInfo();
}
