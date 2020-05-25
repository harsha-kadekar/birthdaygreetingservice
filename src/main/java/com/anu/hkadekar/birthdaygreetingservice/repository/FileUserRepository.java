package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;

public class FileUserRepository {

    private static String userDataFilePath = "FileDB/UserDBFile.json";
    private List<User> users = null;
    private ObjectMapper objectMapper = null;
    private static FileUserRepository instance = null;
    StampedLock fileLock = null;

    private FileUserRepository(){
        users = new ArrayList<>();
        fileLock = new StampedLock();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public synchronized static FileUserRepository getInstance(){
        if(null == instance) {
            instance = new FileUserRepository();
            instance.readDataFromFile();
        }
        return instance;
    }

    private void readDataFromFile(){

        long fileStamp = fileLock.readLock();
        try {
            String absolutePath = FileUserRepository.class.getClassLoader().getResource(userDataFilePath).getPath();
            users = objectMapper.readValue(Paths.get(absolutePath).toFile(), new TypeReference<List<User>>(){});

        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            fileLock.unlockRead(fileStamp);
        }

    }

    public List<User> getAllUsers(){
        return users;
    }


}
