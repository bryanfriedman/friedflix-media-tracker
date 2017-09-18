package friedflix.mediatracker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriedflixFamilyMemberRepository extends JpaRepository<FriedflixFamilyMember, Long> {
    Optional<FriedflixFamilyMember> findByName(String name);
}
