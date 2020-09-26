/**
 * 
 */

package com.api.enrollee.controller;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.enrollee.exception.ValidationException;
import com.api.enrollee.model.Enrollee;
import com.api.enrollee.request.model.DependentRequest;
import com.api.enrollee.request.model.EnrolleeRequest;
import com.api.enrollee.service.EnrolleeService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/api")
@Slf4j
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;

	/**
	 * This method holds the logic to fetch the enrollee based on given enrollId.
	 * @param id Holds the enrolleeId.
	 * @return enrollee object.
	 */
	@GetMapping(value = "/enrollees/{id}")
	@ApiOperation(value = "GET enrollee", response = Enrollee.class)
	public Enrollee one(@NotNull @PathVariable Long id) throws Exception{
		log.info(" enrollee Id ", id);
		return enrolleeService.getEnrollee(id);
	}

	/**
	 * This method holds the logic to add the enrollee.
	 * @param enrolleeRequest Holds the enrollee details.
	 * @return enrollee object.
	 */
	@PostMapping(value = "/enrollees")
	@ApiOperation(value = "ADD enrollee", response = Enrollee.class)
	public Enrollee enrollees(@Valid @RequestBody EnrolleeRequest enrolleeRequest) {
		log.info(" Add enrollee Name ", enrolleeRequest.getName());
		return enrolleeService.registerEnrollee(enrolleeRequest);
	}

	/**
	 * This method holds the logic to edit the enrollee based on given enrollId.
	 * @param id holds the enrolleeId.
	 * @param enrolleeRequest holds the modified enrollee details.
	 * @return holds the updated enrollee object.
	 */
	@PutMapping(value = "/enrollees/{id}")
	@ApiOperation(value = "EDIT enrollee", response = Enrollee.class)
	public Enrollee enrollees(@NotNull @PathVariable Long id, @Valid @RequestBody EnrolleeRequest enrolleeRequest) {
		
		log.info(" Edit enrollee Id ", id);
		return enrolleeService.editEnrollee(id, enrolleeRequest);
	}

	/**
	 * This method holds the logic to delete the enrollee based on given enrollId.
	 * @param id Holds the enrolleeId.
	 * @return enrollee object.
	 */
	@DeleteMapping(value = "/enrollees/{id}")
	@ApiOperation(value = "DELETE enrollee", response = ResponseEntity.class)
	public ResponseEntity<Boolean> enrollees(@NotNull @PathVariable Long id) {
		
		log.info(" Delete enrollee Id ", id);
		Boolean hasDeleted = enrolleeService.deleteEnrollee(id);
		return ResponseEntity.ok(hasDeleted);
	}

	/**
	 * This method holds the logic to add the dependents of an enrollee based on given enrollId.
	 * @param dependentRequest holds the enrollee dependents details.
	 * @return newly added enrollee object.
	 */
	@PostMapping(value = "/dependents")
	@ApiOperation(value = "ADD DEPENDENT", response = Enrollee.class)
	public Enrollee dependents(@Valid @RequestBody DependentRequest dependentRequest) {
		
		log.info(" Add Dependents enrollee Id ", dependentRequest.getEnrolleeId());
		return enrolleeService.addDependent(dependentRequest);
	}

	/**
	 * This method holds the logic to edit dependent based on given dependentId.
	 * @param dependentRequest holds the enrollee dependents details.
	 * @return updated dependent object.
	 */
	@PutMapping(value = "/dependents/{id}")
	@ApiOperation(value = "EDIT DEPENDENT", response = Enrollee.class)
	public Enrollee dependents(@NotNull @PathVariable Long id, 
			@Valid @RequestBody DependentRequest dependentRequest) {
		
		log.info(" Edit Dependents Id ", dependentRequest.getDependentId());
		
		if (Objects.isNull(dependentRequest.getDependentId())) {
			log.info("Dependent Id cannot be null");
			throw new ValidationException("Dependent Id cannot be null");
		}
		
		return enrolleeService.editDependent(dependentRequest);
	}
	
	/**
	 * This method holds the logic to delete dependent based on given dependentId.
	 * @param enrolleeId Holds the enrollees Id.
	 * @param dependentId Holds the dependents Id.
	 * @return delete status.
	 */
	@DeleteMapping(value = "/dependents/enrolleeId/{enrolleeId}/dependentId/{dependentId}")
	@ApiOperation(value = "DELETE DEPENDENT", response = ResponseEntity.class)
	public ResponseEntity<Boolean> dependents(@NotNull @PathVariable Long enrolleeId, 
			@NotNull @PathVariable Long dependentId) {
		log.info(" Delete : enrollee Id " + enrolleeId + " Dependents Id : ", dependentId);
		Boolean hasDeleted = enrolleeService.deleteDependent(enrolleeId, dependentId);
		return ResponseEntity.ok(hasDeleted);
	}
}
