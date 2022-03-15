package com.cruiser.quasarfire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cruiser.quasarfire.dto.Satellite;

public interface QuasarFireRepository extends JpaRepository<Satellite, String> {

}
