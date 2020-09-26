/**
 * 
 */

package com.api.enrollee.service;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.enrollee.exception.ResourceNotFoundException;
import com.api.enrollee.model.Dependent;
import com.api.enrollee.model.Enrollee;
import com.api.enrollee.repository.EnrolleeRepository;
import com.api.enrollee.request.model.DependentRequest;
import com.api.enrollee.request.model.EnrolleeRequest;

@Service
public class EnrolleeService {

	@Autowired
	private EnrolleeRepository enrolleeRepository;

	/**
	 * This method holds the logic to fetch the enrollee based on given enrollId.
	 * @param id Holds the enrolleeId.
	 * @return enrollee object.
	 */
	public Enrollee registerEnrollee(EnrolleeRequest enrolleeRequest) {
		Enrollee enrollee = new Enrollee();
		BeanUtils.copyProperties(enrolleeRequest, enrollee);
		enrollee.setCreatedTimestamp(new Date());
		enrollee.setUpdatedTimestamp(new Date());
		return enrolleeRepository.save(enrollee);
	}

	/**
	 * This method holds the logic to add the enrollee.
	 * @param enrolleeRequest Holds the enrollee details.
	 * @return enrollee object.
	 */
	public Enrollee getEnrollee(Long id) {
		return enrolleeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("enrollee", "id", id));
	}

	/**
	 * This method holds the logic to edit the enrollee based on given enrollId.
	 * @param id holds the enrolleeId.
	 * @param enrolleeRequest holds the modified enrollee details.
	 * @return holds the updated enrollee object.
	 */
	public Enrollee editEnrollee(Long id, EnrolleeRequest enrolleeRequest) {
		Enrollee enrollee = enrolleeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("enrollee", "id", id));
		BeanUtils.copyProperties(enrolleeRequest, enrollee);
		enrollee.setUpdatedTimestamp(new Date());
		return enrolleeRepository.save(enrollee);
	}

	/**
	 * This method holds the logic to delete the enrollee based on given enrollId.
	 * @param id Holds the enrolleeId.
	 * @return enrollee object.
	 */
	public Boolean deleteEnrollee(Long id) {
		enrolleeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("enrollee", "id", id));
		enrolleeRepository.deleteById(id);
		return true;
	}

	/**
	 * This method holds the logic to add the dependents of an enrollee based on given enrollId.
	 * @param dependentRequest holds the enrollee dependents details.
	 * @return newly added enrollee object.
	 */
	public Enrollee addDependent(@Valid DependentRequest dependentRequest) {
		Enrollee enrollee = enrolleeRepository.findById(dependentRequest.getEnrolleeId())
				.orElseThrow(() -> new ResourceNotFoundException("enrollee", "id", dependentRequest.getEnrolleeId()));
		Dependent dependent = new Dependent();
		dependent.setEnrolleeId(dependentRequest.getEnrolleeId());
		dependent.setName(dependentRequest.getDependentName());
		dependent.setDob(dependentRequest.getDependentDob());
		dependent.setCreatedTimestamp(new Date());
		dependent.setUpdatedTimestamp(new Date());
		enrollee.getDependents().add(dependent);
		return enrolleeRepository.save(enrollee);
	}

	/**
	 * This method holds the logic to edit dependent based on given dependentId.
	 * @param dependentRequest holds the enrollee dependents details.
	 * @return updated dependent object.
	 */
	public Enrollee editDependent(@Valid DependentRequest dependentRequest) {
		Enrollee enrollee = enrolleeRepository.findById(dependentRequest.getEnrolleeId()).get();
		Dependent dependent = enrollee.getDependents().stream()
				.filter(dep -> dep.getDependentId().equals(dependentRequest.getDependentId())).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Dependent", "id", dependentRequest.getDependentId()));
		dependent.setEnrolleeId(dependentRequest.getEnrolleeId());
		dependent.setName(dependentRequest.getDependentName());
		dependent.setDob(dependentRequest.getDependentDob());
		dependent.setUpdatedTimestamp(new Date());
		enrollee.getDependents().add(dependent);
		return enrolleeRepository.save(enrollee);
	}

	/**
	 * This method holds the logic to delete dependent based on given dependentId.
	 * @param enrolleeId Holds the enrollees Id.
	 * @param dependentId Holds the dependents Id.
	 * @return delete status.
	 */
	public Boolean deleteDependent(@NotNull final Long enrolleeId, @NotNull final Long dependentId) {
		Enrollee enrollee = enrolleeRepository.findById(enrolleeId)
				.orElseThrow(() -> new ResourceNotFoundException("enrollee", "id", enrolleeId));
		enrollee.getDependents().stream().filter(dep -> dep.getDependentId().equals(dependentId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Dependent", "id", dependentId));
		enrollee.getDependents().removeIf(dependent -> dependent.getDependentId().equals(dependentId));
		enrolleeRepository.save(enrollee);
		return true;
	}

}
