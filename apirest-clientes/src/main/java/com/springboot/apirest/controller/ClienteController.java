package com.springboot.apirest.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import com.springboot.apirest.entity.Cliente;
import com.springboot.apirest.entity.Region;
import com.springboot.apirest.service.ClienteService;

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
	
	/*@PostMapping("/saveClient")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		
		return clienteService.saveClient(cliente);
		
	}*/
	@PostMapping("/saveClient")
	public ResponseEntity<?> show(@RequestBody Cliente cliente){
	Map<String,Object> response = new HashMap<>();
	
	try {
		cliente = clienteService.saveClient(cliente);
	}catch (DataAccessException e){
		response.put("mensaje", "Error al realizar la insercion en base de datos");
		response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	response.put("mensaje", "El cliente ha sido creado con exito");
	response.put("cliente", cliente);
	
	return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
}
	
	/*@PutMapping("/updateClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		
		Cliente clienteUpdate = clienteService.findClientById(id);
		
		clienteUpdate.setNombre(cliente.getNombre());
		clienteUpdate.setApellido(cliente.getApellido());
		clienteUpdate.setEmail(cliente.getEmail());
		clienteUpdate.setTelefono(cliente.getTelefono());
		
		return clienteService.saveClient(clienteUpdate);
		
	}*/
	@PutMapping("/updateClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id){
		Map<String,Object> response = new HashMap<>();
		
		try {
			Cliente clienteUpdate = clienteService.findClientById(id);
			
			clienteUpdate.setNombre(cliente.getNombre());
			clienteUpdate.setApellido(cliente.getApellido());
			clienteUpdate.setEmail(cliente.getEmail());
			clienteUpdate.setTelefono(cliente.getTelefono());
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar la insercion en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente creado con exito");
		response.put("cliente", cliente);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
//	@DeleteMapping("/deleteClient/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	public Cliente delete(@PathVariable Long id) {
//		
//		Cliente clienteDeleted  = clienteService.findClientById(id);
//		
//		clienteService.delete(id);
//		
//		return clienteDeleted;
//		
//	}
	
	@DeleteMapping("/deleteClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String,Object> response = new HashMap<>();
		Cliente clienteDeleted  = new Cliente(); 
		try {
			clienteDeleted  = clienteService.findClientById(id);
			clienteService.delete(id);
		}catch (DataAccessException e){
			response.put("mensaje", "Error al realizar el borrado en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido borrado con exito");
		response.put("cliente", clienteDeleted);
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,@RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		
		Cliente cliente = clienteService.findClientById(id);
		
		if(!archivo.isEmpty()) {
			
			String nombreArchivo = archivo.getOriginalFilename();
			Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
			
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			}catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen del cliente");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			cliente.setImagen(nombreArchivo);
			clienteService.saveClient(cliente);
			
			response.put("cliente", cliente);
			response.put("mensaje", "La imagen ha sido subida con exito");
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/upload/{imagen:.+}")
	public ResponseEntity<Resource> showImage(@PathVariable String nombreImagen){
		
		Path rutaArchivo = Paths.get("uploads").resolve(nombreImagen).toAbsolutePath();
		
		Resource recurso = null;
		
		try {
			
			recurso = new UrlResource(rutaArchivo.toUri());
			
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error, no se pudo recuperar la imagen");
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attechment;filename=\""+recurso.getFilename()+"\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@GetMapping("/regiones")
	public List<Region> listRegions(){
		
		return clienteService.findRegiones();
		
	}
	
	@GetMapping("/emails/{email}")
	public Cliente buscarPorEmail(@PathVariable String email) {
		return clienteService.buscarPorEmail(email);
	}
}
