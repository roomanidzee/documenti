package com.romanidze.documenti.dto.jobs

import com.fasterxml.jackson.annotation.JsonProperty

data class JobRequestDTO(@JsonProperty("job_name")
                         val jobName: String,

                         val properties: Map<String, String>?,

                         @JsonProperty("is_async")
                         val isAsync: Boolean,

                         @JsonProperty("cron_expression")
                         val cronExpression: String)