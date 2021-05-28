package com.tw.jobposting.web.rest;

import com.tw.jobposting.domain.JobPost;
import com.tw.jobposting.security.SecurityUtils;
import com.tw.jobposting.service.JobPostService;
import com.tw.jobposting.service.dto.UserApplicationDTO;
import com.tw.jobposting.service.dto.UserDTO;
import com.tw.jobposting.web.rest.errors.BadRequestAlertException;
import com.tw.jobposting.service.dto.JobPostDTO;

import com.tw.jobposting.web.rest.feignClients.DataappService;
import com.tw.jobposting.web.rest.feignClients.SkillappService;
import com.tw.jobposting.web.rest.feignClients.UserAppService;
import com.tw.jobposting.web.rest.models.SkillJobPostDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link JobPost}.
 */
@RestController
@RequestMapping("/api")
public class JobPostResource {

    private final Logger log = LoggerFactory.getLogger(JobPostResource.class);

    private static final String ENTITY_NAME = "jobpostingJobpost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobPostService jobpostService;

    private final DataappService dataappService;

    private final SkillappService skillappService;

    private final UserAppService userAppService;

    public JobPostResource(JobPostService jobpostService, DataappService dataappService, SkillappService skillappService, UserAppService userAppService) {
        this.jobpostService = jobpostService;
        this.dataappService = dataappService;
        this.skillappService = skillappService;
        this.userAppService = userAppService;
    }

    /**
     * {@code POST  /jobposts} : Create a new jobpost.
     *
     * @param jobpostDTO the jobpostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobpostDTO, or with status {@code 400 (Bad Request)} if the jobpost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/jobposts")
    public ResponseEntity<JobPostDTO> createJobpost(@RequestBody JobPostDTO jobpostDTO) throws URISyntaxException {
        log.debug("REST request to save Jobpost : {}", jobpostDTO);
        if (jobpostDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobpost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobPostDTO result = jobpostService.save(jobpostDTO);
        dataappService.deleteSkillJobPost(result.getId());
        for(long skillids:jobpostDTO.getSkillId() ){

            SkillJobPostDTO skillJobPostDTO = new SkillJobPostDTO(skillids, result.getId());
            dataappService.createSkillJobPost(skillJobPostDTO);
        }
        return ResponseEntity.created(new URI("/api/jobposts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jobposts} : Updates an existing jobpost.
     *
     * @param jobpostDTO the jobpostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobpostDTO,
     * or with status {@code 400 (Bad Request)} if the jobpostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobpostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/jobposts")
    public ResponseEntity<JobPostDTO> updateJobpost(@RequestBody JobPostDTO jobpostDTO) throws URISyntaxException {
        log.debug("REST request to update Jobpost : {}", jobpostDTO);
        if (jobpostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobPostDTO result = jobpostService.save(jobpostDTO);
        dataappService.deleteSkillJobPost(result.getId());
        for(long skillids:jobpostDTO.getSkillId() ){

            SkillJobPostDTO skillJobPostDTO = new SkillJobPostDTO(skillids, result.getId());
            dataappService.createSkillJobPost(skillJobPostDTO);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobpostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jobposts} : get all the jobposts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobposts in body.
     */
    @GetMapping("/jobposts")
    public List<JobPostDTO> getAllJobposts() {
        log.debug("REST request to get all Jobposts");
        return jobpostService.findAll();
    }

    /**
     * {@code GET  /jobposts/:id} : get the "id" jobpost.
     *
     * @param id the id of the jobpostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobpostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jobposts/{id}")
    public ResponseEntity<JobPostDTO> getJobpost(@PathVariable Long id) {
        log.debug("REST request to get Jobpost : {}", id);
        Optional<JobPostDTO> jobpostDTO = jobpostService.findOne(id);

        return ResponseUtil.wrapOrNotFound(jobpostDTO);
    }

    /**
     * {@code DELETE  /jobposts/:id} : delete the "id" jobpost.
     *
     * @param id the id of the jobpostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jobposts/{id}")
    public ResponseEntity<Void> deleteJobpost(@PathVariable Long id) {
        log.debug("REST request to delete Jobpost : {}", id);
        jobpostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    /**
     * {@code GET  /job-posts/unappliedforposts} : get the unappliedFor posts.
     *
     @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobPosts in body
     .     */
    @GetMapping("/jobposts/unappliedforposts/")
    public List<JobPostDTO> getCurrentUserUnappliedJobPosts() {
        log.debug("REST request to get all JobPosts for user {}", SecurityUtils.getCurrentUserLogin());

        /** get User **/
        String login = SecurityUtils.getCurrentUserLogin().get();
        UserDTO user = userAppService.getUserByLogin(login).getBody();

        /** get List of user applications **/
        List<UserApplicationDTO> applications = dataappService.getListOfUserApplications(user.getId()).getUserApplications();


        List<JobPostDTO> jobPosts = jobpostService.findAll();


        /** Filter jobs where jobId equals jobid in userApplication **/
        jobPosts = jobPosts.stream()
            .filter(jp -> applications.stream().allMatch(app -> !app.getJobPostId().equals(jp.getId()))).collect(Collectors.toList());
        for(JobPostDTO jp : jobPosts) {
            List<SkillJobPostDTO> skillsJobPostIds =   dataappService.getListOfJobPostSkills(jp.getId());
            for(SkillJobPostDTO sk: skillsJobPostIds) {
                jp.addSkill(skillappService.getSkill(sk.getSkillId()).getBody());
            }
        }
        return jobPosts;
    }
}
