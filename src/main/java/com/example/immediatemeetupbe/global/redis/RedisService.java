package com.example.immediatemeetupbe.global.redis;

import com.example.immediatemeetupbe.domain.meeting.entity.Meeting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Meeting> inviteRedisTemplate;
    private final RedisTemplate<String, Object> verifiedRedisTemplate;

    public void addMeetingToList(String key, Meeting data) {
        ListOperations<String, Meeting> listOperations = inviteRedisTemplate.opsForList();
        listOperations.rightPush(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public void setValues(String key, String data) {
        ValueOperations<String, Object> values = verifiedRedisTemplate.opsForValue();
        values.set(key, data);
    }

    @Transactional(readOnly = true)
    public String getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return (String) values.get(key);
    }

    @Transactional(readOnly = true)
    public List<Meeting> getMeetingValues(String key) {
        ListOperations<String, Meeting> listOperations = inviteRedisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }

    @Transactional(readOnly = true)
    public String getCertifiedValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return "false";
        }
        return (String) values.get(key);
    }

    public void deleteMeetingValue(String key) {
        inviteRedisTemplate.delete(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

   public void deleteCertifiedValue(String key) {
        verifiedRedisTemplate.delete(key);
   }

    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }
}
