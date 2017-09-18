package friedflix.mediatracker;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
class FamilyMemberAlreadyExistsException extends RuntimeException {
	public FamilyMemberAlreadyExistsException(String familyMemberName) {
		super("user '" + familyMemberName + "' already exists.");
	}
}
