package $domain.getServicePackageName();

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import $domain.getDaoPackageName().${domain.getDomainName()}Dao;
import $domain.getDomainPackageName().$domain.getDomainName();
<#if $domain.isHasKey()>
import $domain.getDomainPackageName().$domain.getDomainKey();
</#if>


/**
 * Generated $time by HibernateGenericDaoTools 0.1 beta	
 * @author idor(sjbwylbs@gmail.com)
 */
@Service
public class ${domain.getDomainName()}ServiceImpl implements ${domain.getDomainName()}Service {

	final static Logger logger = LoggerFactory.getLogger(${domain.getDomainName()}ServiceImpl.class);
	
	$domain.getDomainName()Dao dao;
	@Autowired
	public void setDao($domain.getDomainName()Dao dao) {
		this.dao = dao;
	}
	
	@Override
	public void save($domain.getDomainName() t) {
		dao.save(t);
	}

	@Override
	public void delete($domain.getDomainKey() id) {
	}

	@Override
	public List<$domain.getDomainName()> findAll() {
		return null;
	}

	@Override
	public List<$domain.getDomainName()> search(ISearch search) {
		return null;
	}

	@Override
	public SearchResult<$domain.getDomainName()> searchAndCount(ISearch search) {
		return null;
	}

	@Override
	public $domain.getDomainName() findById($domain.getDomainKey() id) {
		return null;
	}

	@Override
	public void flush() {
	}

}
