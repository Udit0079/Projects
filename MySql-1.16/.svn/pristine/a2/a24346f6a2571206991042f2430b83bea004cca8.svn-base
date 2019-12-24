/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.neftrtgs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImpsOwResponseDto implements Serializable {

//    @JsonProperty("OthrFeature")
//    private String OthrFeature;
    @JsonProperty("ChannelReplyObj")
    private ChannelReplyDto ChannelReplyObj;

    public ChannelReplyDto getChannelReplyObj() {
        return ChannelReplyObj;
    }

    public void setChannelReplyObj(ChannelReplyDto ChannelReplyObj) {
        this.ChannelReplyObj = ChannelReplyObj;
    }
}
