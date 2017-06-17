package domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 返回存储的结果
 */
public class ResultItems {

    private Map<String, Object> fields = new LinkedHashMap<String, Object>();

    private Request request;

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
