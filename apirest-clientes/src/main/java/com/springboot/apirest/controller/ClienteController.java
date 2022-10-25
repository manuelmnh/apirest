package com.springboot.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.criteria.internal.expression.ConcatExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.apirest.entity.Cliente;
import com.springboot.apirest.service.ClienteService;

import antlr.StringUtils;

@RestController
@RequestMapping("/api")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/clientes")
	public List<Cliente> index(){
		
		return clienteService.findClients();
		
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Cliente cliente = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			cliente = clienteService.findClientById(id);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta a base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(cliente == null) {
			response.put("mensaje", "El cliente con ID: "+id.toString()+" no existe en base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
	}
	
	/*@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/clientes/{id}")
	public Cliente show(@PathVariable Long id) throws Exception {
		Cliente cliente = new Cliente();
		try {
			Cliente clientId = clienteService.findClientById(id);
			if(clientId.equals("")) {
				throw new Exception("No se encuentra el ID");
			}else {
				cliente = clientId;
			}
		}catch (Exception e) {
			throw e;
		}
		
		return cliente;
		
	}*/
	
	@PostMapping("/saveClient")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		
		return clienteService.saveClient(cliente);
		
	}
	
	@PutMapping("/updateClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		Cliente clienteUpdate = clienteService.findClientById(id);
		
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setTelefono(cliente.getTelefono());
		
		return clienteService.saveClient(clienteUpdate);
		
	}
	
	@DeleteMapping("/deleteClient/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Cliente delete(@PathVariable Long id) {
		
		Cliente clienteDeleted  = clienteService.findClientById(id);
		
		clienteService.delete(id);
		
		return clienteDeleted;
		
	}

}
