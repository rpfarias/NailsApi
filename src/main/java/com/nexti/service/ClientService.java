package com.nexti.service;

import com.nexti.domain.model.Client;
import com.nexti.domain.request.ClientCreateRequest;
import com.nexti.domain.request.ClientUpdateRequest;
import com.nexti.mapper.ClientMapper;
import com.nexti.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    public List<Client> findAll() {
        log.info("Trying to get all clients.");

        return repository.findAll();
    }

    public Client findById(Long id) {
        log.info(String.format("Trying to get a client by id { %s }.", id));

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Client { %s } not found.", id)));
    }

    public Client create(ClientCreateRequest request) {
        log.info("Trying to create a client.");

        checkCpf(request.getCpf());

        var client = new Client();
        mapper.merge(request, client);
        client.setCpf(client.getCpf().replaceAll("[^0-9]*", ""));
        return repository.saveAndFlush(client);
    }

    public Client update(Long id, ClientUpdateRequest request) {
        log.info(String.format("Trying to update a client by id { %s }.", id));

        var client = findById(id);
        mapper.merge(request, client);
        client.setCpf(client.getCpf().replaceAll("[^0-9]*", ""));

        if (!client.getCpf().equals(request.getCpf()))
            checkCpf(request.getCpf());

        return repository.saveAndFlush(client);
    }

    public void delete(Long id) {
        log.info(String.format("Trying to delete a client by id { %s }.", id));

        var client = findById(id);
        repository.deleteById(client.getId());
    }

    private void checkCpf(String cpf) {
        if (repository.findByCpf(cpf.replaceAll("[^0-9]*", "")).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("There is already a registered client with the informed cpf { %s }.", cpf));
    }
}
