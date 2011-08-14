package $domain.getDaoPackageName();

import org.springframework.stereotype.Repository;
import $domain.getDomainPackageName().$domain.getDomainName();
<#if $domain.isHasKey()>
import $domain.getDomainPackageName().$domain.getDomainKey();
</#if>


/**
 * Generated $time by HibernateGenericDaoTools 0.1 beta	
 * @author idor(sjbwylbs@gmail.com)
 */
@Repository
public class ${domain.getDomainName()}DAOImpl extends BaseDAOImpl<$domain.getDomainName(), $domain.getDomainKey()> implements ${domain.getDomainName()}Dao {

}
