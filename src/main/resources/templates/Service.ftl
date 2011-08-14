package $domain.getServicePackageName();

import $domain.getDomainPackageName().$domain.getDomainName();
<#if $domain.isHasKey()>
import $domain.getDomainPackageName().$domain.getDomainKey();
</#if>


/**
 * Generated $time by HibernateGenericDaoTools 0.1 beta	
 * @author idor(sjbwylbs@gmail.com)
 */
public interface ${domain.getDomainName()}Service extends BaseService<$domain.getDomainName(), $domain.getDomainKey()> {

}
