package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.Professor;

/**
 * 修改专家
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ProfessorUpdateForm extends ProfessorSaveForm {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Professor toDomain(String channelId){
        Professor t = super.toDomain(channelId);
        t.setId(id);

        return t;
    }
}
