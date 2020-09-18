package tripmaster.trip;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tripPricer.Provider;
import tripPricer.TripPricer;
import tripmaster.common.attraction.AttractionNearby;
import tripmaster.common.trip.ProviderData;
import tripmaster.common.user.User;

/**
 * Class for trip services. Implements TripService interface.
 * @see tripmaster.trip.TripService
 */
@Service
public class TripServiceImpl implements TripService {
	
	@Autowired private TripPricer tripPricer;	
	
	private Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
	
	/**
	 * Gets the proposed trips for a given user, attractions and reward points combination.
	 * @param user for whom the proposals shall be computed (based on his preferences).
	 * @param attractions the list of AttractionNearby to be parsed for the trip proposals.
	 * @param cumulativeRewardPoints the number of reward points to be taken into account for pricing.
	 * @return List of ProviderData proposed for the user (name, price and id).
	 */
	@Override
	public List<ProviderData> calculateProposals(User user, List<AttractionNearby> attractions, int cumulativeRewardPoints) {
		logger.debug("calculateProposals userName = " + user.userName 
			+ " and attractionList of size " + attractions.size()
			+ " and rewardPoints = cumulativeRewardPoints");
		List<ProviderData> providers = new ArrayList<ProviderData>();
		for (AttractionNearby a : attractions) {
			List<Provider> proposals = tripPricer.getPrice(
					TRIP_PRICER_KEY, a.id, 
					user.userPreferences.numberOfAdults, 
					user.userPreferences.numberOfChildren, 
					user.userPreferences.tripDuration, 
					cumulativeRewardPoints);
			for (Provider provider : proposals) {
				providers.add(newProviderData(provider));
			}
		}
		return providers;
	}
	
	// data conversion helper
	private ProviderData newProviderData(Provider provider) {
		return new ProviderData(provider.name, provider.price, provider.tripId);
	}
}
