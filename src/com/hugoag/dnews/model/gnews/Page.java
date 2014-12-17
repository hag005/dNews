
package com.hugoag.dnews.model.gnews;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Page {

    @Expose
    private String start;
    @Expose
    private Integer label;

    /**
     * 
     * @return
     *     The start
     */
    public String getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * 
     * @return
     *     The label
     */
    public Integer getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(Integer label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
