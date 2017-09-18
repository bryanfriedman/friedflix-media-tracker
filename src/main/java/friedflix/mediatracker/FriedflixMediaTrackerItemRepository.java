package friedflix.mediatracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FriedflixMediaTrackerItemRepository extends JpaRepository<FriedflixMediaTrackerItem, Long> {
    Collection<FriedflixMediaTrackerItem> findByFamilyMemberName(String name);
}
