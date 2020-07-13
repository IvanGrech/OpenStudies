//package com.openstudies.tests.webservices.rest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.openstudies.model.entities.Role;
//import com.openstudies.model.entities.User;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.methods.*;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.junit.BeforeClass;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import java.io.IOException;
//import java.sql.Date;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class UserRestWebServiceTest {
//    private static ObjectMapper objectMapper;
//
//    private static final String baseURL = "http://10.10.21.239:8080/maven-1.0-SNAPSHOT";
//    private static final String mediaType = "application/json";
//
//    @BeforeClass
//    public static void setup() throws IOException {
//        objectMapper = new ObjectMapper();
//
//    }
//
//    public boolean checkMediaType(HttpResponse response) {
//        String testMediaType = ContentType.getOrDefault(response.getEntity())
//                .getMimeType();
//        if (mediaType.equals(testMediaType))
//            return true;
//        return false;
//    }
//
//    @Test
//    public void testGetAllUsers() throws IOException {
//        HttpUriRequest request = new HttpGet(baseURL + "/users");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//        assertTrue(checkMediaType(response));
//    }
//
//    @Test
//    public void testGetUserByLogin() throws IOException {
//        HttpUriRequest request = new HttpGet(baseURL + "/users/login/admin");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//        assertTrue(checkMediaType(response));
//    }
//
//    @Test
//    public void testGetUserByLoginFail() throws IOException {
//        HttpUriRequest request = new HttpGet(
//                baseURL + "/users/login/notExistingLogin");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_NOT_FOUND,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testGetUserByEmail() throws IOException {
//        HttpUriRequest request = new HttpGet(
//                baseURL + "/users/email/somebody123@gmail.com/");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//        assertTrue(checkMediaType(response));
//    }
//
//    @Test
//    public void testGetUserByEmailFail() throws IOException {
//        HttpUriRequest request = new HttpGet(
//                baseURL + "/users/email/notexistingemail@gmail.com/");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_NOT_FOUND,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testGetUserById() throws IOException {
//        HttpUriRequest request = new HttpGet(baseURL + "/users/id/1");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//        assertTrue(checkMediaType(response));
//    }
//
//    @Test
//    public void testGetUserByIdFail() throws IOException {
//        HttpUriRequest request = new HttpGet(baseURL + "/users/id/10000");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_NOT_FOUND,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testCreateUser() throws IOException {
//        HttpPost request = new HttpPost(baseURL + "/users/create");
//        User user = new User();
//        user.setFirstName("Re");
//        user.setLastName("St");
//        user.setEmail("rest@gmail.com");
//        user.setRole(new Role(1l, "admin"));
//        user.setPassword("pass");
//        user.setBirthday(new Date(1999 + 1900, 11, 16));
//        user.setLogin("restedUser");
//        HttpEntity entity = new StringEntity(
//                objectMapper.writeValueAsString(user),
//                ContentType.APPLICATION_JSON);
//        request.setEntity(entity);
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_CREATED,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testCreateUserFail() throws IOException {
//        HttpPost request = new HttpPost(baseURL + "/users/create");
//        User user = new User();
//        user.setId(500l);
//        user.setFirstName("Re");
//        user.setLastName("St");
//        user.setEmail("rest@gmail.com");
//        user.setRole(new Role(1l, "admin"));
//        user.setPassword("pass");
//        user.setBirthday(new Date(1999 + 1900, 11, 16));
//        user.setLogin("admin");//user with this login already exists
//        HttpEntity entity = new StringEntity(
//                objectMapper.writeValueAsString(user),
//                ContentType.APPLICATION_JSON);
//        request.setEntity(entity);
//
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_CONFLICT,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testUpdateUser() throws IOException {
//        HttpPut request = new HttpPut(baseURL + "/users/update");
//
//        User user = new User();
//        user.setId(48l);
//        user.setFirstName("UpdatedFname");
//        user.setLastName("UpdatedLname");
//        user.setEmail("newemail@gmail.com");
//        user.setRole(new Role(2l, "user"));
//        user.setPassword("pass");
//        user.setBirthday(new Date(1999 + 1900, 11, 16));
//        user.setLogin("agent");
//
//        HttpEntity entity = new StringEntity(
//                objectMapper.writeValueAsString(user),
//                ContentType.APPLICATION_JSON);
//        request.setEntity(entity);
//
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testUpdateUserFail() throws IOException {
//        HttpPut request = new HttpPut(baseURL + "/users/update");
//
//        User user = new User();
//        user.setId(48l);
//        user.setFirstName("UpdatedFname");
//        user.setLastName("UpdatedLname");
//        user.setEmail("admin@gmail.com");//user with this email already exists
//        user.setRole(new Role(2l, "user"));
//        user.setPassword("pass");
//        user.setBirthday(new Date(1999 + 1900, 11, 16));
//        user.setLogin("agent");
//
//        HttpEntity entity = new StringEntity(
//                objectMapper.writeValueAsString(user),
//                ContentType.APPLICATION_JSON);
//        request.setEntity(entity);
//
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_CONFLICT,
//                response.getStatusLine().getStatusCode());
//    }
//
//    @Test
//    public void testDeleteUser() throws IOException {
//
//        HttpDelete request = new HttpDelete(
//                baseURL + "/users/delete/login/restedUser");
//        HttpResponse response = HttpClientBuilder.create().build()
//                .execute(request);
//        assertEquals(HttpStatus.SC_OK,
//                response.getStatusLine().getStatusCode());
//
//    }
//}
//
