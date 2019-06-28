package com.romanidze.documenti.dto.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class JobRequestDTO(@JsonProperty("job_name")
                         val jobName: String,

                         @JsonProperty("cron_expression")
                         val cronExpression: String)