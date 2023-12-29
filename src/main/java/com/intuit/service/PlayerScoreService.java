package com.intuit.service;

import com.intuit.entity.PlayerScore;
import com.intuit.exception.DataBaseStorageException;
import com.intuit.exception.DataRetrivalFail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PlayerScoreService {
     void updateAllPlayerScore( MultipartFile playerScoreFile) throws IOException, DataBaseStorageException ;
     List<PlayerScore> getAllPlayerScore() throws DataRetrivalFail ;
     List<PlayerScore> getTopPlayerScore () throws DataRetrivalFail ;
}
