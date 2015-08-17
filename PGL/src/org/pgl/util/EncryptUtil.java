package org.pgl.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @category ʹ��DES��String��byte[]�������ݽ��м��ܽ���
 * @author Administrator
 * 
 */
public class EncryptUtil
{
	public static final String ENCRYPT_KEY = "feinno_amigo";

	/**
	 * ����
	 * 
	 * @param content
	 *            ��Ҫ���ܵ�����
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public static byte[] encrypt(String content, String password)
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// ����������
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// ��ʼ��
			byte[] result = cipher.doFinal(byteContent);
			return result; // ����
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param content
	 *            ����������
	 * @param password
	 *            ������Կ
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password)
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(password.getBytes());
			kgen.init(128, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// ����������
			cipher.init(Cipher.DECRYPT_MODE, key);// ��ʼ��
			byte[] result = cipher.doFinal(content);
			return result; // ����
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��������ת����16����
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[])
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1)
			{
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * ��16����ת��Ϊ������
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr)
	{

		System.out.println("sssss" + hexStr);
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static String getDecString(String encpasswd)
	{
		byte[] decryptFrom = parseHexStr2Byte(encpasswd);
		byte[] decryptResult = decrypt(decryptFrom, EncryptUtil.ENCRYPT_KEY);
		return new String(decryptResult);
	}

	public static String getEncString(String string)
	{
		byte[] encryptResult = encrypt(string, EncryptUtil.ENCRYPT_KEY);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}

	public static void main(String[] args)
	{
		String content = "admin";
		String password = ENCRYPT_KEY;
		// ����
		System.out.println("����ǰ��" + content);
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		System.out.println("���ܺ�" + encryptResultStr);
		// ����
		byte[] decryptFrom = parseHexStr2Byte("8F3D0A7A9B6E49616F6AB078E93A286C");
		byte[] decryptResult = decrypt(decryptFrom, password);
		System.out.println("���ܺ�" + new String(decryptResult));
	}
}
