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
@RequestMapping("/{familyMemberName}")
public class FriedflixFamilyMemberController {
	
	@Autowired
	private final FriedflixFamilyMemberRepository familyMemberRepository;
	
	@Autowired
	FriedflixFamilyMemberController(FriedflixFamilyMemberRepository familyMemberRepository) {
		this.familyMemberRepository = familyMemberRepository;
	}	
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String familyMemberName) {
		this.validateUser(familyMemberName);
		FriedflixFamilyMember result = this.familyMemberRepository.save(new FriedflixFamilyMember(familyMemberName));
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
    private void validateUser(String familyMemberName) {
		if (this.familyMemberRepository.findByName(familyMemberName).isPresent()) {
			throw new FamilyMemberAlreadyExistsException(familyMemberName);
		}
	}
    
}
