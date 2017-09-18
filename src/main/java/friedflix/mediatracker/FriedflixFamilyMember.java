package friedflix.mediatracker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class FriedflixFamilyMember {
	@OneToMany(mappedBy = "familyMember")
    private Set<FriedflixMediaTrackerItem> mediaTrackerItems = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    
    public String name;
    
    public Set<FriedflixMediaTrackerItem> getMediaTrackerItems() {
        return mediaTrackerItems;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FriedflixFamilyMember(String name) {
        this.name = name;
    }

    FriedflixFamilyMember() { // jpa only
    }
}
