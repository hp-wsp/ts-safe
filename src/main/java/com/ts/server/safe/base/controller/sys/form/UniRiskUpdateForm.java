package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniRisk;

public class UniRiskUpdateForm extends UniRiskSaveForm {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniRisk toDomain() {
        UniRisk t = super.toDomain();

        t.setId(id);

        return t;
    }
}
