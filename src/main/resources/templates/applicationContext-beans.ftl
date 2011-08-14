<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:aop="http://www.springframework.org/schema/aop" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd         http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd         http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd         http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-3.0.xsd         http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">
<#list domains as ref>
<bean id="${ref?uncap_first}Dao" class="com.ijqg.pos.dao.${ref}DaoImpl"></bean>
<bean id="${ref?uncap_first}Service" class="com.ijqg.pos.dao.${ref}ServiceImpl"></bean>
</#list>
</beans>