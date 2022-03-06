package com.uol.states.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.uol.states.model.State;
import com.uol.states.repository.StateRepository;



@RestController
@RequestMapping("/api/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@GetMapping("/{id}")
	public State findById(@PathVariable("id") Long id) {
		Optional<State> stateFind = this.stateRepository.findById(id);

		if (stateFind.isPresent()) {
			return stateFind.get();
		}

		return null;
	}

	@GetMapping()
	public List<State> findAll() {
		List<State> states = this.stateRepository.findAll();
		return states;
	}

	@GetMapping("/findByRegiao/{regiao}")
	public List<State> findByRegiao(@PathVariable("regiao") String regiao) {
		List<State> states = this.stateRepository.findAllByRegiaoIgnoreCase(regiao);
		return states;
	}

	@GetMapping("/findByMaiorPopulacao")
	public List<State> findByMaiorPopulacao() {
		List<State> states = this.stateRepository.findByMaiorPopulacao();
		return states;
	}

	@GetMapping("/findByMaiorArea")
	public List<State> findByMaiorArea() {
		List<State> states = this.stateRepository.findByMaiorArea();
		return states;
	}

	@PostMapping
	public ResponseEntity<State> saveState(@RequestBody @Valid State state, UriComponentsBuilder uriBuilder) {
		this.stateRepository.save(state);
		URI uri = uriBuilder.path("/api/states/{id}").buildAndExpand(state.getId()).toUri();
		return ResponseEntity.created(uri).body(state);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<State> updateState(@PathVariable Long id, @RequestBody @Valid State stateNew) {
		Optional<State> optional = this.stateRepository.findById(id);
		if (optional.isPresent()) {
			State state = optional.get();

			state.setNome(stateNew.getNome());
			state.setRegiao(stateNew.getRegiao());
			state.setCapital(stateNew.getCapital());
			state.setArea(stateNew.getArea());
			state.setPopulacao(stateNew.getPopulacao());

			return ResponseEntity.ok(state);
		}

		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<State> optional = this.stateRepository.findById(id);
		if (optional.isPresent()) {
			this.stateRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	

}
