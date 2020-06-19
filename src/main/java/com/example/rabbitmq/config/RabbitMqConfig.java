package com.example.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            System.out.println("confirmCallback:" + "相关数据" + correlationData);
            System.out.println("confirmCallback:" + "确认情况" + b);
            System.out.println("confirmCallback:" + "原因" + s);
        });

        rabbitTemplate.setReturnCallback((message, i, s, s1, s2) -> {
            System.out.println("ReturnCallback:     " + "消息：" + message);
            System.out.println("ReturnCallback:     " + "回应码：" + i);
            System.out.println("ReturnCallback:     " + "回应信息：" + s);
            System.out.println("ReturnCallback:     " + "交换机：" + s1);
            System.out.println("ReturnCallback:     " + "路由键：" + s2);
        });

        return rabbitTemplate;
    }

}
