package com.tw.jobposting.web.rest.feignClients;

import com.tw.jobposting.client.AuthorizedFeignClient;
import com.tw.jobposting.service.dto.Skill;
import com.tw.jobposting.service.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "userapp")
@RequestMapping(value = "/api")
public interface UserAppService {


    @RequestMapping(value = "/users/{login}", method = RequestMethod.GET)
    ResponseEntity<UserDTO> getUserByLogin(@RequestParam("login") String login );
}
