package com.abderrahmane.elearning.socialservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abderrahmane.elearning.common.converters.MapGroupConverter;
import com.abderrahmane.elearning.common.models.Account;
import com.abderrahmane.elearning.common.models.Group;
import com.abderrahmane.elearning.common.repositories.GroupDAO;
import com.abderrahmane.elearning.common.utils.ErrorMessageResolver;
import com.abderrahmane.elearning.socialservice.validators.CreateGroupValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/${api.version}/teacher/groups")
public class GroupsController {
    @Autowired
    private MapGroupConverter groupConverter;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private ErrorMessageResolver resolver;

    @Autowired
    private CreateGroupValidator createGroupValidator;

    @GetMapping
    public Map<String, Object> getTeacherGroups(
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit, 
            @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
            @RequestAttribute("account") Account account) {

        if (limit <= 0) limit = 10;
        if (offset < 0) offset = 0;

        Map<String, Object> response = new HashMap<>();
        List<Group> groups = groupDAO.getTeacherGroups(account.getId(), limit, offset);

        response.put("data", groupConverter.convertList(groups));
        response.put("success", true);
        
        return response;
    }

    @PostMapping
    public Map<String, Object> createTeacherGroup (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();
        MapBindingResult errors = new MapBindingResult(data, "group");

        createGroupValidator.validate(data, errors);

        if (errors.hasErrors()) return this.resolver.constructErrorResponse(errors);

        try {
            Map<String, Object> responseData = new HashMap<>();
            Group group = groupDAO.insertGroup(account.getTeacherProfile(), (String)data.get("label"), (String)data.get("description"));

            responseData.put("id", group.getId());
            response.put("data", responseData);
            response.put("success", true);
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
            response.put("success", false);
        }

        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateTeacherGroup (@RequestAttribute("account") Account account, @RequestBody Map<String, Object> data, @PathVariable(name = "id") String groupId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean updated = this.groupDAO.updateGroup(groupId, account.getId(), (String)data.get("label"), (String)data.get("description"));
            response.put("success", updated);
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
            response.put("success", false);
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteTeacherGroup (@RequestAttribute("account") Account account, @PathVariable(name = "id") String groupId) {
        Map<String, Object> response = new HashMap<>();

        try {
            boolean deleted = this.groupDAO.deleteGroup(groupId, account.getId());
            response.put("success", deleted);
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
            response.put("success", false);
        }

        return response;
    }
}
