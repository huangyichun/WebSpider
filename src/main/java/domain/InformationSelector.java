package domain;

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

    public void setTitleSelect(String titleSelect) {
        this.titleSelect = titleSelect;
    }

    public String getTimeSelect() {
        return timeSelect;
    }

    public void setTimeSelect(String timeSelect) {
        this.timeSelect = timeSelect;
    }

    public String getCollegeSelect() {
        return collegeSelect;
    }

    public void setCollegeSelect(String collegeSelect) {
        this.collegeSelect = collegeSelect;
    }

    public String getPicSeclect() {
        return picSeclect;
    }

    public void setPicSeclect(String picSeclect) {
        this.picSeclect = picSeclect;
    }

    public String getContentSelect() {
        return contentSelect;
    }

    public void setContentSelect(String contentSelect) {
        this.contentSelect = contentSelect;
    }
}
