package com.intuit.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.intuit.entity.PlayerScore;
import com.intuit.exception.DataBaseStorageException;
import com.intuit.exception.DataRetrivalFail;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;

import com.intuit.repository.PlayerScoreRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PlayerScoreServiceImpl extends PlayerScore {

    @Autowired
    private PlayerScoreRepository scoreRepository;

    @Value("${gamingData.topNoOfPlayer:2}")
    private int numberOfTopPlayers;

    @Value("${gamingData.pageNo}")
    private int pageNo;

    public void addPlayerScore(PlayerScore playerScore) throws DataBaseStorageException {
       try{
            scoreRepository.save(playerScore);
        }
        catch (Exception e) {
            throw new DataBaseStorageException("Failed to add data");
        }

    }

    public List<PlayerScore> getTopPlayerScore () throws DataRetrivalFail {
        try {
            Page<PlayerScore> page = scoreRepository.findAll(PageRequest.of(pageNo, numberOfTopPlayers, Sort.by(Sort.Order.desc("score"))));
            return page.getContent();
        }
        catch(Exception e)
        {
            throw new  DataRetrivalFail("Failed to get the data , Please check the db connection");
        }
    }

    public PlayerScore getPlayerScore(UUID uuid) throws DataRetrivalFail {
        try {
             Optional<PlayerScore> score = scoreRepository.findById(uuid);
             return  score.get() ;

        } catch (Exception e) {
            throw new  DataRetrivalFail("Failed to get the data , Please check the db connection");
        }
    }


    public List<PlayerScore> getAllPlayerScore() throws DataRetrivalFail {
        try {
            return scoreRepository.findAll();
        }
        catch (Exception e) {
            throw new  DataRetrivalFail("Failed to get the data , Please check the db connection");
        }

    }

    public void updateAllPlayerScore( MultipartFile playerScoreFile) throws IOException, DataBaseStorageException {
        try {

            Type playerScoreType = new TypeToken<ArrayList<PlayerScore>>(){}.getType();
            List<PlayerScore> playerScores = new GsonBuilder().create().fromJson(new String(
                    playerScoreFile.getBytes(), StandardCharsets.UTF_8), playerScoreType);

            scoreRepository.saveAll(playerScores);
        }
        catch(JsonSyntaxException e)
        {
            throw new InvalidDataException("Data Provided is not in correct Format please check the data");

        }
        catch (Exception e) {

            throw new DataBaseStorageException("Failed to add data");
        }

    }
}
