package demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TimeoffManagerControllerTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void aTestCreate() throws Exception {
		mvc.perform(post("/timeoff/add?firstName=Jane&lastName=Doe")).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8")).andExpect(content().string("Employee created: Jane Doe"));
	}
	
	@Test
	public void bTestFind() throws Exception {
		mvc.perform(get("/timeoff/find/1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.firstName").value("Jane"))
				.andExpect(jsonPath("$.lastName").value("Doe")).andExpect(jsonPath("$.fullName").value("Jane Doe"))
				.andExpect(jsonPath("$.remainingHours").value(112));
	}

	@Test
	public void cTestQuery() throws Exception {
		mvc.perform(get("/timeoff/query/1")).andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(content().string("Employee Jane Doe has 112.0 hours remaining."));
	}

	@Test
	public void dTestRequestTimeoff() throws Exception {
		mvc.perform(post("/timeoff/request/1/10")).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(content().string("Employee request successful. Employee has 102.0 hours remaining."));
	}

	@Test
	public void eTestList() throws Exception {
		mvc.perform(get("/timeoff/list/1")).andExpect(status().isOk())
				.andExpect(content().contentType("text/plain;charset=UTF-8")).andExpect(content().string("[10.0]"));
	}


	@Test
	public void fTestDelete() throws Exception {
		mvc.perform(delete("/timeoff/delete/1")).andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=UTF-8"))
				.andExpect(content().string("Employee Jane Doe successfully deleted."));
	}

}
