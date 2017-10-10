package cn.com.eju.deal.common.jms.rabbitmq.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class TestQueue
{
    @Autowired
    private Product producer;
    
    //@Value("#{appConfig['mq.queue']}")
    private String queueId = "rms_export_mq";
    
    /**
     * @Description: 消息队列
     * @Author: 
     * @CreateTime: 
     */
    @ResponseBody
    @RequestMapping("/sendQueue")
    public String testQueue()
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", "hello rabbitmq");
            producer.sendQueue(queueId + "_exchange", queueId + "_patt", map);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "发送完毕";
    }
}
