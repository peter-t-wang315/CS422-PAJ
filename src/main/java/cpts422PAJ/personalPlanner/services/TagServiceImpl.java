package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Iterable<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
       return this.tagRepository.findById(id).get();
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

}
