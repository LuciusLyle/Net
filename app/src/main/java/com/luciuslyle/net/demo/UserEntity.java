package com.luciuslyle.net.demo;

import java.util.List;

public class UserEntity {

    /**
     * id : 66
     * name : 鲜美时蔬
     * level : 0
     * icon : http://qn.zuizhezhi.com/1593832225898.png
     * pid : 0
     * children : [{"id":69,"name":"叶菜类","level":1,"icon":"http://qn.zuizhezhi.com/1591283651522.jpg","pid":66},{"id":76,"name":"根茎类","level":1,"icon":"http://qn.zuizhezhi.com/1591171029391.jpg","pid":66},{"id":67,"name":"茄果瓜","level":1,"icon":"http://qn.zuizhezhi.com/1591286222284.jpg","pid":66},{"id":80,"name":"豆品类","level":1,"icon":"http://qn.zuizhezhi.com/1591285581133.jpg","pid":66},{"id":75,"name":"菌类","level":1,"icon":"http://qn.zuizhezhi.com/1591285975106.jpg","pid":66},{"id":74,"name":"姜葱蒜","level":1,"icon":"http://qn.zuizhezhi.com/1591286628048.jpg","pid":66}]
     */

    private int id;
    private String name;
    private int level;
    private String icon;
    private int pid;
    private List<ChildrenBean> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean {
        /**
         * id : 69
         * name : 叶菜类
         * level : 1
         * icon : http://qn.zuizhezhi.com/1591283651522.jpg
         * pid : 66
         */

        private int id;
        private String name;
        private int level;
        private String icon;
        private int pid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
