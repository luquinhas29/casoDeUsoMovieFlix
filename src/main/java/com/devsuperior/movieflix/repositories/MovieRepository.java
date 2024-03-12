package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(value = "SELECT obj FROM Movie obj JOIN FETCH obj.genre "
			+ "ORDER BY obj.title",
			countQuery = "SELECT COUNT(obj) FROM Movie obj JOIN obj.genre"
					+ " ORDER BY obj.title")
	Page<Movie> searchAll(Pageable pageable);

	/*
	 * @Query("SELECT obj FROM Product obj JOIN FETCH obj.categories " +
	 * "WHERE obj.id IN :productIds ORDER BY obj.name") List<Product>
	 * searchProductsWithCategories(List<Long> productIds);
	 */

}
