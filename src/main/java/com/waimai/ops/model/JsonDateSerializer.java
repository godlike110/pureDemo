package com.waimai.ops.model;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * zhiweiwen
 */
public class JsonDateSerializer extends DateSerializer {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
	  JsonGenerationException {
		String formateDate=simpleDateFormat.format(value);
		jgen.writeString(formateDate);
    }

}
