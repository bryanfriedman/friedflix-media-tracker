package friedflix.mediatracker;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/{familyMemberName}/media")
public class FriedflixMediaTrackerController {
	
	@Autowired
	private final FriedflixMediaTrackerItemRepository mediaTrackerItemRepository;
	
	@Autowired
	private final FriedflixFamilyMemberRepository familyMemberRepository;
	
	@Autowired
	FriedflixMediaTrackerController(FriedflixMediaTrackerItemRepository mediaTrackerItemRepository,
			FriedflixFamilyMemberRepository familyMemberRepository) {
		this.mediaTrackerItemRepository = mediaTrackerItemRepository;
		this.familyMemberRepository = familyMemberRepository;
	}	
	

	@RequestMapping(method = RequestMethod.GET)
	Collection<FriedflixMediaTrackerItem> readBookmarks(@PathVariable String familyMemberName) {
		this.validateUser(familyMemberName);
		return this.mediaTrackerItemRepository.findByFamilyMemberName(familyMemberName);
	}
    
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String familyMemberName, @RequestBody FriedflixMediaTrackerItem input) {
		this.validateUser(familyMemberName);

		return this.familyMemberRepository
				.findByName(familyMemberName)
				.map(familyMember -> {
					FriedflixMediaTrackerItem result = mediaTrackerItemRepository.save(new FriedflixMediaTrackerItem(familyMember,
							input.title, input.mediaType, input.service, input.watched, input.notes, input.seasonNumber, input.tmdbId));

					URI location = ServletUriComponentsBuilder
						.fromCurrentRequest().path("/{id}")
						.buildAndExpand(result.getId()).toUri();

					return ResponseEntity.created(location).build();
				})
				.orElse(ResponseEntity.noContent().build());

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{mediaItemId}")
	FriedflixMediaTrackerItem readMediaItem(@PathVariable String familyMemberName, @PathVariable Long mediaItemId) {
		this.validateUser(familyMemberName);
		return this.mediaTrackerItemRepository.findOne(mediaItemId);
	}
	
	
    private void validateUser(String familyMemberName) {
		this.familyMemberRepository.findByName(familyMemberName).orElseThrow(
				() -> new FamilyMemberNotFoundException(familyMemberName));
	}
    
}
