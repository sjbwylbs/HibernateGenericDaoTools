<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans">
<#list domains as ref>
<bean id="${ref?uncap_first}Dao" class="com.ijqg.pos.dao.${ref}DaoImple"></bean>
<bean id="${ref?uncap_first}Service" class="com.ijqg.pos.dao.${ref}ServiceImple"></bean>
</#list>
</beans>