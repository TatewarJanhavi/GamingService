package com.intuit.controller;

import com.intuit.entity.PlayerScore;
import com.intuit.exception.DataBaseStorageException;
import com.intuit.exception.DataRetrivalFail;
import com.intuit.service.PlayerScoreServiceImpl;
import com.sun.media.sound.InvalidDataException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;


@RestController
public class GamingController {

    Logger logger = LoggerFactory.getLogger(GamingController.class);

    @Autowired
    private PlayerScoreServiceImpl playerScoreServiceImpl;

    @GetMapping("/v1/getPlayerScore/{playerId}")
    public ResponseEntity<PlayerScore> getPlayerScore(@PathVariable UUID playerId)  {
        try {
            logger.info("Fetching data for player id {} ", playerId);
            return ResponseEntity.ok().body(playerScoreServiceImpl.getPlayerScore(playerId));
        }
        catch(DataRetrivalFail  e)
        {
            logger.error("Failed to get the data player  id {}" , playerId);
        }

        return  ResponseEntity.internalServerError().body(new PlayerScore());
    }

    @GetMapping("/v1/getAllPlayerScore")
    public ResponseEntity<List<PlayerScore>> getAllPlayerScore() {
        try {
            logger.info("Fetching data for all player ");
            return  ResponseEntity.ok().body(playerScoreServiceImpl.getAllPlayerScore());

        }
        catch(DataRetrivalFail  e)
        {
            logger.error("Failed to get the data all player");
        }
        return  ResponseEntity.internalServerError().body(new ArrayList<PlayerScore>());
    }


    @PostMapping("/v1/addPlayerScore")
    public ResponseEntity<String> addPlayerScore(@RequestBody PlayerScore playerScore)  {
        try{
            logger.info("Adding Player score with name {} " , playerScore.getName());
            playerScoreServiceImpl.addPlayerScore(playerScore);
            return ResponseEntity.ok().body("Player Score Updated Successfully")  ;
        }
        catch(DataBaseStorageException  e)
        {
            logger.error("Failed to add the data for player {}" , playerScore.getName());
        }
        return  ResponseEntity.internalServerError().body("Failed to Add the data");
    }

    @GetMapping("/v1/getTopPlayerScore")
    public ResponseEntity<List<PlayerScore>> getTopPlayerScore()  {
        try {
            logger.info("Fetching Top Player data ");
            return ResponseEntity.ok().body(playerScoreServiceImpl.getTopPlayerScore());
        }
        catch(DataRetrivalFail  e)
        {
            logger.error("Failed to get the data for top player");
            return  ResponseEntity.internalServerError().body(new ArrayList<PlayerScore>());
        }

    }

    @PostMapping("/v1/updateAllPlayerScore")
    public ResponseEntity<String>  updateAllPlayerScore(@RequestBody MultipartFile playerScoreFile) throws IOException {
        try {
            playerScoreServiceImpl.updateAllPlayerScore(playerScoreFile);
            return ResponseEntity.ok().body("Player's Score Updated Successfully");
        }
        catch(InvalidDataException e)
        {
            logger.error("Data is in Invalid Format " );
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }
        catch(DataBaseStorageException  e)
        {
            logger.error("Failed to add the data for player " );
            return  ResponseEntity.internalServerError().body(e.getMessage());
        }



    }



}
