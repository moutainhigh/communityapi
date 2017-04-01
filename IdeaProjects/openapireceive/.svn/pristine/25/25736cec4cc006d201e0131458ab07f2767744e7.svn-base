package net.okdi.core.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GenerateRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		generateMbgConfiguration();
	}

	private static void generateMbgConfiguration() {
		/*
		 * Mybatis自带Generator工具生成相应东西
		 */
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		// 配置文件的位置 可以为项目路径也可以为磁盘的绝对路径
		File configFile = new File("./src/main/resources/configuration.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		try {
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("生成Mybatis配置成功！");
	}

}
