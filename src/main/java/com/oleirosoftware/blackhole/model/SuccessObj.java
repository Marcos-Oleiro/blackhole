package com.oleirosoftware.blackhole.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SuccessObj {

    @JsonProperty("desc")
    SuccessDesc successDesc;
}
