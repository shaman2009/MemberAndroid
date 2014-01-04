package com.dandelion.memberandroid.util;

/**
 * Created by Fengxiang on 14-1-3.
 */

import com.dandelion.memberandroid.dao.auto.MerchantInfo;

import java.lang.reflect.Field;


public class AutoGenerator {
    /**
     * @param dst to be overwrite
     * @param src
     */
    public Object assignLeft(Object dst, Object src) {
        if (null == dst || null == src)
            return dst;
        Class<?> dstClass = dst.getClass();
        while (null != dstClass) {
            Class<?> srcClass = src.getClass();
            while (null != srcClass) {
                assginFields(dstClass, dst, srcClass, src);
                srcClass = srcClass.getSuperclass();
            }
            dstClass = dstClass.getSuperclass();
        }
        return dst;
    }

    private void assginFields(Class<?> dstClass, Object dst, Class<?> srcClass,
                              Object src) {
        Field[] dstFields = dstClass.getDeclaredFields();
        Field[] srcFields = srcClass.getDeclaredFields();
        for (Field srcField : srcFields) {
            for (Field dstField : dstFields) {
                if (dstField.getName().equals(srcField.getName())
                        && dstField.getClass().isAssignableFrom(
                        srcField.getClass())) {
                    boolean dstAccess = dstField.isAccessible();
                    boolean srcAccess = srcField.isAccessible();
                    dstField.setAccessible(true);
                    srcField.setAccessible(true);
                    try {
                        dstField.set(dst, srcField.get(src));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    dstField.setAccessible(dstAccess);
                    srcField.setAccessible(srcAccess);
                }
            }
        }
    }

    private String printGetterSettgerFields(Class<?> dstClass, Object dst,
                                            Class<?> srcClass, Object src) {
        Field[] dstFields = dstClass.getDeclaredFields();
        Field[] srcFields = srcClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field srcField : srcFields) {
            for (Field dstField : dstFields) {
                if (dstField.getName().equals(srcField.getName())
                        && dstField.getClass().isAssignableFrom(
                        srcField.getClass())) {
                    String name = dstField.getName();
                    String first = name.substring(0, 1).toUpperCase();
                    String last = name.substring(1);
                    String get = "get" + first + last;
                    String set = "set" + first + last;
                    String dstClassName = dst.getClass().getSimpleName();
                    String dstFirst = dstClassName.substring(0, 1)
                            .toLowerCase();
                    String dstLast = dstClassName.substring(1);
                    String srcClassName = src.getClass().getSimpleName();
                    String srcFirst = srcClassName.substring(0, 1)
                            .toLowerCase();
                    String srcLast = srcClassName.substring(1);
                    String srcName = srcFirst + srcLast;
                    if (!src.getClass().equals(srcClass)) {
                        srcName = "((" + srcClass.getSimpleName() + ") "
                                + srcName + ")";
                    }
                    String dstName = dstFirst + dstLast;
                    if (!dst.getClass().equals(dstClass)) {
                        dstName = "((" + dstClass.getSimpleName() + ") "
                                + dstName + ")";
                    }
                    stringBuilder.append(dstFirst + dstLast + "." + set + "("
                            + srcName + "." + get + "());");
                    stringBuilder.append("\n");
                }
            }
        }
        return stringBuilder.toString();
    }

