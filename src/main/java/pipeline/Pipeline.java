package pipeline;

import domain.ResultItems;

/**
 * 存储resultItems到数据库或者文件等
 * Created by huangyichun on 2017/6/17.
 */
public interface Pipeline {

    /**
     * Process extracted results.
     * @param resultItems resultItems
     */
    void process(ResultItems resultItems);
}
