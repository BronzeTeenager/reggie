package top.api.common;

/**
 * 设置线程独立副本唯一变量
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static void set(Long id){
        threadLocal.set(id);
    }

    public static Long get(){
        return threadLocal.get();
    }
}
