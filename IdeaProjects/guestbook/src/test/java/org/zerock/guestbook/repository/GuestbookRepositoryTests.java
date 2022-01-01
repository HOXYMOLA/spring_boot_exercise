package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entitiy.Guestbook;
import org.zerock.guestbook.entitiy.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

//    @Test
//    public void insertDummies(){
//
//        IntStream.rangeClosed(1,300).forEach(i -> {
//
//            Guestbook guestbook = Guestbook.builder()
//                    .title("Title...." + i)
//                    .content("Content..." + i)
//                    .writer("user" + (i % 10))
//                    .build();
//            System.out.println(guestbookRepository.save(guestbook));
//        });
//    }

//    @Test
//    public void updateTest(){
//
//        Optional<Guestbook> result = guestbookRepository.findById(300L); //존재하는 번호로 테스트
//
//        if(result.isPresent()){
//
//            Guestbook guestbook = result.get();
//
//            guestbook.changeTitle("Changed Title....");
//            guestbook.changeContent("Changed Content...");
//
//            guestbookRepository.save(guestbook);
//        }
//    }

//    @Test
//    public void testQuery1(){
//    //제목(title)에 '1'이라는 글자가 있는 엔티티들을 검색
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
//
//        QGuestbook qGuestbook = QGuestbook.guestbook;
//        //동적으로 처리하기 위해 Q도메인 클래스를 얻어온다.
//
//        String keyword = "1";
//
//        BooleanBuilder builder = new BooleanBuilder();
//        //BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너
//
//        BooleanExpression expression = qGuestbook.title.contains(keyword);
//        //원하는 조건은 필드 값과 같이 결합해서 생성
//
//        builder.and(expression);
//        //만들어진 조건은 where문에 and나 or같은 키워드와 결합
//
//        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
//        //BooleanBuilder는 GuestbookRepository에 추가 된 QuerydslPredicateExcutor 인터페이스의 findAll() 사용 가능
//
//        result.stream().forEach(guestbook -> {
//            System.out.println(guestbook);
//        });
//    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);
        //exTitle과 exContent를 결합

        builder.and(exAll);
        //BooleanBuilder에 추가

        builder.and(qGuestbook.gno.gt(0L));
        //'gno가 0보다 크다' 조건을 추가

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
