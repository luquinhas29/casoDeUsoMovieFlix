package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(nativeQuery = true, value = """
			SELECT * FROM(
			SELECT * FROM TB_MOVIE
			WHERE (:genreIds IS NULL OR TB_MOVIE.GENRE_ID IN :genreIds)
			ORDER BY TB_MOVIE.TITLE
			)AS tb_result
			""",countQuery = """
			SELECT COUNT(*) FROM(
			SELECT * FROM TB_MOVIE
			WHERE (:genreIds IS NULL OR TB_MOVIE.GENRE_ID IN :genreIds)
			)AS tb_result
			""")
	Page<MovieProjection> searchGenreId(List<Long> genreIds, Pageable page);

	@Query(value = "SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj.id IN :movieIds")
	List<Movie> searchMoviesWithGenres(List<Long> movieIds);

}
