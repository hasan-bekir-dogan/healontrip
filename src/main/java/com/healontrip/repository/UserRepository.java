package com.healontrip.repository;

import com.healontrip.dto.ExperienceYears;
import com.healontrip.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    @Query(value = "Select *" +
            "       From users" +
            "       Where role = :role", nativeQuery = true)
    List<UserEntity> findUsersByRole(@Param("role") String role);

    @Query(value = "Select distinct u.*" +
            "       From users u" +
            "       Right Join specialist s on u.id = s.user_id" +
            "       Where u.role = :role" +
            "       And Case" +
            "               When :genderNullCheck = false Then" +
            "                   Upper(u.gender) in (:gender)" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When :specialistNullCheck = false Then" +
            "                   Upper(s.name) in (:specialist)" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When :experienceYearNullCheck = false Then" +
            "                   u.id in (Select e.user_id" +
            "                         From experiences e" +
            "                         Group By e.user_id" +
            "                         Having Sum(Case" +
            "                                       When e.to_date is null or e.to_date = '' Then " +
            "                                           date_format(sysdate(), '%Y') - e.from_date" +
            "                                       Else" +
            "                                           e.to_date - e.from_date" +
            "                                    End) Between" +
            "                                               (Select ey.from_date" +
            "                                                From experience_years ey" +
            "                                                Where ey.id = :experienceYearId)" +
            "                                               And" +
            "                                               (Select If(ey.to_date = -1, ~0, to_date)" +
            "                                                From experience_years ey " +
            "                                                Where ey.id = :experienceYearId))" +
            "               Else" +
            "                   True" +
            "           End" +
            "       ", nativeQuery = true)
    List<UserEntity> findDoctorsByFilter(@Param("role") String role,
                                         @Param("gender") List<String> gender,
                                         @Param("genderNullCheck") boolean genderNullCheck,
                                         @Param("specialist") List<String> specialist,
                                         @Param("specialistNullCheck") boolean specialistNullCheck,
                                         @Param("experienceYearId") int experienceYearId,
                                         @Param("experienceYearNullCheck") boolean experienceYearNullCheck);
}