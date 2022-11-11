package com.trilogyed.musicstorerecommendations.repository;



import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumRecommendation, Integer> {
}
