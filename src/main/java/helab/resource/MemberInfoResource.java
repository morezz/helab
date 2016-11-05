package helab.resource;

import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by gzlin@coremail.cn on 2016/10/30.
 */

@Path("/member")
public interface MemberInfoResource {

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    ResourceResult listMember(@QueryParam("type") String type);

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    ResourceResult create(@Multipart("cn_name") String nameCN, @Multipart("en_name") String nameEN, @Multipart("type") String type, @Multipart("email") String email, @Multipart("file") Attachment attachment);

    @POST
    @Path("/{id}/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    ResourceResult update(@Multipart("id") Long id, @Multipart("cn_name") String nameCN, @Multipart("en_name") String nameEN, @Multipart("type") String type, @Multipart("email") String email, @Multipart("file") Attachment attachment);

    @POST
    @Path("/{id}/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ResourceResult delete(@PathParam("id") Long id);


}
