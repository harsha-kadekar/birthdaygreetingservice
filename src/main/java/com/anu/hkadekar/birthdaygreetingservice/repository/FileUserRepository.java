package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUserRepository implements UserRepository {

    private Logger log = LoggerFactory.getLogger(FileUserRepository.class);

    private static String userDataFilePath = "FileDB/UserDBFile.json";
    private CopyOnWriteArrayList<User> users = null;
    private ObjectMapper objectMapper = null;
    private static FileUserRepository instance = null;
    StampedLock fileLock = null;
    ReentrantLock lock = null;

    private FileUserRepository(){
        users = new CopyOnWriteArrayList<>();
        fileLock = new StampedLock();
        lock = new ReentrantLock();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        readDataFromFile();
    }

    public synchronized static FileUserRepository getInstance(){
        if(null == instance) {
            instance = new FileUserRepository();
        }
        return instance;
    }

    private void readDataFromFile(){
        long fileStamp = fileLock.readLock();
        try {
            String absolutePath = FileUserRepository.class.getClassLoader().getResource(userDataFilePath).getPath();
            List<User> userList = objectMapper.readValue(Paths.get(absolutePath).toFile(), new TypeReference<List<User>>(){});
            users.clear();
            users.addAll(userList);
        } catch (Exception exp) {
            log.error("Error while reading from User Data Source File", exp);
        } finally {
            fileLock.unlockRead(fileStamp);
        }

    }

    private List<User> getArrayListOfUsers() {
        Iterator<User> userIterable = users.iterator();
        List<User> allUsers = new ArrayList<>();
        while(userIterable.hasNext()){
            allUsers.add(userIterable.next());
        }
        return allUsers;
    }

    private void writeDataToFile(){
        long fileStamp = fileLock.writeLock();
        try {
            String absolutePath = FileUserRepository.class.getClassLoader().getResource(userDataFilePath).getPath();
            List<User> userList = getArrayListOfUsers();
            objectMapper.writeValue(Paths.get(absolutePath).toFile(), userList);
        }catch (Exception exp) {
            log.error("Error while writing into User Data Source File", exp);
        } finally {
            fileLock.unlockWrite(fileStamp);
        }
    }

    private void flushData(){
        writeDataToFile();
        readDataFromFile();

    }

    @Override
    public List<User> getAllUsers(){
        return getArrayListOfUsers();
    }

    @Override
    public List<User> getUsersByEmail(List<String> emails) {
        return users.stream().filter(item -> emails.contains(item.getEmail())).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersById(List<UUID> uuids) {
        return users.stream().filter(item -> uuids.contains(item.getUserId())).collect(Collectors.toList());
    }

    @Override
    public void addUsers(List<User> userList) {
        users.addAllAbsent(userList);
        flushData();
    }

    @Override
    public void removeUsers(List<User> users) {

    }

    @Override
    public void updateUsers(List<User> users) {

    }


}
