package org.pgl.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pgl.Model.LanguageBean;

public class LM_Util
{

	// �ͻ�������-���пͻ�������
	public static Map<Integer, String> map = new HashMap<Integer, String>();

	// �ͻ�������-���������
	public static Map<Integer, String> noMixtureMap = new HashMap<Integer, String>();

	// �����Բ�����ʱ��Ҫ���´�list ֻ��languageName��languageCode����ֵ��
	public static List<LanguageBean> languageList = new ArrayList<LanguageBean>();

	/*
	 * ������Դ��
	 * 
	 * ��Ϊ���ͻ���Ҫ��ĸ�ʽ��ͳһ,����ÿ������һ���ͻ��˾�ҪΪ�ÿͻ���������Ӧ�ĵ�������
	 * 
	 * ���¿ͻ��˶�Ӧ��CODE��������ݿ���clienttype��һ��
	 * 
	 * ��Ϊ�ͻ��˿ɹ����µ�����Դ��Ǳ��������
	 */
	public static int ALL_CLIENT_CODE = 0;
	public static int ANDROID_CLIENT_CODE = 1;
	public static int IOS_CLIENT_CODE = 2;
	public static int PC_CLIENT_CODE = 3;
	public static int JAVA_CLIENT_CODE = 5;
	// ��ҳ-ÿҳ��ʾ����
	public static int SHOW_COUNT = 14;

	// ê��֧�ֶ�KEY�������޷�ֱ��ͨ��FORM�ύ-��˲����鴮����ʽ������
	public static String TAB_SEPARATOR = "`��#";
	public static String GROUP_SEPARATOR = "`��!";
	public static String DATA_SEPARATOR = "`��=";

	// �����ߵ������ַ
	public static ArrayList<String> mailReceiverList = new ArrayList<String>();
//���ݿ��������ö��
	public static enum OperationType{
		INSERT(1),UPDATE(2),DELETE(3),QUERY(4);
		private int value;
		public int getValue(){
			return value;
		}
		private OperationType(int _value){
			this.value = _value;
		}
		
	}
}
