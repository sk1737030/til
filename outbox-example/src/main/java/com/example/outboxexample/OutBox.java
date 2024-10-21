/*
package com.example.outboxexample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hibernate.annotations.Type;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.UUID;

@Entity
public class OutBox {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  private UUID id;
  private String aggregateType;
  private String aggregateId;
  private String type;
  @Lob
  private String payload;

  public OutBox() {
  }

  public OutBox(DomainEventEnvelope envelope) {
    this.aggregateType = envelope.getAggregateType();
    this.aggregateId = envelope.getAggregateId();
    this.type = envelope.getEventType();
    ObjectMapper mapper =
      Jackson2ObjectMapperBuilder.json().featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .modules(new JavaTimeModule())
        .build();
    try {
      this.payload = mapper.writeValueAsString(envelope.getEvent());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}*/
