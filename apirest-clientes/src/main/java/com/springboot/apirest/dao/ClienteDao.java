package com.springboot.apirest.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.apirest.entity.Cliente;
import com.springboot.apirest.entity.Region;

@Repository
public interface ClienteDao extends CrudRepository<Cliente, Long> {

	@Query("from Region")
	public List<Region> findAllRegiones();
	
	public Cliente findByEmail(String email);
}
