package cn.soso.entity;
/**
 * ���ƶ����ײ�
 * @author ��������
 *
 */
public abstract class ServicePackage {	
	public double price;  //�ײ����ʷ�(Ԫ)
     
    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//��ʾ�ײ�����
    public abstract void showInfo();
}
