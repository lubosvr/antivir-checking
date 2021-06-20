package cz.partners.repository.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Class added to enable auditing on entity.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
