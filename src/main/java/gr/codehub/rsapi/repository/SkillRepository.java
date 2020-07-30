package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    @Query(value = " SELECT * FROM Skill where (title = :title)", nativeQuery = true)
    public Skill findBySkillTitle(@Param("title") String title);

    Optional<Skill> findSkillByTitle(String title);


}