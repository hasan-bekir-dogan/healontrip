package com.healontrip.repository;

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
            "       Where u.role = :role" +
            "       And Case" +
            "               When :genderNullCheck = false Then" +
            "                   Upper(u.gender) in (:gender)" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When :specialistNullCheck = false Then" +
            "                   Upper(u.specialist_id) in (:specialist)" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When :experienceYearNullCheck = false Then" +
            "                   u.id in (Select e.user_id" +
            "                            From experiences e" +
            "                            Group By e.user_id" +
            "                            Having Sum(Case" +
            "                                           When e.to_date is null or e.to_date = '' Then " +
            "                                               date_format(sysdate(), '%Y') - e.from_date" +
            "                                           Else" +
            "                                               e.to_date - e.from_date" +
            "                                       End) Between" +
            "                                                   (Select ey.from_date" +
            "                                                    From experience_years ey" +
            "                                                    Where ey.id = :experienceYearId)" +
            "                                                       And" +
            "                                                   (Select If(ey.to_date = -1, ~0, to_date)" +
            "                                                    From experience_years ey " +
            "                                                    Where ey.id = :experienceYearId))" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When Trim(:search) != '' Then" +
            "                   Upper(u.first_name) like %:search%" +
            "                       Or" +
            "                   Upper(u.last_name) like %:search%" +
            "               Else" +
            "                   True" +
            "           End" +
            "       And Case" +
            "               When Trim(:location) != '' Then" +
            "                   Upper(u.city) like %:location%" +
            "                       Or" +
            "                   Upper(u.country) like %:location%" +
            "               Else" +
            "                   True" +
            "           End" +
            "       order by if(:sortBy = 'name_asc', first_name, 0) asc," +
            "                if(:sortBy = 'name_desc', first_name, 0) desc," +
            "                if(:sortBy = 'rate_desc', (select avg(rating) as rate_avg" +
            "                                           from reviews" +
            "                                           where doctor_id = u.id), 0) desc," +
            "                if(:sortBy = 'review_desc', (select count(*) as review_sum" +
            "                                             from reviews" +
            "                                             where doctor_id = u.id), 0) desc," +
            "                if(:sortBy = 'exp_desc', (select sum(if(to_date = null Or to_date = '', DATE_FORMAT(now(), '%Y'), to_date) - from_date)" +
            "                                          from healontrip.experiences" +
            "                                          where user_id = u.id), 0) desc", nativeQuery = true)
    List<UserEntity> findDoctorsByFilter(@Param("role") String role,
                                         @Param("gender") List<String> gender,
                                         @Param("genderNullCheck") boolean genderNullCheck,
                                         @Param("specialist") List<Long> specialist,
                                         @Param("specialistNullCheck") boolean specialistNullCheck,
                                         @Param("experienceYearId") int experienceYearId,
                                         @Param("experienceYearNullCheck") boolean experienceYearNullCheck,
                                         @Param("search") String search,
                                         @Param("location") String location,
                                         @Param("sortBy") String sortBy);

    @Query(value = "Select *" +
            "       From users" +
            "       Where user_name = :userName", nativeQuery = true)
    UserEntity findByUserName(@Param("userName") String userName);
}