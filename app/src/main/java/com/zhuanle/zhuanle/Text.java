package com.zhuanle.zhuanle;

import com.socks.library.KLog;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//启动application的方法耗时

@Aspect
public class Text {
    @Around("call(*com.zhuanle.zhuanle.MyApplication.**(..))")
    public void aspectBefore(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        long l = System.currentTimeMillis();
        try {

            joinPoint.proceed(); //开始
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        KLog.e("启动耗时 方法名：",signature.toShortString()+"，耗时："+(System.currentTimeMillis()-l));
    }
}
