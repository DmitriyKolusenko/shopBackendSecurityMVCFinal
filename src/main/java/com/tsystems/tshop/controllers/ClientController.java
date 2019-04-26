package com.tsystems.tshop.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tsystems.tshop.domain.Client;
import com.tsystems.tshop.services.ClientService;

@Controller
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(final ClientService clientService) {
		this.clientService = clientService;
	}

//	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
//	@ResponseBody
//	public Client getClient(@PathVariable Long id) {
//		return clientService.getClientById(id);
//	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Client> getClients() {
		return clientService.getClients();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user")
	@ResponseBody
	public Client getUser() {
		return clientService.getClientByName(getUsername());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/changeuser")
	@ResponseBody
	public Client changeUser(@RequestBody Client client) {
		client.setName(getUsername());
		return clientService.changeUser(client);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/setrole")
	@ResponseBody
	public List<Client> setRole(@RequestBody Client client){
		clientService.setRole(client);
		return clientService.getClients();
	}
	private String getUsername(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return user.getUsername();
	}
}
