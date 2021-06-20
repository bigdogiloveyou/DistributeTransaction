//package cn.blogxin.dt.client.aop;
//
//import cn.blogxin.dt.client.annotation.TwoPhaseCommit;
//import cn.blogxin.dt.client.context.DTContext;
//import cn.blogxin.dt.client.context.DTContextEnum;
//import cn.blogxin.dt.client.log.entity.Action;
//import cn.blogxin.dt.client.log.enums.ActionStatus;
//import cn.blogxin.dt.client.rm.ResourceManager;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//
//import java.util.Date;
//
///**
// * @author xushu
// */
//public class ActionInterceptor implements MethodInterceptor {
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        if (!DTContext.inTransaction()) {
//            return invocation.proceed();
//        }
//        TwoPhaseCommit annotation = invocation.getMethod().getAnnotation(TwoPhaseCommit.class);
//        if (annotation != null) {
//            String actionName = annotation.name();
//            Action action = new Action();
//            action.setXid((String) DTContext.get(DTContextEnum.XID));
//            action.setName(actionName);
//            action.setStatus(ActionStatus.INIT.getStatus());
//            Date now = new Date();
//            action.setGmtCreate(now);
//            action.setGmtModified(now);
//            ResourceManager.registerAction(action);
//            return invocation.proceed();
//        }
//        return invocation.proceed();
//    }
//
//}
