package com.logo.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.logo.domain.Reservation;
import com.logo.domain.ReservationList;
import com.logo.domain.Resource;
import com.logo.domain.ResourceDashboard;
import com.logo.domain.ResourceDashboardList;
import com.logo.domain.ResourceList;
import com.logo.domain.ResourceTypesList;
import com.logo.domain.Token;
import com.logo.domain.TokenResponse;
import com.logo.domain.User;
import com.logo.domain.UserList;

/**
 */
public class RestService {

    private Client client;
    private WebTarget target;
    
    public static RestService instance;

    public RestService() {
    	 
        client = ClientBuilder.newClient();
        //example query params: ?q=Turku&cnt=10&mode=json&units=metric
        target = client.target(
                "http://onerkaya:7080/api/v1");
    }
    
    private Builder getTokenService(){
    	return target.path("token")
        		.request(MediaType.APPLICATION_JSON_TYPE);
    }
    
    private Builder getUserService(){
    	return target.path("user")
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getUserService(String userName){
    	return target.path("userdetails").queryParam("email", userName)
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getResourceService(int resourceId){
    	return target.path("resources").queryParam("Id", resourceId)
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getResourceService(){
    	return target.path("resources")
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getReservationsService(){
    	return target.path("reservations")
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getReservationsService(int reservationId){
    	return target.path("reservations").queryParam("Id", reservationId)
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getReservationService(){
    	return target.path("reservationdetails")
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    private Builder getResourceTypeService(){
    	return target.path("resourcetype")
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
   private Builder getResourceDashboardService()
   {
	   return target.path("resourcedashboard")
       		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
   }
    
    public ReservationList getScheduleList(int resourceTypeId) {
    	return getScheduleService(resourceTypeId).
        		get(ReservationList.class);
    }
    
    public TokenResponse getTokenResponse(Token token) {
        return getTokenService().
        		post(Entity.entity(token.toString() ,MediaType.APPLICATION_FORM_URLENCODED_TYPE), TokenResponse.class);
    }
    
    public UserList getUserList() {
    	return getUserService().
        		get(UserList.class);
    }
    
    public ResourceList getResourceList(int resourceId) {
    	return getResourceService(resourceId).
        		get(ResourceList.class);
    }
    
    public ResourceList getResourceListDetail() {
    	return target.path("resourcedetails")
    		   .request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
        	   .get(ResourceList.class);
    }
    
    public void persistResource(Resource resource){
    	getResourceService().
    		post(Entity.entity(resource.toString() ,MediaType.APPLICATION_JSON_TYPE));
    }
    
    public void persistReservation(Reservation reservation){
    	getReservationsService().
    		post(Entity.entity(reservation.toString() ,MediaType.APPLICATION_JSON_TYPE));
    }
    
    public void deleteResource(int resourceId){
    	getResourceService(resourceId).
    		delete();
    }
    
    public void deleteReservation(int reservationId){
    	getReservationsService(reservationId).
    		delete();
    }
    
    public void approveReservation(int reservationId){
    	target.path("reservations/"+reservationId+"/ApproveOrNot").queryParam("reservationId", reservationId).queryParam("isApprove", true)
		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
		.get();
    }
    
    public void disapproveReservation(int reservationId){
    	target.path("reservations/"+reservationId+"/ApproveOrNot").queryParam("reservationId", reservationId).queryParam("isApprove", false)
		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
		.get();
    }
    
    public ResourceList getResourceByType(int type){
    	return target.path("resources/"+type)
		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
		.get(ResourceList.class);
    	
//    	return target.path("resources/"+type+"/GetByResourceType").queryParam("resourceType", type)
//    			.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
//    			.get(ResourceList.class);
    }
    
    public User getUserByName(String userName){
    	UserList user = getUserService(userName).
    			get(UserList.class);
    	if(user.getUsers().size() > 0)
    		return user.getUsers().get(0);
    	return null;
    }
    
    public ReservationList getReservationList() {
    	return getReservationService().
        		get(ReservationList.class);
    }
    
    public ResourceTypesList getResourceTypes() {
    	return getResourceTypeService().
        		get(ResourceTypesList.class);
    }
    
   
    public ResourceDashboardList getResourceDashboardList()
    {
    	return getResourceDashboardService()
    			.get(ResourceDashboardList.class);
    }
    
    private Builder getScheduleService(int resourceTypeId){
    	return target.path("ReservationDetails/"+resourceTypeId+"/CurrentReservations").queryParam("resourceTypeId", resourceTypeId)
        		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token());
    }
    
    public ReservationList getReservationsByUser(int userId){
    	return target.path("reservationdetails/"+userId+"/UserCurrentReservations")
		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
		.get(ReservationList.class);
    }
    
    public ReservationList getReservationsByUserAndStatus(int userId, int status){
    	return target.path("reservationdetails/"+userId+"/UserCurrentReservations").queryParam("status", status)
		.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", TokenResponse.instance.getToken_type() + " " + TokenResponse.instance.getAccess_token())
		.get(ReservationList.class);
    }
}
