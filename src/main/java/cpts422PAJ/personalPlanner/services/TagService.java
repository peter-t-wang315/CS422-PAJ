package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;

import java.util.List;

public interface TagService {
    Iterable<Tag> findAll();
    Tag findById(Long id);
    Tag save(Tag tag);
}
