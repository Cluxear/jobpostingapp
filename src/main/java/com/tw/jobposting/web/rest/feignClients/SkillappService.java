package com.tw.jobposting.web.rest.feignClients;

import com.tw.jobposting.client.AuthorizedFeignClient;
import com.tw.jobposting.service.dto.Skill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "skillapp")
@RequestMapping(value = "/api")
public interface SkillappService {

    @RequestMapping(value = "/skills/{id}", method = RequestMethod.GET)
    ResponseEntity<Skill> getSkill(@RequestParam("id") Long id );
}
