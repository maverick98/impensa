/**
  * This file is part of impensa.
  * CopyLeft (C) BigBang<->BigCrunch.All Rights are left.
  *
  * 1) Modify it if you can understand.
  * 2) If you distribute a modified version, you must do it at your own risk.
  *
  */
package org.impensa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author msahu98
 */
@Configuration
@ImportResource({"classpath:spring/neo4j/spring-neo4j.xml"})
public class ImpensaSpringConfig {

}
