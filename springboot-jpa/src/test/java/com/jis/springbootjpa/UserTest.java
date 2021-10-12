package com.jis.springbootjpa;

import com.jis.springbootjpa.domain.Gender;
import com.jis.springbootjpa.domain.User;
import com.jis.springbootjpa.domain.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Test
    public void crud(){
        //저장
        //userRepository.save(new User());
        //userRepository.findAll().forEach(System.out::println);
        //System.out.println(">>>>>>>>>>>" + userRepository.findAll());

        //정렬로 받기
        //List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC,"name"));
        //users.forEach(System.out::println);

        List<User> users = userRepository.findAllById(Lists.newArrayList(1l,3l,5l));
        users.forEach(System.out::println);

        User user1 = new User("martin","google");
        User user2 = User.builder().name("test2").email("test2@gmail.com").build();
        userRepository.saveAll(Lists.newArrayList(user1,user2));

        //flush 쿼리에 따로 영향을 주는것이 아니라 DB반영시점을 조절
        userRepository.flush();

        userRepository.findAll().forEach(System.out::println);


    }
    @Test
    @Transactional
    public void read(){
        //L은 Long

        //바로 조회 진행시 no Session 에러 발생 session이 묶이지 않음 -> 따라서 트랜잭션으로 묶어줘야함
        System.out.println(userRepository.getOne(1L));

        //findById는 Optional로 반환 따라서 orElse 설정
        User user = userRepository.findById(1L).orElse(null);
    }

    @Test
    public void delete(){
        //userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));
        // 해당 로직은 각각 select Delete를 수행하기 때문에 부하 발생
        //userRepository.deleteAll(userRepository.findAllById(Lists.newArrayList(1L,3L)));

        //select 한번 delete 한번 수행 위에 사항을 개선
        userRepository.deleteInBatch(userRepository.findAllById(Lists.newArrayList(1L,3L)));
    }

    @Test
    public void paging(){
        //페이징 인덱스는 0부터 시작
        Page<User> users = userRepository.findAll(PageRequest.of(1,3));

        System.out.println("pages :" + users);
        System.out.println("totalElements :" + users.getTotalElements());
        System.out.println("totalpage :" + users.getTotalPages());
        System.out.println("sort :" + users.getSort());
        System.out.println("size :" + users.getSize());

        //users.getContent().forEach(System.out::println);

        //page 설정
        userRepository.findByName("tester",PageRequest.of(0,1,Sort.by(Sort.Order.desc("id"))));
    }

    @Test
    public void exampleMatcher(){

        //matcher where절에 like나 특수 조건 걸떄 사용
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name")
                .withMatcher("email",endsWith());//email LIKE value%

        //검색이 필요한 인자를 example로 추가 해서 검색가능
        Example<User> example = Example.of(new User("ma","fastcampus.com"),matcher);
        userRepository.findAll(example).forEach(System.out::println);

        Example<User> example1 = Example.of(new User("ma","fastcampus.com"));
        userRepository.findAll(example1).forEach(System.out::println);

        //Like '%value%'
        User user = new User();
        user.setEmail("slow");
        ExampleMatcher matcher1 = ExampleMatcher.matching().withMatcher("email",contains());
        Example<User> example2 = Example.of(user,matcher1);
        userRepository.findAll(example2).forEach(System.out::println);

    }

    @Test
    public void read1(){
        //User user = userRepository.findById(1L).orElse(null);
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        //where 조건
        user.setEmail("naver.com");
        userRepository.save(user);
    }

    @Test
    public void select(){
        System.out.println(userRepository.findByName("martin"));

        System.out.println("findFirstByName : " + userRepository.findFirstByName("martin"));
        System.out.println("findTopByName : " + userRepository.findTopByName("martin"));

        System.out.println("findFirst2ByName : " + userRepository.findFirst2ByName("martin"));
        System.out.println("findTop2ByName : " + userRepository.findTop2ByName("martin"));

        System.out.println("findTop2ByName : " + userRepository.findLast1ByName("martin"));


        logger.info("list" + userRepository.findByEmailAndName("test@naver.com","martin"));


        logger.info(""+userRepository.findByIdAfter(4L));
        userRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1l));

        userRepository.findByEmailIsNotNull();

        List<String> names = new ArrayList<String>();
        names.add("test");
        names.add("test1");
        userRepository.findByNameIn(names);
        userRepository.findUserByNameEquals("test");

        userRepository.findTop1ByName("test");
        
    }


    @Test
    public void sortTest(){
        //정렬
        //... sort를 배열형태로 받을 수 있음
        userRepository.findFirstByName("test", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email")));
    
        //method 네이밍을으로 지정해서 사용
        userRepository.findFirstByNameOrderByIdDescEmailAsc("test");
    }

    @Test
    public void enumTest(){
        //User user = userRepository.findById(1L).orElseThrow();//값이 없으면 에러 발생
        User user1 = User.builder().name("test2").email("test2@gmail.com").build();
        userRepository.save(user1);
        userRepository.findAll().forEach(System.out::println);

        User user = userRepository.findById(1L).orElse(null);//없으면 null로 리천
        user.setGender(Gender.FEMALE);
        userRepository.save(user);
//
        userRepository.findAll().forEach(System.out::println);

        logger.info("gender : " + userRepository.findRowRecord().get("gender"));
    }

}
