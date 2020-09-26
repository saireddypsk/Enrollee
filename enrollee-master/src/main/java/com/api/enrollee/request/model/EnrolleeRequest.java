package com.api.enrollee.request.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name","status","dob", "phoneNumber"})
public class EnrolleeRequest {

	@NotNull(message = "Name cannot be Empty.")
	@JsonProperty("name")
	private String name;
	@NotBlank(message = "Status cannot be Empty." )
	@JsonProperty("status")
	private Boolean status;
	@NotBlank(message = "DateOfBirth cannot be Empty.")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty("dob")
	@ApiModelProperty(dataType = "java.sql.Date")
	private Date dob;
	@JsonProperty("phoneNumber")
	private String phoneNumber;
}
