package com.romanidze.documenti.config.mapstruct

import org.mapstruct.MapperConfig
import org.mapstruct.InjectionStrategy
import org.mapstruct.ReportingPolicy

@MapperConfig(componentModel = "spring",
              unmappedTargetPolicy = ReportingPolicy.IGNORE,
              injectionStrategy = InjectionStrategy.CONSTRUCTOR)
interface MapStructConfig