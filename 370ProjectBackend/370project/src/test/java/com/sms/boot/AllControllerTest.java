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
class AllControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @BeforeEach
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
	@Test
	@Order(1)  
	// add a new event
	  public void addEvent() throws Exception {
		  String json="{\"userId\":1,\"eventTitle\":\"TitileTest1\",\"note\":\"NoteTest1\",\"priority\":3,\"startTime\":\"2020-03-05 00:00:00\",\"endTime\":\"2020-03-06 00:00:00\"}";
	      mvc.perform(MockMvcRequestBuilders.post("/addnewevent")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());

  }
    
	@Test
	@Order(2)  
	// should get the event just added
    public void getEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getevent/1")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.eventId").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.eventTitle").value("TitileTest1"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.note").value("NoteTest1"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(3))
           .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("2020-03-05 00:00:00"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("2020-03-06 00:00:00"))
           .andDo(MockMvcResultHandlers.print());

    }
	
	@Test
	@Order(3)  
    public void updateEvent() throws Exception {
		String json="{\"eventId\":1,\"eventTitle\":\"UpdateTitileTest1\",\"note\":\"UpdateNoteTest1\",\"priority\":1,\"startTime\":\"2020-03-06 20:00:00\",\"endTime\":\"2020-03-07 21:00:00\"}";
        mvc.perform(MockMvcRequestBuilders.put("/updateEvent")
    			.contentType("application/json")
    			.accept(MediaType.APPLICATION_JSON)
                .content(json.getBytes())
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andDo(MockMvcResultHandlers.print());
        
        // use getEvent() to check
        mvc.perform(MockMvcRequestBuilders.get("/getevent/1")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andExpect(MockMvcResultMatchers.jsonPath("$.eventId").value(1))
       .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
       .andExpect(MockMvcResultMatchers.jsonPath("$.eventTitle").value("UpdateTitileTest1"))
       .andExpect(MockMvcResultMatchers.jsonPath("$.note").value("UpdateNoteTest1"))
       .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(1))
       .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").value("2020-03-06 20:00:00"))
       .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").value("2020-03-07 21:00:00"))
       .andExpect(MockMvcResultMatchers.jsonPath("$.isDel").value(0))
       .andDo(MockMvcResultHandlers.print());
        
    }
	
	@Test
	@Order(4)  
	// after deleteEvent() there is one row data with is_del = 1 in database event table
	public void deleteEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteEvent/1")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	@Order(5)  
	// add two events to test getAllEvent()
	  public void calenderAddEvent() throws Exception {
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
	@Order(6)  
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
	@Order(7)  
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
	@Order(8)  
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
	@Order(9)  
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
	@Order(10)  
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
	
	@Test
	@Order(11)  
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
	@Order(12)  
	// should get the user just added
    public void userGetEvent() throws Exception {
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
	@Order(13)  
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
	
	
	@Test
	@Order(14)  
	// add two friends to user id = 1
	  public void addFriend() throws Exception {
		  String json1="{\"userId\":1,\"friendId\":2,\"friendGroup\":\"Family\"}";
		  String json2="{\"userId\":1,\"friendId\":3,\"friendGroup\":\"Student\"}";
		  
	      mvc.perform(MockMvcRequestBuilders.post("/addfriend")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json1.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());
	      
	      mvc.perform(MockMvcRequestBuilders.post("/addfriend")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json2.getBytes())
	          )
	         .andExpect(MockMvcResultMatchers.status().isOk())
	         .andDo(MockMvcResultHandlers.print());

	}
	
	@Test
	@Order(15)  
	// should get friends just added
    public void getFriends() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getfirends/1")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tbFriendId").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendGroup").value("Family"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendName").value("Ao"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendEmail").value("Ao@gmail.com"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].groupColor").value("red"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].tbFriendId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId").value(1))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendId").value(3))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendGroup").value("Student"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendName").value("Co"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendEmail").value("Co@gmail.com"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].groupColor").value("volcano"))
           .andDo(MockMvcResultHandlers.print());
    }
	
	
	@Test
	@Order(16)  
	// update tbFriendId = 1  friendGroup to Default
    public void updateFriend() throws Exception {
		String json="{\"tbFriendId\":1,\"friendGroup\":\"Student\"}";
        mvc.perform(MockMvcRequestBuilders.put("/updateFriend")
    			.contentType("application/json")
    			.accept(MediaType.APPLICATION_JSON)
                .content(json.getBytes())
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andDo(MockMvcResultHandlers.print());
        
        
        // use getFriends() to check
        mvc.perform(MockMvcRequestBuilders.get("/getfirends/1")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tbFriendId").value(1))
       .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendGroup").value("Student"))
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andExpect(MockMvcResultMatchers.jsonPath("$.[1].tbFriendId").value(2))
       .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendGroup").value("Student"))
       .andDo(MockMvcResultHandlers.print());
        
    }
	
	@Test
	@Order(17)  
	// get other users who are not user's friends by user id
    public void getOtherUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getotherusers/1")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(4))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("Do"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userRole").value("student"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userAge").value(28))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userEmail").value("Do@gmail.com"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId").value(5))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userName").value("Eo"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userRole").value("professor"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userAge").value(22))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userEmail").value("Eo@gmail.com"))
           .andDo(MockMvcResultHandlers.print());
    }
	
	@Test
	@Order(18)  
	// get all friends include their information by user id
    public void getFriendsWithInfo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getfriendswithinfo/1")
                    .contentType("text/html")
                    .accept(MediaType.APPLICATION_JSON)
            )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userId").value(2))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userName").value("Ao"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userRole").value("student"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userAge").value(20))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userEmail").value("Ao@gmail.com"))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userId").value(3))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userName").value("Co"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userRole").value("student"))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userAge").value(30))
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].userEmail").value("Co@gmail.com"))
           .andDo(MockMvcResultHandlers.print());
    }
	
	@Test
	@Order(19)  
	// get all friends in a specific group
    public void getGroupMember() throws Exception {
		  String json="{\"userId\":1,\"friendGroup\":\"Student\"}";
		  
	      mvc.perform(MockMvcRequestBuilders.post("/getGroupMember")
	      			.contentType("application/json")
	      			.accept(MediaType.APPLICATION_JSON)
	                  .content(json.getBytes())
	          )
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[0].friendId").value(2))
           .andExpect(MockMvcResultMatchers.status().isOk())
           .andExpect(MockMvcResultMatchers.jsonPath("$.[1].friendId").value(3))
           .andDo(MockMvcResultHandlers.print());
    }
	
	@Test
	@Order(20)  
	// after deleteFriend() there are two row data with is_del = 1 in database friend table
	public void deleteFriend() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/deleteFriend/1")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
        
        mvc.perform(MockMvcRequestBuilders.delete("/deleteFriend/2")
                .contentType("text/html")
                .accept(MediaType.APPLICATION_JSON)
        )
       .andExpect(MockMvcResultMatchers.status().isOk())
       .andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	@Order(21)  
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
	@Order(22)  
	// check the share event just shared
    public void shareGetEvent() throws Exception {
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
	@Order(23)  
	// after deleteEvent() there is one row data with is_del = 1 in database share table
	public void shareDeleteEvent() throws Exception {
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
