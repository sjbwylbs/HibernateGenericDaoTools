package com.ijqg.tools;

//package com.ijqg.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author idor(sjbwylbs@gmail.com)
 */
public class HibernateGenericDaoTool {

	final static Logger log = Logger.getLogger(HibernateGenericDaoTool.class);
	//Tool Settings
	String daoPath;
	String servicePath;
	String domainPath;
	String beansPath;
	File rootFile;
	Configuration cfg;
	Domain domain;
	Properties p;
	boolean isCoverd = false;
	//model variable Settings
	Map<String, Object> datas;

	final static String T_DAO = "Dao";
	final static String T_DAO_IMPL = "DAOImpl";
	final static String T_SERVICE = "Service";
	final static String T_SERVICE_IMPL = "ServiceImpl";
	final static String T_BEAN_LIST = "applicationContext-beans";

	//Tempate Settings
	List<String> templateNames;
	Map<String, Template> templates;

	public HibernateGenericDaoTool(Domain domain) {
		log.debug("HibernateGenericDaoTool init");
	try {	
		this.domain = domain;
		cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		p = new Properties();
	
			p.load(new FileInputStream("src/main/resources/typeMapping.properties"));
		} catch (FileNotFoundException e) {
			log.warn(e.getMessage());
		} catch (IOException e) {
			log.warn(e.getMessage());
		}

		templateNames = new ArrayList<String>();
		templateNames.add(T_DAO);
		templateNames.add(T_DAO_IMPL);
		templateNames.add(T_SERVICE);
		templateNames.add(T_SERVICE_IMPL);
		templateNames.add(T_BEAN_LIST);

		/*
		 *  get the Template object.  This is the parsed version of your
		 *  template input file.  Note that getTemplate() can throw
		 *   ResourceNotFoundException : if it doesn't find the template
		 *   ParseErrorException : if there is something wrong with the VTL
		 *   Exception : if something else goes wrong (this is generally
		 *        indicative of as serious problem...)
		 */
		templates = new HashMap<String, Template>();

		Template template = null;
		for (String name : templateNames) {

			try {
				template = cfg.getTemplate(name + ".ftl");
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.debug("find template:" + File.separator + name + ".ftl");
			templates.put(name, template);
		}
		datas=new HashMap<String,Object>();
	}

	public void createBeans() {

		List<String> domains = new ArrayList<String>();
		//循环目录文件
		String[] fileNames = getRootFile().list();
		for (String fileName : fileNames) {
			String domain = fileName.substring(0, fileName.indexOf("."));
			if (domain.contains("Id")) {
				continue;
			}
			domains.add(domain);
		}

		if (datas!=null && datas.containsKey("domain")) {
			datas.remove("domain");
		}

		datas.put("domains", domains);

		try {

			File outFile = new File(this.getBeansPath());
			if (outFile.exists()) {
				if (!this.isCoverd()) {
					//如果不能覆盖则直接跳过
					log.warn(outFile + " is exists.pass it.");
					return;
				}
			} else {
				log.debug("out file:" + outFile);
				if (!outFile.createNewFile()) {
					//如果建立文件失败则直接跳过
					log.warn(outFile + " create failure.");
					return;
				}
			}

			FileWriter fw = new FileWriter(outFile);
			/*
			 *  Now have the template engine process your template using the
			 *  data placed into the context.  Think of it as a  'merge'
			 *  of the template and the data to produce the output stream.
			 */

			BufferedWriter writer = new BufferedWriter(fw);
			templates.get(T_BEAN_LIST).process(datas, writer);
			/*
			 *  flush and cleanup
			 */

			writer.flush();
			writer.close();
			fw.close();
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	public void create() {
		/*
		*  Make a context object and populate with the data.  This
		*  is where the Velocity engine gets the data to resolve the
		*  references (ex. $list) in the template
		*/

		//循环目录文件
		String[] fileNames = getRootFile().list();
		for (String fileName : fileNames) {
			domain.setDomainName(fileName.substring(0, fileName.indexOf(".")));
			if (domain.getDomainName().contains("Id")) {
				continue;
			}
			try {

				//解释java类获取ID类型
				String id = parse(getDomainPath() + fileName);
				if (id != null) {

					//设置ID
					//context.put(KEY_domainKey, id);
					Object tid = getP().get(id);
					//根据类型映射,转换ID
					if (tid != null) {
						id = tid.toString();
					}
					domain.setDomainKey(id);

					if (id.indexOf(domain.getDomainName()) >= 0) {
						//是否有自定义的Id
						//context.put(KEY_hasKey, true);
						domain.setHasKey(true);
					} else {
						//context.put(KEY_hasKey, false);
						domain.setHasKey(false);
					}

					//dao包名
					//context.put(KEY_daoPackageName, getDaoPackageName());
					//domain.setDaoPackageName(daoPackageName)
					//service包名
					//context.put(KEY_servicePackageName, getServicePackageName());
					//domain包名
					//context.put(KEY_domainPackageName, getDoaminPackageName());
					//domain名
					//context.put(KEY_domainName, domainName);
					//domain.setDomainName(domainName);
					datas.put("domain", domain);
					datas.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

					//循环模版,处理模版
					for (Map.Entry<String, Template> t : templates.entrySet()) {
						String outPath = null;

						if (t.getKey().matches(T_DAO + "|" + T_DAO_IMPL)) {
							outPath = this.getDaoPath();
						} else if (t.getKey().matches(T_DAO + "|" + T_DAO_IMPL)) {
							outPath = this.getServicePath();
						} else {
							break;
						}

						log.debug("templateName:" + t.getKey() + ";outPath:" + outPath);

						if (!outPath.endsWith(File.separator))
							outPath += File.separator;

						String outFileName = outPath + domain.getDomainName() + t.getKey() + ".java";

						File outFile = new File(outFileName);
						if (outFile.exists()) {
							if (!this.isCoverd()) {
								//如果不能覆盖则直接跳过
								log.warn(outFileName + " is exists.pass it.");
								break;
							}
						} else {
							log.debug("out file:" + outFileName);
							if (!outFile.createNewFile()) {
								//如果建立文件失败则直接跳过
								log.warn(outFileName + " create failure.");
								break;
							}
						}

						FileWriter fw = new FileWriter(outFile);
						/*
						 *  Now have the template engine process your template using the
						 *  data placed into the context.  Think of it as a  'merge'
						 *  of the template and the data to produce the output stream.
						 */

						BufferedWriter writer = new BufferedWriter(fw);

						if (t.getValue() != null) {
							t.getValue().process(datas, writer);
						} else {
							log.debug("You didn't had template " + t.getKey());
						}

						/*
						 *  flush and cleanup
						 */

						writer.flush();
						writer.close();
						fw.close();
					}
				} else {
					log.debug("sorry,id is null.");
				}
			} catch (IOException ioe) {
				log.error(ioe.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			}

		}

	}

	public String getDaoPath() {
		return daoPath;
	}

	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}

	public String getServicePath() {
		return servicePath;
	}

	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}

	public String getDomainPath() {
		return domainPath;
	}

	public void setDomainPath(String domainPath) {
		this.rootFile = new File(domainPath);
		if (!rootFile.exists() || (!rootFile.isDirectory())) {
			log.warn("List失败！找不到目录：" + domainPath);
			domainPath = null;
		}

		if (!domainPath.endsWith(File.separator)) {
			domainPath += File.separator;
		}
		this.domainPath = domainPath;
	}

	public String getBeansPath() {
		return beansPath;
	}

	public void setBeansPath(String beansPath) {
		this.beansPath = beansPath;
	}

	public File getRootFile() {
		return rootFile;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Properties getP() {
		return p;
	}

	public void setP(Properties p) {
		this.p = p;
	}

	public boolean isCoverd() {
		return isCoverd;
	}

	public void setCoverd(boolean isCoverd) {
		this.isCoverd = isCoverd;
	}

	/**
	 * 根据文件取出Id的类型
	 * @param parsefile
	 * @return
	 */
	public String parse(String parsefile) {
		String id = null;
		if (parsefile != null && parsefile.endsWith(".java")) {
			File file = new File(parsefile);
			if (file.exists()) {
				log.debug("handler domain file name:" + parsefile);
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = null;
					while ((line = br.readLine()) != null) {
						//comment '.*?'
						//private Integer id;
						if (line.matches(".*private \\w{3,30} id.*")) {
							id = line.split(" ")[1];
							log.debug(file.getName() + "'s key type is:" + id);
							break;
						}
					}
					br.close();
				} catch (FileNotFoundException fnfe) {
					log.warn(fnfe);
				} catch (IOException e) {
					log.warn(e);
				}
			} else {
				log.debug("file not exists:" + parsefile);
			}
		}

		return id;
	}

}
