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
class ShareControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @BeforeEach
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
	@Test
	@Order(1)  
	// add one event and share
	  public void addShare() throws Exception {
		// add one event for sharing
		  String json1="{\"userId\":2,\"eventTitle\":\"TitileTest3\",\"note\":\"NoteTest3\",\"priority\":3,\"startTime\":\"2020-03-07 00:00:00\",\"endTime\":\"2020-03-08 00:00:00\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/addnewevent")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json1.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());
	      
	      // add a share record
		  String json2="{\"shareUserId\":1,\"eventId\":4}";
	      mvc.perform(MockMvcRequestBuilders.post("/addNewShare")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json2.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());
	      
  }
	
	@Test
	@Order(2)  
	// check the share event just shared
    public void getEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/showAllShareEvent/1")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(4))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("Ao"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userEmail").value("Ao@gmail.com"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventTitle").value("TitileTest3"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].note").value("NoteTest3"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].priority").value(3))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startTime").value("2020-03-07 00:00:00"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endTime").value("2020-03-08 00:00:00"))
           .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(3)  
	// after deleteEvent() there is one row data with is_del = 1 in database share table
	public void deleteEvent() throws Exception {
		  String json1="{\"shareUserId\":1,\"eventId\":4}";
	      mvc.perform(MockMvcRequestBuilders.put("/deleteShare")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json1.getBytes())
	          )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
		
    	//and delete the event
        mvc.perform(MockMvcRequestBuilders.delete("/deleteEvent/4")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
	}

}
