package com.embraiz.entity.base;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class UpdateMap extends DefaultMap {
    private List<DefaultParameter> updateParameters;

    public UpdateMap() {
        super();
        this.updateParameters = new ArrayList<>();
    }

    public void upd(String name, Object value) {
        if (value != null) {
            this.updateParameters.add(new DefaultParameter(name, value));
        }

    }

    public List<DefaultParameter> getUpdateParameters() {
        return updateParameters;
    }

    public void setUpdateParameters(List<DefaultParameter> updateParameters) {
        this.updateParameters = updateParameters;
    }
}
