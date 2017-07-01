package selector;

/**
 * Created by huangyichun on 2017/6/17.
 */
public class InformationSelector {

    private String titleSelect;

    private String timeSelect;

    private String collegeSelect;

    private String picSeclect;

    private String contentSelect;

    public InformationSelector() {
    }

    public InformationSelector(String titleSelect, String timeSelect, String collegeSelect,
                               String picSeclect, String contentSelect) {
        this.titleSelect = titleSelect;
        this.timeSelect = timeSelect;
        this.collegeSelect = collegeSelect;
        this.picSeclect = picSeclect;
        this.contentSelect = contentSelect;
    }

    public InformationSelector(String titleSelect, String timeSelect,
                               String collegeSelect, String contentSelect) {
        this.titleSelect = titleSelect;
        this.timeSelect = timeSelect;
        this.collegeSelect = collegeSelect;
        this.contentSelect = contentSelect;
    }

    public String getTitleSelect() {
        return titleSelect;
    }

    public InformationSelector setTitleSelect(String titleSelect) {
        this.titleSelect = titleSelect;
        return this;
    }

    public String getTimeSelect() {
        return timeSelect;
    }

    public InformationSelector setTimeSelect(String timeSelect) {
        this.timeSelect = timeSelect;
        return this;
    }

    public String getCollegeSelect() {
        return collegeSelect;
    }

    public InformationSelector setCollegeSelect(String collegeSelect) {
        this.collegeSelect = collegeSelect;
        return this;
    }

    public String getPicSeclect() {
        return picSeclect;
    }

    public InformationSelector setPicSeclect(String picSeclect) {
        this.picSeclect = picSeclect;
        return this;
    }

    public String getContentSelect() {
        return contentSelect;
    }

    public InformationSelector setContentSelect(String contentSelect) {
        this.contentSelect = contentSelect;
        return this;
    }
}
