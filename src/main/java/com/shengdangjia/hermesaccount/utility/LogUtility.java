package com.shengdangjia.hermesaccount.utility;

import com.shengdangjia.hermescommon.model.LogMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 日志操作
 */
@Configuration
public class LogUtility {

    private static RabbitTemplate rabbitTemplate;

    /**
     * 队列交换机名称
     */
    static String exchange = "LogExchange";

    /**
     * 队列路由名称
     */
    static String routing = "LogRouting";

    /**
     * 模块名称
     */
    static String module = "hermes-account";

    /**
     * 异常
     * @param action 操作
     * @param message 消息
     */
    public static void exception(String action, String message) {
        LogMessage logMessage = new LogMessage(0, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    /**
     * 错误
     * @param action 操作
     * @param message 消息
     */
    public static void error(String action, String message) {
        LogMessage logMessage = new LogMessage(1, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    /**
     * 警告
     * @param action 操作
     * @param message 消息
     */
    public static void warning(String action, String message) {
        LogMessage logMessage = new LogMessage(2, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    /**
     * 信息
     * @param action 操作
     * @param message 消息
     */
    public static void info(String action, String message) {
        LogMessage logMessage = new LogMessage(3, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    /**
     * 调试
     * @param action 操作
     * @param message 消息
     */
    public static void debug(String action, String message) {
        LogMessage logMessage = new LogMessage(4, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    /**
     * 详细
     * @param action 操作
     * @param message 消息
     */
    public static void verbose(String action, String message) {
        LogMessage logMessage = new LogMessage(5, module, action, message);
        rabbitTemplate.convertAndSend(exchange, routing, logMessage);
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        LogUtility.rabbitTemplate = rabbitTemplate;
    }
}
