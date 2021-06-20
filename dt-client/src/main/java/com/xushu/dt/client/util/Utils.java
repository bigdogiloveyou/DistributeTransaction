package com.xushu.dt.client.util;

import com.xushu.dt.api.annotation.TwoPhaseCommit;
import com.xushu.dt.api.context.DTParam;
import com.xushu.dt.client.context.ActionContext;
import com.xushu.dt.client.context.DTContext;
import com.xushu.dt.client.context.DTContextEnum;
import com.xushu.dt.client.exception.DTException;
import com.xushu.dt.client.log.entity.Activity;
import com.xushu.dt.client.log.enums.ActionStatus;
import com.xushu.dt.client.log.enums.ActivityStatus;
import com.xushu.dt.client.rm.ActionResource;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @author xushu
 */
public class Utils {

    public static void initDTContext(Activity activity) {
        DTContext.set(DTContextEnum.XID, activity.getXid());
        DTContext.set(DTContextEnum.START_TIME, activity.getStartTime());
        DTContext.set(DTContextEnum.TIMEOUT_TIME, activity.getTimeoutTime());
        DTContext.set(DTContextEnum.ACTIVITY, activity);
        DTContext.set(DTContextEnum.ACTION_CONTEXT, new ActionContext());
    }

    public static ActivityStatus getFinalStatus(Activity activity) {
        if (ActivityStatus.INIT.getStatus() == activity.getStatus() && activity.getTimeoutTime().before(new Date())) {
            return ActivityStatus.ROLLBACK;
        } else if (ActivityStatus.COMMIT.getStatus() == activity.getStatus()) {
            return ActivityStatus.COMMIT_FINISH;
        }
        return null;
    }

    public static String getActionName(Class<?> interfaceClass, Method method, TwoPhaseCommit annotation) {
        if (annotation == null || StringUtils.isBlank(annotation.name())) {
            return interfaceClass.getSimpleName() + "#" + method.getName();
        }
        return annotation.name();
    }

    public static Method getTwoPhaseMethodByName(String methodName, Method[] methods) {
        Method result = null;
        for (Method method : methods) {
            if (StringUtils.equals(method.getName(), methodName)) {
                if (result == null) {
                    result = method;
                } else {
                    throw new DTException("禁止二阶段方法被重载");
                }
            }
        }
        return result;
    }

    public static Method getTwoPhaseMethodByActionStatus(ActionResource actionResource, ActionStatus actionStatus) {
        if (actionStatus == ActionStatus.COMMIT) {
            return actionResource.getConfirmMethod();
        } else if (actionStatus == ActionStatus.ROLLBACK) {
            return actionResource.getCancelMethod();
        }
        throw new DTException("未找到二阶段方法");
    }

    public static Object[] getTwoPhaseMethodParam(Method method, String arguments, DTParam dtParam) {
        List<Object> result = Lists.newArrayList();
        result.add(dtParam);
        Class<?>[] parameterTypes = method.getParameterTypes();
        JSONArray jsonArray = JSONArray.parseArray(arguments);
        for (int i = 0; i < jsonArray.size(); i++) {
            Class<?> parameterType = parameterTypes[i + 1];
            Object param = jsonArray.getObject(i, parameterType);
            result.add(param);
        }
        return result.toArray();
    }

}
