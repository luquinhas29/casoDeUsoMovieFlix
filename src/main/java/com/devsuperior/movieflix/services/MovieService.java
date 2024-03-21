package com.devsuperior.movieflix.services;

import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.util.Utils;

@Service
public class MovieService {
	
	@Autowired
	private MovieRepository repository;
	

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findAllPaged(String genreId, Pageable pageable) {
		
		List<Long> genreIds = Arrays.asList();
		if(!"0".equals(genreId)) {
			genreIds = Arrays.asList(genreId.split(",")).stream().map(Long::parseLong).toList();
		}
		
		Page<MovieProjection> page = repository.searchGenreId(genreIds, pageable);
		List<Long> movieIds = page.map(x -> x.getId()).toList();
		
		List<Movie> entities = repository.searchMoviesWithGenres(movieIds);
		entities = (List<Movie>)Utils.replace(page.getContent(), entities);
		
		List<MovieCardDTO> dtos = entities.stream().map(x -> new MovieCardDTO(x)).toList();
		Page<MovieCardDTO> pageDto = new PageImpl<>(dtos, page.getPageable(), page.getTotalElements());
		return pageDto;
	}
	
	
	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new MovieDetailsDTO(entity);
	}
	
}
