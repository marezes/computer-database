package com.excilys.cdb.springConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.cdb.persistence", "com.excilys.cdb.service", "com.excilys.cdb.mapper", "com.excilys.cdb.validator", "com.excilys.cdb.dbConfig"})
public class SpringConfig {
	
}
