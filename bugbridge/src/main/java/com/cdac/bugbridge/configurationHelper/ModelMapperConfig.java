package com.cdac.bugbridge.configurationHelper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;

@Configuration
public class ModelMapperConfig {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();

    // Explicitly map User to User ID in BugDTO
    modelMapper.typeMap(Bug.class, BugDTO.class).addMappings(mapper -> {
      mapper.map(src -> src.getReportedBy().getId(), BugDTO::setReportedBy);
      mapper.map(src -> src.getAssignedTo() != null ? src.getAssignedTo().getId() : null, BugDTO::setAssignedTo);
    });

    return modelMapper;
  }
}
