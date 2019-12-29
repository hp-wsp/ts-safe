package com.ts.server.safe.base.controller.man.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 检查类别输出
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTypeVo {
    @ApiModelProperty("编号")
    private final String id;
    @ApiModelProperty("名称")
    private final String name;
    @ApiModelProperty("检查项目集合")
    private final List<UniCheckItemVo> items;

    /**
     * 构造{@link UniCheckTypeVo}
     *
     * @param id 编号
     * @param name 名称
     * @param items 检查项目集合
     */
    public UniCheckTypeVo(String id, String name, List<UniCheckItemVo> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<UniCheckItemVo> getItems() {
        return items;
    }

    /**
     * 检查项目
     */
    public static class UniCheckItemVo {
        @ApiModelProperty("编号")
        private final String id;
        @ApiModelProperty("项目名称")
        private final String name;

        /**
         * 构造{@link UniCheckItemVo}
         *
         * @param id 编号
         * @param name 名称
         */
        public UniCheckItemVo(String id, String name){
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
