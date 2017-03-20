package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：Administrator
 * 时间：2015/12/16  13:57
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ExerciseTextBookChoseBean extends BaseBean{


    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * types : {"all":{"name":"全部(500)","value":0},"versions":[{"name":"人教版","value":13},{"name":"冀教版","value":23}],"subjects":[{"name":"教辅类","value":0},{"name":"视频类","value":1}]}
         * sorts : [{"name":"按年级","value":"grade"},{"name":"按首字母","value":"firstchar"}]
         * filters : [{"name":"一年级","value":1},{"name":"二年级","value":2}]
         * stars : [{"name":"一星","value":1},{"name":"二星","value":2},{"name":"三星","value":3}]
         */

        private HeaderEntity header;
        private int totol;
        /**
         * book_id : 123
         * name : 人教版三年级上册
         * en_name : This is a Book
         * grade : 三年级
         * version : 人教版
         * subject : 0
         * star : 0
         * cover : http://xxxxx/1.png
         */

        private List<ListEntity> list;

        public void setHeader(HeaderEntity header) {
            this.header = header;
        }

        public void setTotol(int totol) {
            this.totol = totol;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public HeaderEntity getHeader() {
            return header;
        }

        public int getTotol() {
            return totol;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class HeaderEntity {
            /**
             * all : {"name":"全部(500)","value":0}
             * versions : [{"name":"人教版","value":13},{"name":"冀教版","value":23}]
             * subjects : [{"name":"教辅类","value":0},{"name":"视频类","value":1}]
             */

            private TypesEntity types;
            /**
             * name : 按年级
             * value : grade
             */

            private List<SortsEntity> sorts;
            /**
             * name : 一年级
             * value : 1
             */

            private List<FiltersEntity> filters;
            /**
             * name : 一星
             * value : 1
             */

            private List<StarsEntity> stars;

            public void setTypes(TypesEntity types) {
                this.types = types;
            }

            public void setSorts(List<SortsEntity> sorts) {
                this.sorts = sorts;
            }

            public void setFilters(List<FiltersEntity> filters) {
                this.filters = filters;
            }

            public void setStars(List<StarsEntity> stars) {
                this.stars = stars;
            }

            public TypesEntity getTypes() {
                return types;
            }

            public List<SortsEntity> getSorts() {
                return sorts;
            }

            public List<FiltersEntity> getFilters() {
                return filters;
            }

            public List<StarsEntity> getStars() {
                return stars;
            }

            public static class TypesEntity {
                /**
                 * name : 全部(500)
                 * value : 0
                 */

                private AllEntity all;
                /**
                 * name : 人教版
                 * value : 13
                 */

                private List<VersionsEntity> versions;
                /**
                 * name : 教辅类
                 * value : 0
                 */

                private List<SubjectsEntity> subjects;

                public void setAll(AllEntity all) {
                    this.all = all;
                }

                public void setVersions(List<VersionsEntity> versions) {
                    this.versions = versions;
                }

                public void setSubjects(List<SubjectsEntity> subjects) {
                    this.subjects = subjects;
                }

                public AllEntity getAll() {
                    return all;
                }

                public List<VersionsEntity> getVersions() {
                    return versions;
                }

                public List<SubjectsEntity> getSubjects() {
                    return subjects;
                }

                public static class AllEntity {
                    private String name;
                    private int value;

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getName() {
                        return name;
                    }

                    public int getValue() {
                        return value;
                    }
                }

                public static class VersionsEntity {
                    private String name;
                    private int value;

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getName() {
                        return name;
                    }

                    public int getValue() {
                        return value;
                    }
                }

                public static class SubjectsEntity {
                    private String name;
                    private int value;

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }

                    public String getName() {
                        return name;
                    }

                    public int getValue() {
                        return value;
                    }
                }
            }

            public static class SortsEntity {
                private String name;
                private String value;

                public void setName(String name) {
                    this.name = name;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public String getValue() {
                    return value;
                }
            }

            public static class FiltersEntity {
                private String name;
                private int value;

                public void setName(String name) {
                    this.name = name;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public int getValue() {
                    return value;
                }
            }

            public static class StarsEntity {
                private String name;
                private int value;

                public void setName(String name) {
                    this.name = name;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public String getName() {
                    return name;
                }

                public int getValue() {
                    return value;
                }
            }
        }

        public static class ListEntity {
            private int book_id;
            private String name;
            private String en_name;
            private String grade;
            private String version;
            private int subject;
            private int star;
            private int category;
            private String cover;

            public void setCategory(int category) {
                this.category = category;
            }

            public int getCategory() {
                return category;
            }

            public void setBook_id(int book_id) {
                this.book_id = book_id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public void setSubject(int subject) {
                this.subject = subject;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getBook_id() {
                return book_id;
            }

            public String getName() {
                return name;
            }

            public String getEn_name() {
                return en_name;
            }

            public String getGrade() {
                return grade;
            }

            public String getVersion() {
                return version;
            }

            public int getSubject() {
                return subject;
            }

            public int getStar() {
                return star;
            }

            public String getCover() {
                return cover;
            }
        }
    }
}
