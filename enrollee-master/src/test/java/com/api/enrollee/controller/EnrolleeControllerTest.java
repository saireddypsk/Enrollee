/**
 * 
 */
package com.api.enrollee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.enrollee.model.Dependent;
import com.api.enrollee.model.Enrollee;
import com.api.enrollee.request.model.DependentRequest;
import com.api.enrollee.request.model.EnrolleeRequest;
import com.api.enrollee.service.EnrolleeService;

/**
 * @author Sai
 *
 */
@WebMvcTest(value = EnrolleeController.class)
class EnrolleeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EnrolleeService enrolleeService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#one(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testOne$ValidData() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setEnrolleeId(9L);
		enrollee.setName("Sam");
		enrollee.setActivationStatus(true);
		enrollee.setDob(new Date());
		Mockito.when(enrolleeService.getEnrollee(Mockito.anyLong())).thenReturn(enrollee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/api/enrollees/9");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
		
	}
	
	@Test
	void testOne$NullScenario() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setEnrolleeId(9L);
		enrollee.setName("Sam");
		enrollee.setActivationStatus(true);
		enrollee.setDob(new Date());
		Mockito.when(enrolleeService.getEnrollee(Mockito.anyLong())).thenReturn(enrollee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/api/enrollees/null");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
		
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#enrollees(com.api.enrollee.request.model.EnrolleeRequest)}.
	 * @throws Exception 
	 */
	@Test
	void testenrolleesenrolleeRequest() throws Exception {
		Enrollee enrolleeObj = new Enrollee();
		enrolleeObj.setEnrolleeId(9l);
		enrolleeObj.setActivationStatus(false);
		String enrollee = "{\"enrolleeId\":9,\"name\":\"Sam\",\"activationStatus\":true,\"dob\":\"2020-09-26T11:04:12.302+00:00\",\"phoneNumber\":null,\"dependents\":null}";
		Mockito.when(enrolleeService.registerEnrollee(Mockito.any(EnrolleeRequest.class))).thenReturn(enrolleeObj);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/api/enrollees")
															  .accept(MediaType.APPLICATION_JSON)
															  .content(enrollee)
															  .contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, mvcResult.getResponse().getStatus());
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#enrollees(java.lang.Long, com.api.enrollee.request.model.EnrolleeRequest)}.
	 * @throws Exception 
	 */
	@Test
	void testenrolleesLongenrolleeRequest() throws Exception {
		Enrollee enrolleeObj = new Enrollee();
		enrolleeObj.setEnrolleeId(9l);
		enrolleeObj.setActivationStatus(false);
		String enrollee = "{\"enrolleeId\":9,\"name\":\"Sam\",\"activationStatus\":true,\"dob\":\"2020-09-26T11:04:12.302+00:00\",\"phoneNumber\":null,\"dependents\":null}";
		Mockito.when(enrolleeService.editEnrollee(Mockito.anyLong(), Mockito.any(EnrolleeRequest.class))).thenReturn(enrolleeObj);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/api/enrollees/9")
															  .accept(MediaType.APPLICATION_JSON)
															  .content(enrollee)
															  .contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"enrolleeId\":9,\"name\":null,\"activationStatus\":false,\"dob\":null,\"phoneNumber\":null,\"dependents\":null}";
		assertEquals(expected, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#enrollees(java.lang.Long)}.
	 * @throws Exception 
	 */
	@Test
	void testenrolleesLong() throws Exception {
		Mockito.when(enrolleeService.deleteEnrollee(Mockito.anyLong())).thenReturn(true);
		RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/api/enrollees/8");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		assertEquals("true", mvcResult.getResponse().getContentAsString());
		
	}
	
	@Test
	void testenrolleesLong$invalidenrollee() throws Exception {
		Mockito.when(enrolleeService.deleteEnrollee(Mockito.anyLong())).thenReturn(true);
		RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/api/enrollees/null");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#dependents(com.api.enrollee.request.model.DependentRequest)}.
	 */
	@Test
	void testDependentsDependentRequest() throws Exception{
		Enrollee enrollee = new Enrollee();
		enrollee.setEnrolleeId(9L);
		enrollee.setName("Sam");
		enrollee.setActivationStatus(true);
		enrollee.setDob(null);
		Dependent dependent = new Dependent();
		dependent.setEnrolleeId(9l);
		dependent.setDependentId(99l);
		dependent.setName("myDependent");
		Set<Dependent> dependents = new HashSet<Dependent>();
		dependents.add(dependent);
		enrollee.setDependents(dependents);
		String dependentContent = "{\"enrolleeId\":9,\"dependentId\":99,\"dependentName\":\"John\",\"dependentDob\":null}";
		Mockito.when(enrolleeService.addDependent(Mockito.any(DependentRequest.class))).thenReturn(enrollee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/api/dependents")
															 .accept(MediaType.APPLICATION_JSON)
															  .content(dependentContent)
															  .contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"enrolleeId\":9,\"name\":\"Sam\",\"activationStatus\":true,\"dob\":null,\"phoneNumber\":null,\"dependents\":[{\"dependentId\":99,\"enrolleeId\":9,\"name\":\"myDependent\",\"dob\":null}]}"; 
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(expected, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#dependents(java.lang.Long, com.api.enrollee.request.model.DependentRequest)}.
	 */
	@Test
	void testDependentsLongDependentRequest() throws Exception{
		Enrollee enrollee = new Enrollee();
		enrollee.setEnrolleeId(9L);
		enrollee.setName("Sam");
		enrollee.setActivationStatus(true);
		Dependent dependent = new Dependent();
		dependent.setEnrolleeId(9l);
		dependent.setDependentId(99l);
		dependent.setName("updatedDependent");
		Set<Dependent> dependents = new HashSet<Dependent>();
		dependents.add(dependent);
		enrollee.setDependents(dependents);
		String dependentString = "{\"enrolleeId\":9,\"dependentId\":99,\"dependentName\":\"John\",\"dependentDob\":null}";
		Mockito.when(enrolleeService.editDependent(Mockito.any(DependentRequest.class))).thenReturn(enrollee);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/api/dependents/7")
															  .accept(MediaType.APPLICATION_JSON)
															  .content(dependentString)
															  .contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{\"enrolleeId\":9,\"name\":\"Sam\",\"activationStatus\":true,\"dob\":null,\"phoneNumber\":null,\"dependents\":[{\"dependentId\":99,\"enrolleeId\":9,\"name\":\"updatedDependent\",\"dob\":null}]}";
		assertEquals(200, mvcResult.getResponse().getStatus());
		assertEquals(expected, mvcResult.getResponse().getContentAsString());
	}

	/**
	 * Test method for {@link com.api.enrollee.controller.EnrolleeController#dependents(java.lang.Long, java.lang.Long)}.
	 */
	@Test
	void testDependentsLongLong() throws Exception {
		Mockito.when(enrolleeService.deleteDependent(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/api/dependents/enrolleeId/9/dependentId/99");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		assertEquals("true", mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	void testDependentsLongLong$InvalidEnrollee() throws Exception {
		Mockito.when(enrolleeService.deleteDependent(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/api/dependents/enrolleeId/null/dependentId/99");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}
	
	@Test
	void testDependentsLongLong$InvalidDepndent() throws Exception {
		Mockito.when(enrolleeService.deleteDependent(Mockito.anyLong(), Mockito.anyLong())).thenReturn(true);
		RequestBuilder builder = MockMvcRequestBuilders.delete("/v1/api/dependents/enrolleeId/9/dependentId/null");
		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
	}

}
