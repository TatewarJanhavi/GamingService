package com.inuit;

import com.intuit.entity.PlayerScore;
import com.intuit.exception.DataBaseStorageException;
import com.intuit.exception.DataRetrivalFail;
import com.intuit.repository.PlayerScoreRepository;
import com.intuit.service.PlayerScoreServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class PlayerScoreServiceTest {

    @TestConfiguration
    static class PlayerScoreImplTestContextConfiguration {

        @Bean
        public PlayerScoreServiceImpl playerScoreService() {
            return  new PlayerScoreServiceImpl();
        }
    }

    @Autowired
    private PlayerScoreServiceImpl playerScoreService;

    @MockBean
    private PlayerScoreRepository repository;
    @Test
    public void testUpdatePlayerData() throws IOException, DataBaseStorageException {
        MultipartFile multipartFile =
                new MockMultipartFile(
                        "originalFileName", "[{ \"name\": \"ram7\", \"score\": 20 } ]".getBytes(StandardCharsets.UTF_8));
        playerScoreService.updateAllPlayerScore(multipartFile);

    }

    @Test
    public  void testTopPlayerData() throws DataRetrivalFail {
        Page<PlayerScore> playerScore = new PageImpl<PlayerScore>(Arrays.asList(new PlayerScore(UUID.randomUUID() ,"abc" , 30L) , new PlayerScore(UUID.randomUUID() ,"abcd" , 20L)));

        when(repository.findAll(PageRequest.of(0, 5, Sort.by(Sort.Order.desc("score"))))).thenReturn( playerScore);
        List<PlayerScore> exceptedPlayerScore = playerScoreService.getTopPlayerScore();
        Assert.assertEquals(exceptedPlayerScore.get(0).getName(), "abc");


    }

}
