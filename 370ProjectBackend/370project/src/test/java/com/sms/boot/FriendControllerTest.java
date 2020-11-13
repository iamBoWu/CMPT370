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
class FriendControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    @BeforeEach
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
	@Test
	@Order(1)  
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
	@Order(2)  
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
	@Order(3)  
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
	@Order(4)  
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
	@Order(5)  
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
	@Order(6)  
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
	@Order(7)  
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

}
