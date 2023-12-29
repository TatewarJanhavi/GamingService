package com.inuit;

import com.intuit.GamingApp;
import com.intuit.entity.PlayerScore;
import com.intuit.repository.PlayerScoreRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.JsonPathAssertions;

import java.util.UUID;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=GamingApp.class)
@DataJpaTest
public class PlayerScoreRepoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PlayerScoreRepository playerRepository;


    @Test
    public void whenFindByPlayerScore() {
        PlayerScore playerScore = new PlayerScore(UUID.randomUUID(),"alex" , 10L);
        entityManager.merge(playerScore);
        entityManager.flush();

        // when
        Page<PlayerScore> found = playerRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Order.desc("score"))));

        // then

        Assert.assertEquals(found.getContent().get(0).getScore(), playerScore.getScore());
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void whenFindByPlayerScoreWithInvalidName() {
        PlayerScore playerScore = new PlayerScore(UUID.randomUUID(),"a" , 10L);
        entityManager.merge(playerScore);
        entityManager.flush();

        Page<PlayerScore> found = playerRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Order.desc("score"))));

    }


}
