package com.uol.states.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.uol.states.model.State;

public interface StateRepository extends JpaRepository<State, Long>{

	Optional<State> findById(Long id);

	List<State> findAllByRegiaoIgnoreCase(String regiao);

	@Query(nativeQuery = true,value="SELECT TOP 1 * FROM State ORDER BY populacao DESC")
	List<State> findByMaiorPopulacao();
	
	@Query(nativeQuery = true,value="SELECT TOP 1 * FROM State ORDER BY area DESC")
	List<State> findByMaiorArea();

	
	

}