    public String printGetterSetter(Object dst, Object src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == dst || null == src)
            return stringBuilder.toString();
        Class<?> dstClass = dst.getClass();
        while (null != dstClass) {
            Class<?> srcClass = src.getClass();
            while (null != srcClass) {
                stringBuilder.append(printGetterSettgerFields(dstClass, dst,
                        srcClass, src));
                srcClass = srcClass.getSuperclass();
            }
            dstClass = dstClass.getSuperclass();
        }
        return stringBuilder.toString();
    }

    public String printSetter(Object src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (null == src)
            return stringBuilder.toString();
        Class<?> srcClass = src.getClass();
        while (null != srcClass) {
            stringBuilder.append(printSettgerFields(
                    srcClass, src));
            srcClass = srcClass.getSuperclass();
        }
        return stringBuilder.toString();
    }


    private String printSettgerFields(
            Class<?> srcClass, Object src) {
        Field[] srcFields = srcClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field srcField : srcFields) {
            String name = srcField.getName();
            String first = name.substring(0, 1).toUpperCase();
            String last = name.substring(1);
            String set = "set" + first + last;
            String srcClassName = src.getClass().getSimpleName();
            String srcFirst = srcClassName.substring(0, 1)
                    .toLowerCase();
            String srcLast = srcClassName.substring(1);
            String srcName = srcFirst + srcLast;
            if (!src.getClass().equals(srcClass)) {
                srcName = "((" + srcClass.getSimpleName() + ") "
                        + srcName + ")";
            }
            stringBuilder.append(srcName + "." + set + "("
                    + name + ");");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    private String printGetter(Object src) {
        Class<?> srcClass = src.getClass();
        Field[] srcFields = srcClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field srcField : srcFields) {
            String name = srcField.getName();
            String first = name.substring(0, 1).toUpperCase();
            String last = name.substring(1);
            String get = "get" + first + last;
            String srcClassName = src.getClass().getSimpleName();
            String srcFirst = srcClassName.substring(0, 1)
                    .toLowerCase();
            String srcLast = srcClassName.substring(1);
            String srcName = srcFirst + srcLast;
            if (!src.getClass().equals(srcClass)) {
                srcName = "((" + srcClass.getSimpleName() + ") "
                        + srcName + ")";
            }
            stringBuilder.append(srcName + "." + get + "();");
            stringBuilder.append("\n");
        }
        System.out.print(stringBuilder.toString());
        return stringBuilder.toString();
    }

    static class A {
        private String pra;
        public String pua;
        String a;

        public String getPra() {
            return pra;
        }

        public void setPra(String pra) {
            this.pra = pra;
        }

        public String getPua() {
            return pua;
        }

        public void setPua(String pua) {
            this.pua = pua;
        }

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        @Override
        public String toString() {
            return pra + pua + a;
        }
    }

    static class C extends B {
        private String prC;

        public String getPrC() {
            return prC;
        }

        public void setPrC(String prC) {
            this.prC = prC;
        }
    }

    static class B extends A {
        private String prb;
        public String pub;
        String b;

        public String getPrb() {
            return prb;
        }

        public void setPrb(String prb) {
            this.prb = prb;
        }

        public String getPub() {
            return pub;
        }

        public void setPub(String pub) {
            this.pub = pub;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return super.toString() + prb + pub + b;
        }
    }

    static class JF {
        Field[] fields;

        JF(Field[] fields) {
            this.fields = fields;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Field field : fields) {
                stringBuilder.append(field);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
    }

    static class JO {
        Object object;

        JO(Object object) {
            this.object = object;
        }

        @Override
        public String toString() {
            return object.toString();
        }
    }

    public static void main(String[] args) {
        // System.out.println(new JF(new A().getClass().getFields()));
        // System.out.println(new JF(new A().getClass().getDeclaredFields()));
        // System.out.println(new JF(new B().getClass().getFields()));
        // System.out.println(new JF(new B().getClass().getDeclaredFields()));
        // System.out.println(new JF(new
        // B().getClass().getSuperclass().getDeclaredFields()));
        // System.out.println(new JF(new
        // Object().getClass().getDeclaredFields()));
        // System.out.println(new JO(new A()));
        // System.out.println(new JO(new B()));
        // A a = new A();
        // B b = new B();
        // a.pra = "pra";
        // a.pua = "pua";
        // a.a = "a";
        // b.pua = "puba";
        // b.a = "ba";
        // b.prb = "prb";
        // b.pub = "pub";
        // b.b = "b";
        // System.out.println(new JO(a));
        // System.out.println(new JO(b));
        // System.out.println(new ClassAttributesAssignment().assignLeft(a, b));
        // System.out.println(new ClassAttributesAssignment().assignLeft(b, a));
		System.out.println(new AutoGenerator().printSetter( new MerchantInfo()));
//		System.out.println(new AutoGenerator().printGetterSetter(new GameRoleAchievementInfo(), new GameRoleAchievement()));
//        new AutoGenerator().printGetter(new MerchantInfo());
    }
}
