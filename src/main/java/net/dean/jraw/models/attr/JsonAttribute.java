package net.dean.jraw.models.attr;

import net.dean.jraw.models.JsonProperty;
import org.codehaus.jackson.JsonNode;

interface JsonAttribute {

    /**
     * See {@link net.dean.jraw.models.JsonModel#getDataNode()}
     * @return The JsonNode to use for methods annotated with the {@link JsonProperty} annotation
     */
    public JsonNode getDataNode();
}
