package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAll();
    Tag findById(Long id);
    void save(Tag tag);
    void deleteById(Long id);
}
