package com.cruiser.quasarfire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cruiser.quasarfire.dto.QuasarFireRequest;
import com.cruiser.quasarfire.dto.QuasarFireResponse;
import com.cruiser.quasarfire.dto.Satellite;
import com.cruiser.quasarfire.exception.QuasarFireException;
import com.cruiser.quasarfire.repository.QuasarFireRepository;
import com.cruiser.quasarfire.service.TrilaterationService;

@RestController
public class QuasarFireController {
	
	@Autowired
	QuasarFireRepository repository;
	
	@Autowired
	TrilaterationService service;

	@PostMapping("/topsecret")
	QuasarFireResponse postTopSecret(@RequestBody QuasarFireRequest request) {
		List<Satellite> satellites = service.sortAndValidateSatellites(request.getSatellites());
		
		double[] distances = new double[satellites.size()];
		String[] messages[] = new String[satellites.size()][];
		
		for(int i=0; i < satellites.size(); i++) {
			distances[i] = satellites.get(i).getDistance();
			messages[i] = satellites.get(i).getMessage();
		}
		
		QuasarFireResponse response = new QuasarFireResponse();
		response.setPosition(service.getLocation(distances));
		response.setMessage(service.getMessage(messages));

		
		return response;
	}
	
	@PostMapping("/topsecret_split/{satellite_name}")
	Satellite postSatellite(@RequestBody Satellite newSatellite, @PathVariable String satellite_name) {
		return repository.findById(satellite_name)
				.map(satellite -> {
						satellite.setDistance(newSatellite.getDistance());
						satellite.setMessage(newSatellite.getMessage());
						return repository.save(satellite);
					})
				.orElseGet(() -> {
					newSatellite.setName(satellite_name);
					return repository.save(newSatellite);
				});
	}
	
	
	@GetMapping("/topsecret_split/{satellite_name}")
	Satellite getSatellite(@PathVariable String satellite_name) {
		return repository.findById(satellite_name)
				.orElseThrow(() -> new QuasarFireException());
	}
	
	@DeleteMapping("/topsecret_split/{satellite_name}")
	void deleteSatellite(@PathVariable String satellite_name) {
		repository.deleteById(satellite_name);
	}
	
	@GetMapping("/topsecret_split")
	QuasarFireResponse postTopSecret() {
		List<Satellite> satellites = service.sortAndValidateSatellites(repository.findAll());
		
		double[] distances = new double[satellites.size()];
		String[] messages[] = new String[satellites.size()][];
		
		for(int i=0; i < satellites.size(); i++) {
			distances[i] = satellites.get(i).getDistance();
			messages[i] = satellites.get(i).getMessage();
		}
		
		QuasarFireResponse response = new QuasarFireResponse();
		response.setPosition(service.getLocation(distances));
		response.setMessage(service.getMessage(messages));
		
		return response;
	}
}
