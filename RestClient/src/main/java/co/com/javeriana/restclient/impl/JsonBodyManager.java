package co.com.javeriana.restclient.impl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes("application/json")
@Produces("application/json")
class JsonBodyManager implements MessageBodyReader, MessageBodyWriter {

    private static final String UTF_8 = "UTF-8";
    private transient final static Gson gSonManager;

    static {
        gSonManager = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy")
                              //  .addSerializationExclusionStrategy(new PublicExclusionStrategy())
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();
    }

    @Override
    public boolean isReadable(Class type, Type type1, Annotation[] antns, MediaType mt) {
         return mt.equals(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] antns, MediaType mt, MultivaluedMap mm, InputStream in) throws IOException, WebApplicationException {
        InputStreamReader streamReader = new InputStreamReader(in, UTF_8);
        Type jsonType;
        if (type.equals(genericType)) {
            jsonType = type;
        } else {
            jsonType = genericType;
        }
        return gSonManager.fromJson(streamReader, jsonType);
    }

    @Override
    public long getSize(Object t, Class type, Type type1, Annotation[] antns, MediaType mt) {
        return -1;
    }

    @Override
    public void writeTo(Object object, Class type, Type genericType, Annotation[] antns, MediaType mt, MultivaluedMap mm, OutputStream out) throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(out, UTF_8);
        Type jsonType;
        if (type.equals(genericType)) {
            jsonType = type;
        } else {
            jsonType = genericType;
        }
        gSonManager.toJson(object, jsonType, writer);
        writer.close();
    }

    @Override
    public boolean isWriteable(Class type, Type type1, Annotation[] antns, MediaType mt) {
        return mt.equals(MediaType.APPLICATION_JSON_TYPE);
    }

}

