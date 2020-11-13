package com.sms.boot;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes={Application.class})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @BeforeEach
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
	@Test
	@Order(1)  
	// add a new user
	  public void addUser() throws Exception {
		  String json="{\"userName\":\"Go\",\"password\":\"12345\",\"userFirstname\":\"Go\",\"userLastname\":\"G\",\"userEmail\":\"Go@gmail.com\",\"userAge\":25,\"userRole\":\"staff\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/signup")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());

  }
	
	@Test
	@Order(2)  
	// should get the user just added
    public void getEvent() throws Exception {
		  String json="{\"userName\":\"Go\",\"password\":\"12345\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/login")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json.getBytes())
	          )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Go"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("12345"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userFirstname").value("Go"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userLastname").value("G"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail").value("Go@gmail.com"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userAge").value(25))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userRole").value("staff"))
           .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(3)  
	// update the user just added and check
    public void updateUser() throws Exception {
		String json="{\"userId\":6,\"userName\":\"Go\",\"password\":\"12345\",\"userFirstname\":\"Goo\",\"userLastname\":\"Go\",\"userEmail\":\"Goo@gmail.com\",\"userAge\":20,\"userRole\":\"student\"}";
        mvc.perform(MockMvcRequestBuilders.post("/editprofile")
    			.contentType("application/json")
    			.accept(MediaType.APPLICATION_JSON)
                .content(json.getBytes())
            )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("Go"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("12345"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userFirstname").value("Goo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userLastname").value("Go"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail").value("Goo@gmail.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userAge").value(20))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userRole").value("student"))
        .andDo(MockMvcResultHandlers.print());

    }

}
