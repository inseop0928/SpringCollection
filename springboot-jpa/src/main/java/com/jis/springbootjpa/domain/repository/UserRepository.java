package com.jis.springbootjpa.domain.repository;

import com.jis.springbootjpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


//JpaRepository(param1 : entity Type, param2 : pk타입 여기서는 Long)
public interface UserRepository extends JpaRepository<User,Long> {

    //jpa query-method를 참고하면 다양한 메소드가 존재
    //exist..By, count..By, delete..By remove..By도 존재

    Set<User> findByName(String name);

    //getByEmail, readByEmail, queryByEmail, searchByEmail,streamByEmail,findUserByEmail 모두 다 동일
    User findByEmail(String mail);

    List<User> findFirstByName(String name);
    List<User> findTopByName(String name);

    List<User> findFirst2ByName(String name);
    List<User> findTop2ByName(String name);

    Set<User> findUserByNameIs(String name);
    Set<User> findUserByName(String name);
    Set<User> findUserByNameEquals(String name);

    //그냥 엔티티로 반환하면 오류 발생 list 타입으로 변경 필요
    //없는 메소드는 무시하고 실행한다 ->findByName로 실행
    List<User> findLast1ByName(String name);

    //and
    List<User> findByEmailAndName(String email, String name);

    //or
    List<User> findByEmailOrName(String email, String name);

    List<User> findByIdAfter(Long id);

    List<User> findByCreatedAtGreaterThanEqual(LocalDateTime time);

    List<User> findByEmailIsNotNull();

    List<User> findByNameIn(List<String> names);

    List<User> findTop1ByName(String name);
    //정렬 1 name으로 적용 방식
    List<User> findFirstByNameOrderByIdDescEmailAsc(String name);
    
    //정렬 2 Sort 사용
    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable pageable);
    
}
