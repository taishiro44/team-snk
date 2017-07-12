/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
/**
 *
 * @author kjaeyun
 */
@Path("room")
public class SampleResource {
    
    @GET
    public String sample(){
        return "SampleResource";
    }
    
    @GET
    @Path("increment")
    public String inc(@QueryParam("Value") String value){
        Integer v = Integer.parseInt(value);
        v++;
        return "inc value="+v;
    }    
}
