package net.okdi.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
//生成视频文件的首帧为图片  
//windows下的版本  


public class CreatePh {
	// public static final String FFMPEG_PATH = "E:/ffmpeg/ffmpeg.exe";
	public static boolean processImg(String veido_path, String ffmpeg_path) {
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("0");// 这个参数是设置截取视频多少秒时的画面
		 commands.add("-t");
		 commands.add("00:00:05");
		commands.add("-s");
		commands.add("200x200");
		String ss = veido_path.substring(0, veido_path.lastIndexOf("."))
				.replaceFirst("vedio", "file") + "jie.jpg";
		commands.add(ss);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			System.out.println("截取成功");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	// 视频缩略图截取
	// inFile 输入文件(包括完整路径)
	// outFile 输出文件(可包括完整路径)
	public static boolean transfer(String inFile, String outFile,String startTime) {
		String command = "ffmpeg -i " + inFile
				+ " -y -f image2 -ss "+startTime+" -t 00:00:01 -s  250x200  -vframes 1 "
				+ outFile;
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(line);
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		processImg("D:/11.jpg", "D:/ffmpeg/ffmpeg.exe");
	}
}