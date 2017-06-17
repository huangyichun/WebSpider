package pipeline;

import domain.ResultItems;

import java.io.File;
import java.util.Map;

/**
 * 使用文件存储
 * Created by huangyichun on 2017/6/17.
 */
public class ConsolePipeline implements Pipeline{
    @Override
    public void process(ResultItems resultItems) {
        Map<String, Object> map = resultItems.getFields();
//        File file = new File("E:/spider");
        if(map.size() > 0) {
            System.out.println(map.get("title"));
        }
    }
}
