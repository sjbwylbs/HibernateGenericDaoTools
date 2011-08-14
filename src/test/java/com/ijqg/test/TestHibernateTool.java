package com.ijqg.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.ijqg.tools.Domain;
import com.ijqg.tools.HibernateGenericDaoTool;

/**
 * @author idor(sjbwylbs@gmail.com)
 */
public class TestHibernateTool {




	
	@Test
	public void testMatch(){
		assertEquals("DAOImpl".matches("DAOImpl|Dao") ? "dao" :"service","dao");
		assertEquals("Dao".matches("DAOImpl|Dao") ? "dao" :"service","dao");
	}
	
	@Test
	public void testCreate() {
		
		Domain d=new Domain();
		d.setDaoPackageName("com.ijqg.pos.dao");
		d.setServicePackageName("com.ijqg.pos.services");
		d.setDomainPackageName("com.ijqg.pos.model");
		
		HibernateGenericDaoTool tool = new HibernateGenericDaoTool(d);
		//是否覆盖原有文件
		tool.setCoverd(false);
	
		tool.setDomainPath("C:\\Users\\idor\\Documents\\workspace\\posexample\\src\\main\\java\\com\\ijqg\\pos\\model");
		tool.setDaoPath("C:\\Users\\idor\\Documents\\workspace\\posexample\\src\\main\\java\\com\\ijqg\\pos\\dao");
		tool.setServicePath("C:\\Users\\idor\\Documents\\workspace\\posexample\\src\\main\\java\\com\\ijqg\\pos\\services");
		tool.setBeansPath("C:\\Users\\idor\\Documents\\workspace\\posexample\\src\\main\\resources\\META-INF\\spring\\applicationContext-beans.xml");
		assertNotNull(tool.getDomainPath());
		//生成dao,daoimpl,service,serviceimpl
		tool.create();
		//生成用于Spring的beans引用
		tool.setCoverd(true);
		tool.createBeans();
		System.out.print("finish.");
	}
}
