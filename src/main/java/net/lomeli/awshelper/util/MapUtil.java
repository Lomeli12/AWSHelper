package net.lomeli.awshelper.util;

import java.lang.reflect.Field;
import java.util.*;

public enum MapUtil {
    INSTANCE();
    private List<Class> baseTypes = new ArrayList<>();

    MapUtil() {
        baseTypes.add(short.class);
        baseTypes.add(int.class);
        baseTypes.add(long.class);
        baseTypes.add(float.class);
        baseTypes.add(double.class);
        baseTypes.add(byte.class);
        baseTypes.add(String.class);
        baseTypes.add(char.class);
        baseTypes.add(boolean.class);
    }

    public Map<String, Object> objectToMap(Object object) {
        Map<String, Object> objectMap = new HashMap<>();
        List<Field> fields = getClassFields(object.getClass());
        for (Field f : fields) {
            try {
                f.setAccessible(true);
                Object value = f.get(object);
                if (value == null) continue;
                if (baseTypes.contains(value.getClass()))
                    objectMap.put(f.getName(), value.toString());
                else if (value.getClass().isEnum() || value instanceof Map || value instanceof Collection)
                    objectMap.put(f.getName(), value);
                else
                    objectMap.put(f.getName(), objectToMap(value));
            } catch (IllegalAccessException ex) {
                String className = f.getDeclaringClass().getCanonicalName();
                if (className == null) className = "";
                System.out.printf("Failed to parse %s in %s", f.getName(), className);
                ex.printStackTrace();
            }
        }
        return objectMap;
    }

    public <T> T mapToObject(Map<String, Object> map, Class<T> tClass) {
        T out = null;
        try {
            out = tClass.newInstance();
            List<Field> fields = getClassFields(tClass);
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = map.get(field.getName());
                if (value == null) continue;
                if (baseTypes.contains(value.getClass())) {
                    if (field.getType() == value.getClass()) field.set(out, value);
                } else if (field.getType().isEnum()) {
                    Object trueObj = getEnumValue((Class<? extends Enum>) field.getType(), value.toString());
                    field.set(out, trueObj);
                } else if (value instanceof Map) {
                    if (field.getType().isAssignableFrom(Map.class))
                        field.set(out, value);
                    else
                        field.set(out, mapToObject((Map) value, field.getType()));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return out;
    }

    private <T extends Enum> T getEnumValue(Class<T> cl, String name) {
        for (T constant : cl.getEnumConstants()) {
            if (constant.name().equals(name))
                return constant;
        }
        return null;
    }

    private List<Field> getClassFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null)
            fieldList.addAll(getClassFields(clazz.getSuperclass()));
        fieldList.sort(Comparator.comparing(Field::getName));
        return fieldList;
    }
}
