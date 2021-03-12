package io.perf.test.util;

import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OraBatchExecutor {

    private final SqlSessionFactory oraSqlSessionFactory;

    private static SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void init() {
        sqlSessionFactory = oraSqlSessionFactory;
    }

    public static <T> void batch(Class<T> daoClass, Consumer<T> func) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            T dao = sqlSession.getMapper(daoClass);
            func.accept(dao);
            sqlSession.commit();
        } catch (Exception e) {
            log.error("Something went wrong while batching Oracle statements, rolling back", e);
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
    }

}
