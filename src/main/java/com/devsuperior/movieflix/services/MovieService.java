package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;



@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	
	public Page<MovieCardDTO> findPaged(Pageable pageable){
		Page<Movie> list = repository.searchAll(pageable);
		return list.map(x -> new MovieCardDTO(x));
	}
	

}
