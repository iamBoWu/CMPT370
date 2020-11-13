package com.sms.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
class CalenderControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @BeforeEach
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
	
	@Test
	@Order(1)  
	// add two events to test getAllEvent()
	  public void addEvent() throws Exception {
		  String json1="{\"userId\":2,\"eventTitle\":\"TitileTest2\",\"note\":\"NoteTest2\",\"priority\":2,\"startTime\":\"2020-03-06 00:00:00\",\"endTime\":\"2020-03-07 00:00:00\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/addnewevent")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json1.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());
	      
		  String json2="{\"userId\":2,\"eventTitle\":\"TitileTest3\",\"note\":\"NoteTest3\",\"priority\":1,\"startTime\":\"2020-03-05 00:00:00\",\"endTime\":\"2020-03-06 00:00:00\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/addnewevent")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json2.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());

  }

	@Test
	@Order(2)  
	// should get events just added
    public void getAllEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/showAllevent/2")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventTitle").value("TitileTest2"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].note").value("NoteTest2"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].priority").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startTime").value("2020-03-06 00:00:00"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endTime").value("2020-03-07 00:00:00"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventId").value(3))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventTitle").value("TitileTest3"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].note").value("NoteTest3"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].priority").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].startTime").value("2020-03-05 00:00:00"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].endTime").value("2020-03-06 00:00:00"))
           .andDo(MockMvcResultHandlers.print());
        
    }
	
	@Test
	@Order(3)  
	// should get the event with the eventTitle which contain 3
    public void getSearchEvents() throws Exception {
		  String json1="{\"userId\":2,\"eventTitle\":\"3\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/showSearchEvent")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json1.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(3))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(2))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventTitle").value("TitileTest3"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].note").value("NoteTest3"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].priority").value(1))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startTime").value("2020-03-05 00:00:00"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endTime").value("2020-03-06 00:00:00"))
	         .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(4)  
	// events order sort by priority
    public void getEventsByPriority() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/sortbypriority/2")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
	          .andExpect(MockMvcResultMatchers.status().isOk())
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(3))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[0].priority").value(1))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventId").value(2))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[1].priority").value(2))
	          .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(5)  
	// events order sort by starttime
    public void getEventsByStartTime() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/sortbystarttime/2")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
	          .andExpect(MockMvcResultMatchers.status().isOk())
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventId").value(2))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].startTime").value("2020-03-06 00:00:00"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].endTime").value("2020-03-07 00:00:00"))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(3))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startTime").value("2020-03-05 00:00:00"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endTime").value("2020-03-06 00:00:00"))
	          .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(6)  
	// events order sort by endtime
    public void getEventsByEndTime() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/sortbystarttime/2")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
	          .andExpect(MockMvcResultMatchers.status().isOk())
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[1].eventId").value(2))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].startTime").value("2020-03-06 00:00:00"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].endTime").value("2020-03-07 00:00:00"))
	          .andExpect(MockMvcResultMatchers.jsonPath("$.[0].eventId").value(3))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startTime").value("2020-03-05 00:00:00"))
	           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endTime").value("2020-03-06 00:00:00"))
	          .andDo(MockMvcResultHandlers.print());
        
    	// delete two events
        mvc.perform(MockMvcRequestBuilders.delete("/deleteEvent/2")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
        
        mvc.perform(MockMvcRequestBuilders.delete("/deleteEvent/3")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());

    }
	

}
