package tripmaster.trip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tripPricer.TripPricer;

/**
 * Bean class to access tripPricer library
 */
@Configuration
public class TripPricerBean {
	
	@Bean
	public TripPricer tripPricer() {
		return new TripPricer();
	}
}
