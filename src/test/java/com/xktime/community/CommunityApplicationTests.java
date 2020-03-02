package com.xktime.community;

import com.xktime.community.model.entity.Article;
import com.xktime.community.repository.ArticleRepository;
import com.xktime.community.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    UserRepository userRepository;
    @Test
    void contextLoads() {
        System.out.println(articleRepository.getCountByAccountId("50693205"));
    }

}
