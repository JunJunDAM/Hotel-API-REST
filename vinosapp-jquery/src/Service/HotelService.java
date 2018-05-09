/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Repository.HotelDAO;
import Domain.Hotel;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alu2015059
 */
@Path("/hotels")
public class HotelService {
    
    HotelDAO hotelDAO = new HotelDAO();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hotel> findAll() {
        return hotelDAO.findAll();
    }
    
    @GET
    @Path("search/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findByName(@PathParam("query") String query) {
        return hotelDAO.findByName(query).get(0);
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findById(@PathParam("id") int id) {
        System.out.println("Finding hotel by id : " + id);
        return hotelDAO.findById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel create(Hotel hotel) {
        System.out.println("Creating hotel" + hotel.getHotel_name());
        return hotelDAO.save(hotel);
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel update(Hotel hotel, @PathParam("id") int id) {
        System.out.println("Updating : " + hotel.getHotel_name());
        hotel.setId(id);
        hotelDAO.save(hotel);
        return hotel;
    }
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void remove(@PathParam("id") int id) {
        hotelDAO.remove(id);
    }
}
