package com.xuxinyu.uidriver.basehandle;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProLoad {

	private static ProLoad instance = null;

	private ProLoad() {
	}

	/* 获取全局实例 */
	public static ProLoad getInstance() {
		synchronized (ProLoad.class) {
			if (instance == null) {
				instance = new ProLoad();
			}
			return instance;
		}
	}

	/* ProLoad PR = new ProLoad();
	 * 寻找maven的resources目录下的Prop erties文件并加载文件后返回Properties对象
	 */
	public Properties loadproperties(String filename) throws IOException {

		InputStream in = ProLoad.getInstance().getClass()
				.getResourceAsStream("/" + filename);

		Properties p = new Properties();
		InputStreamReader inputStreamreader = new InputStreamReader(in, "UTF-8");

		p.load(inputStreamreader);

		return p;
	}

	public InputStream loadexcel(String filename) {

		InputStream in = ProLoad.getInstance().getClass()
				.getResourceAsStream("/" + filename);
		return in;

	}

	public static void main(String args[]) {

		try {

			Properties proe = ProLoad.getInstance().loadproperties(
					"findElement.properties");

			String preKey = proe.getProperty("MyMail.HomePage.iframeforlogin");
			System.out.println(preKey);

			String path = ProLoad.class.getResource("/").getFile();
			// String path1 = ProLoad.class.getResourceAsStream(name)
			System.out.println(path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
