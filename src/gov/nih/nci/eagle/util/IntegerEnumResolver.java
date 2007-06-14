package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.studyQueryService.dto.epi.IntegerValueEnum;

public class IntegerEnumResolver {

    public static <T> T resolveEnum(Class<T> enumType, String val) {
        T[] enumConstants = enumType.getEnumConstants();
        for(int i = 0; i < enumConstants.length; i++) {
            if(enumConstants[i] instanceof IntegerValueEnum) {
                int value = Integer.parseInt(val);
                if(((IntegerValueEnum)enumConstants[i]).getValue() == value)
                    return enumConstants[i];
            } else {
                throw new RuntimeException("Enum must implement IntegerValueEnum interface to use this utility");
            }
        }
        return null;
    }
}
