package $domain.getDaoPackageName();

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import $domain.getDomainPackageName().$domain.getDomainName();
<#if $domain.isHasKey()>
import $domain.getDomainPackageName().$domain.getDomainKey();
</#if>

/**
 * Generated $time by HibernateGenericDaoTools 0.1 beta	
 * @author idor(sjbwylbs@gmail.com)
 */
public interface ${domain.getDomainName()}Dao extends GenericDAO<$domain.getDomainName(), $domain.getDomainKey()> {

}
