package friedflix.mediatracker;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class FamilyMemberNotFoundException extends RuntimeException {
	public FamilyMemberNotFoundException(String familyMemberName) {
		super("could not find user '" + familyMemberName + "'.");
	}
}
