package com.shengdangjia.hermesaccount.business;

import com.shengdangjia.hermesaccount.entity.Action;
import com.shengdangjia.hermesaccount.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Configuration
public class ActionBusiness {
    @Autowired
    ActionRepository actionRepository;

    /**
     * 获取所有操作记录
     * @return
     */
    public List<Action> findAll() {
        return actionRepository.findAll();
    }

    /**
     * 添加操作记录
     *
     * @param userId 用户ID
     * @param type   操作类型
     * @return
     */
    public void insert(String userId, short type, String parameter1) {
        Action action = new Action();
        action.id = UUID.randomUUID().toString();
        action.userId = userId;
        action.type = type;
        System.out.println(System.currentTimeMillis());
        action.logTime = new Timestamp(System.currentTimeMillis());
        action.parameter1 = parameter1;

        actionRepository.save(action);
        return;
    }
}
