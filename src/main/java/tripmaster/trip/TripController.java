package tripmaster.trip;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tripmaster.common.trip.ProposalForm;
import tripmaster.common.trip.ProviderData;

/**
 * API class for trip methods
 */
@RestController
public class TripController {

	private Logger logger = LoggerFactory.getLogger(TripController.class);
	@Autowired private TripService tripService;

	/**
	 * Gets the proposed trips for a given user, attractions and reward points combination.
	 * @param proposalForm the structure containing user, attractions and reward points.
	 * @return List of ProviderData proposed for the user (name, price and id).
	 */
	@GetMapping("/calculateProposals")
	public List<ProviderData> calculateProposals(@RequestBody ProposalForm proposalForm) {
		logger.debug("calculateProposals for User " + proposalForm.user.userName 
			+ " with Attraction list of size " + proposalForm.attractions.size()
			+ " and " + proposalForm.cumulativeRewardPoints + "reward points");
		return tripService.calculateProposals(proposalForm.user, proposalForm.attractions, proposalForm.cumulativeRewardPoints);
	}
}
