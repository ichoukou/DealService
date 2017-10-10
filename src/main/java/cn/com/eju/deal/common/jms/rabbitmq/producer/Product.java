package cn.com.eju.deal.common.jms.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**   
* 消息队列发送者
* @author li_xiaodong
* @date 2017年6月6日 下午3:15:15
*/
@Service
public class Product
{
    @Autowired
    private AmqpTemplate amqpTemplate;
    
    RabbitTemplate rb = new RabbitTemplate();
    
    public void sendQueue(String exchange_key, String queue_key, Object object) {
        // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
        amqpTemplate.convertAndSend(exchange_key, queue_key, object);
    }
}
