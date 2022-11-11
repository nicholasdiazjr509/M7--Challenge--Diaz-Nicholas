package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelRespository labelRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels() {
        List<Label> allLabels = labelRepo.findAll();
        if(allLabels.isEmpty()) {
            throw new IllegalArgumentException("No lables were found!");
        }else{
            return allLabels;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateLabel(@RequestBody @Valid Label label) {
        if (label == null || label.getId() < 1) {
            throw new IllegalArgumentException("Label does not exist!");
        } else {
            labelRepo.save(label);
        }
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable("id") Integer labelId){
        Optional<Label> label = labelRepo.findById(labelId);
        if(!label.isPresent()){
            throw new IllegalArgumentException("Label not found!");
        }else{
            return (label.get());
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label addNewLabel(@RequestBody Label label){
        return labelRepo.save(label);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelById(@PathVariable("id")Integer labelId){
        labelRepo.deleteById(labelId);
    }
}
