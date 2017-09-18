package friedflix.mediatracker;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FriedflixMediaTrackerItem {

	public enum MediaType {
		tv, movie
	}
	
	public enum StreamingService {
		netflix, hulu, amazon, hbo, showtime, amc, cbs, other
	}
	
	@JsonIgnore
	@ManyToOne
    private FriedflixFamilyMember familyMember;
	
	@Id
    @GeneratedValue
    private Long id;
	
	FriedflixMediaTrackerItem() { // jpa only
    }
	
	public String title;
	public MediaType mediaType;
	public StreamingService service;
	public Boolean watched;
	public String notes;
	public int seasonNumber;
	public int tmdbId;

	public FriedflixMediaTrackerItem(FriedflixFamilyMember familyMember, String title, MediaType mediaType, StreamingService service, Boolean watched,
			String notes, int seasonNumber, int tmdbId) {
		this.familyMember = familyMember;
		this.title = title;
		this.mediaType = mediaType;
		this.service = service;
		this.watched = watched;
		this.notes = notes;
		this.seasonNumber = seasonNumber;
		this.tmdbId = tmdbId;
	}
	
	public Long getId() {
        return id;
    }
	
	public FriedflixFamilyMember getFamilyMember() {
		return familyMember;
	}
	
	public String getTitle() {
		return title;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public StreamingService getService() {
		return service;
	}

	public Boolean getWatched() {
		return watched;
	}

	public String getNotes() {
		return notes;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}
	
	public int getTmdbId() {
		return tmdbId;
	}
	
}
