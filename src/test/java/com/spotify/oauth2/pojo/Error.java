package com.spotify.oauth2.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Getter @Setter
@Jacksonized
@Builder
public class Error {

    InnerError error;


}
