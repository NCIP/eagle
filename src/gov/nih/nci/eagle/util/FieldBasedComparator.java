package gov.nih.nci.eagle.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;

public class FieldBasedComparator implements Comparator {

    private String field;
    private Boolean ascending;
    public FieldBasedComparator(String field, Boolean ascending) {
        this.field = field;
        this.ascending = ascending;
    }
    
    public int compare(Object o1, Object o2) {
        String methodName = null;
        Boolean array = false;
        Integer index = null;
        if(field.indexOf('[') == -1) {
            methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
        } else {
            methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1, field.indexOf('['));
            array = true;
            index = Integer.parseInt(field.substring(field.indexOf('[') + 1, field.indexOf('[')  + 2));
        }
        
        try {
            Method m1 = o1.getClass().getMethod(methodName, new Class[]{});
            Object val1 = m1.invoke(o1, new Object[]{});
            Method m2 = o2.getClass().getMethod(methodName, new Class[]{});
            Object val2 = m2.invoke(o2, new Object[]{});
            if(array) {
                double[] vals1 = (double[])val1;
                double[] vals2 = (double[])val2;
                val1 = vals1[index];
                val2 = vals2[index];
            }
            Comparable c1 = (Comparable)val1;
            Comparable c2 = (Comparable)val2;
            if(ascending) {
                return c1.compareTo(c2);
            } else {
                return c2.compareTo(c1);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public String getField() {
        return field;
    }

}
