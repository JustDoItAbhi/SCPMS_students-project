package userService.registrations.security;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import userService.registrations.security.dtos.ClientRequestDto;

public interface CustomizeRegeredClientService {// OICD client custom registratiton
    RegisteredClient createRegeretedClient(ClientRequestDto dto);// method to create registered client
    boolean deleteClient(String clientId);

}