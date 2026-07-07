package ControllerPerso;

import java.util.HashMap;
import java.util.Map;

public class ModelView {

    private String viewName;

    private Map<String, Object> data = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public void addData(String key, Object value) {
        data.put(key, value);
    }

    public String getViewName()         { return viewName; }
    public Map<String, Object> getData(){ return data; }
}