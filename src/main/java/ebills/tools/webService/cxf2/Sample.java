package ebills.tools.webService.cxf2;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sample/")
public interface Sample {

	@GET
	@Path("/sayHello/{name}/{age}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	String sayHello(@PathParam("name")String name, @PathParam("age")String age);
	
}
