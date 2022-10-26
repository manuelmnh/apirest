package com.springboot.apirest.service;

import java.util.List;

import com.springboot.apirest.entity.Cliente;
import com.springboot.apirest.entity.Region;

public interface ClienteService{

	public List<Cliente> findClients();
	
	public Cliente findClientById(Long id);
	
	public Cliente saveClient(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Region> findRegiones();
	
	public Cliente buscarPorEmail(String email);
}
