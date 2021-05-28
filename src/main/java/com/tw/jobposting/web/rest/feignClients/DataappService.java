package com.tw.jobposting.web.rest.feignClients;


import com.tw.jobposting.client.AuthorizedFeignClient;
import com.tw.jobposting.service.dto.UserApplication;
import com.tw.jobposting.web.rest.models.SkillJobPostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AuthorizedFeignClient(name = "dataapp")
@RequestMapping(value = "/api")
public interface DataappService {

    @RequestMapping (value = "/skill-job-posts/jobpost/{jp_id}", method = RequestMethod.DELETE)
    ResponseEntity<Void>deleteSkillJobPost(@RequestParam("jp_id") Long jp_id);

    @RequestMapping (value = "/skill-job-posts", method = RequestMethod.POST)
    ResponseEntity<SkillJobPostDTO>createSkillJobPost(@RequestBody SkillJobPostDTO skillJobPostDTO);

    @RequestMapping (value = "/skill-job-posts/jobpost/{jp_id}", method = RequestMethod.GET)
    List<SkillJobPostDTO> getListOfJobPostSkills(@RequestParam("jp_id") Long jp_id);

    @RequestMapping (value = "/user-applications/userId/{userId}", method = RequestMethod.GET)
    UserApplication getListOfUserApplications(@RequestParam("userId") String userId);
}
