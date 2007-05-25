package gov.nih.nci.eagle.util;

import gov.nih.nci.caintegrator.dto.query.QueryDTO;
import gov.nih.nci.caintegrator.enumeration.StatisticalMethodType;
import gov.nih.nci.caintegrator.service.task.Task;
import gov.nih.nci.caintegrator.studyQueryService.dto.epi.EPIQueryDTO;
import gov.nih.nci.eagle.query.dto.ClassComparisonQueryDTOImpl;

import javax.servlet.http.HttpSession;

public class ManagedBeanUtil {

    public static void clearReport(HttpSession session, Task task) {
        QueryDTO dto = task.getQueryDTO();
        String beanName = null;
        
        if(dto instanceof EPIQueryDTO) {
            beanName = "epiReport";
        }
        if(dto instanceof ClassComparisonQueryDTOImpl) {
            ClassComparisonQueryDTOImpl ccDto = (ClassComparisonQueryDTOImpl)dto;
            if(ccDto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.TTest))
                beanName = "classComparisonReport";
            else if(ccDto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.FTest))
                beanName = "ftestReport";
            else if(ccDto.getStatisticTypeDE().getValueObject().equals(StatisticalMethodType.GLM))
                beanName = "glmReport";
        }

        session.removeAttribute(beanName);

    }
}
