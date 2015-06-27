package com.owo.base.common;

/**
 * <br>==========================
 * <br> 多元元组类，用于临时使用,目前仅支持２元，３元元组。
 * <br> 公司：九游
 * <br> 开发：wangli
 * <br> 版本：1.0
 * <br> 创建时间：2015/05/05
 * <br>==========================
 */
public class Tuple {
    private Tuple() {
    }

    public static class Tuple2<T1, T2> {
        public T1 item1;
        public T2 item2;

        public Tuple2(T1 item1, T2 item2) {
            this.item1 = item1;
            this.item2 = item2;
        }
    }

    public static class Tuple3<T1, T2, T3> {
        public T1 item1;
        public T2 item2;
        public T3 item3;

        public Tuple3(T1 item1, T2 item2, T3 item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
        }
    }
}
