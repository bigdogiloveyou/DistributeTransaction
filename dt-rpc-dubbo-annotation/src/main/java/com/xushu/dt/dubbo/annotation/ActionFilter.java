package com.xushu.dt.dubbo.annotation;

import com.xushu.dt.api.annotation.TwoPhaseCommit;
import com.xushu.dt.client.context.DTContext;
import com.xushu.dt.client.context.DTContextEnum;
import com.xushu.dt.client.exception.DTException;
import com.xushu.dt.client.log.entity.Action;
import com.xushu.dt.client.log.enums.ActionStatus;
import com.xushu.dt.client.rm.ResourceManager;
import com.xushu.dt.client.util.Utils;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.*;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 这个过滤器是调用方会调用，即事务发起方（开启主事务的那方）
 *
 * @author xushu
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER, order = Integer.MIN_VALUE)
public class ActionFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!DTContext.inTransaction()) {
            return invoker.invoke(invocation);
        }
        try {
            String serviceName = invocation.getServiceName();
            Class<?> aClass = Class.forName(serviceName, true, Thread.currentThread().getContextClassLoader());
            Method method = aClass.getDeclaredMethod(invocation.getMethodName(), invocation.getParameterTypes());
            TwoPhaseCommit annotation = method.getAnnotation(TwoPhaseCommit.class);
            if (annotation != null) {
                Date now = new Date();
                Action action = new Action();
                action.setXid(DTContext.get(DTContextEnum.XID));
                action.setName(Utils.getActionName(aClass, method, annotation));
                action.setStatus(ActionStatus.INIT.getStatus());
                action.setArguments(JSONArray.toJSONString(invocation.getArguments()));
                action.setGmtCreate(now);
                action.setGmtModified(now);
                ExtensionFactory objectFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
                ResourceManager resourceManager = objectFactory.getExtension(ResourceManager.class, "dtResourceManager");
                resourceManager.registerAction(action);
                //todo test
//                if (serviceName.contains("Coupon")) {
//                    int i = 10/0;
//                }
            }
        } catch (Exception e) {
            log.error("ActionFilter error", e);
            throw new DTException("插入分支事务失败");
        }
        return invoker.invoke(invocation);
    }

}
