package ua.ihor.blablatracker;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.PageImpl;

import java.io.IOException;

@JsonComponent
public class PageImplJsonSerializer<T> extends JsonSerializer<PageImpl<T>> {
    @Override
    public void serialize(PageImpl<T> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("content", value.getContent());
        gen.writeNumberField("page", value.getNumber());
        gen.writeNumberField("numberOfElements", value.getNumberOfElements());
        gen.writeNumberField("totalPages", value.getTotalPages());
        gen.writeNumberField("totalElements", value.getTotalElements());
        gen.writeEndObject();
    }
}
