package com.tsystems.tshop.services.impl;

import java.util.List;

import com.tsystems.tshop.components.LoginedUsersStorage;
import org.springframework.stereotype.Service;

import com.tsystems.tshop.domain.Client;
import com.tsystems.tshop.repositories.ClientRepository;
import com.tsystems.tshop.services.ClientService;

import javax.servlet.http.HttpSession;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	
	public ClientServiceImpl(final ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Client getClientById(final Long id) {
		return clientRepository.getClient(id);
	}

	public List<Client> getClients() {
		return clientRepository.getAllUsers();
	}

	public Client getClientByName(String name) {
		return clientRepository.getClientByName(name);
	}

	public Client getUser(){
		return null; //clientRepository.getClientByName(loginedUsersStorage.get(httpSession.getId()).getName());
	}

	@Override
	public Client changeUser(Client client) {
		clientRepository.changeUser(client);

		return clientRepository.getClientByName(client.getName());
	}

	@Override
	public void setRole(Client client) {
		clientRepository.setRole(client);
	}
}
