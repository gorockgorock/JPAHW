package com.sparta.travelnewsfeed.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CommonResponseDto {

        private String msg;
        private Integer statusCode;

        public CommonResponseDto (String msg, Integer statusCode ) {
            this.msg = msg;
            this.statusCode = statusCode;
        }
    }

