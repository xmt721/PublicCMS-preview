package com.publiccms.logic.component.task;

import static org.apache.commons.lang3.time.DateUtils.addMinutes;
import static org.apache.commons.lang3.time.DateUtils.addYears;
import static org.apache.commons.lang3.time.DateUtils.addMonths;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.publiccms.common.constants.CmsVersion;
import com.publiccms.logic.component.cache.CacheComponent;
import com.publiccms.logic.service.log.LogLoginService;
import com.publiccms.logic.service.log.LogOperateService;
import com.publiccms.logic.service.log.LogTaskService;
import com.publiccms.logic.service.sys.SysAppTokenService;
import com.publiccms.logic.service.sys.SysEmailTokenService;
import com.publiccms.logic.service.sys.SysUserTokenService;
import com.sanluan.common.base.Base;

@Component
public class ScheduledTaskComponent extends Base {
    @Autowired
    private SysAppTokenService appTokenService;
    @Autowired
    private SysEmailTokenService emailTokenService;
    @Autowired
    private SysUserTokenService userTokenService;
    @Autowired
    private LogLoginService logLoginService;
    @Autowired
    private LogOperateService logOperateService;
    @Autowired
    private LogTaskService logTaskService;
    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 每分钟清理半小时前的token
     */
    @Scheduled(fixedDelay = 60 * 1000L)
    public void clearAppToken() {
        if (CmsVersion.isInitialized() && CmsVersion.isMaster()) {
            Date date = addMinutes(getDate(), -30);
            appTokenService.delete(date);
            emailTokenService.delete(date);
        }
    }

    /**
     * 每天凌晨清理缓存
     */
    @Scheduled(cron = "0 0 0  * * ?")
    public void clearCache() {
        if (CmsVersion.isInitialized()) {
            cacheComponent.clear();
            if (CmsVersion.isMaster()) {
                // 清理3个月前的Token
                userTokenService.delete(addMonths(getDate(), -3));
            }
        }
    }

    /**
     * 每月1号凌晨清理两年以前的日志
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void clearLog() {
        if (CmsVersion.isInitialized() && CmsVersion.isMaster()) {
            Date date = addYears(getDate(), -2);
            logLoginService.delete(null, date);
            logOperateService.delete(null, date);
            logTaskService.delete(null, date);
        }
    }
}
