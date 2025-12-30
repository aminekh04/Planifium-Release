package com.diro.ift2255.repository;

import com.diro.ift2255.model.Opinion;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class OpinionRepository {

    private final List<Opinion> opinions = new CopyOnWriteArrayList<>();

    public void add(Opinion opinion) {
        opinions.add(opinion);
    }

    public List<Opinion> findAll() {
        return opinions;
    }

    public List<Opinion> findByCourseCode(String courseCode) {
        return opinions.stream()
                .filter(o -> o.getCourse_code() != null
                        && o.getCourse_code().equalsIgnoreCase(courseCode))
                .collect(Collectors.toList());
    }
}
