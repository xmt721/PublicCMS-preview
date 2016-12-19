package com.publiccms.views.directive.api;

import static com.sanluan.common.tools.RequestUtils.getIpAddress;

//Generated 2015-5-10 17:54:56 by com.sanluan.common.source.SourceMaker

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.common.base.AbstractAppDirective;
import com.publiccms.entities.sys.SysApp;
import com.publiccms.entities.sys.SysAppClient;
import com.publiccms.entities.sys.SysAppClientId;
import com.publiccms.entities.sys.SysUser;
import com.publiccms.logic.service.sys.SysAppClientService;
import com.sanluan.common.handler.RenderHandler;

@Component
public class AppClientDirective extends AbstractAppDirective {

    @Override
    public void execute(RenderHandler handler, SysApp app, SysUser user) throws IOException, Exception {
        String uuid = handler.getString("uuid");
        String clientVersion = handler.getString("clientVersion");
        if (notEmpty(uuid)) {
            SysAppClientId sysAppClientId = new SysAppClientId(getSite(handler).getId(), app.getChannel(), uuid);
            SysAppClient appClient = appClientService.getEntity(sysAppClientId);
            if (empty(appClient)) {
                appClient = new SysAppClient(sysAppClientId, getDate(), false);
                appClient.setClientVersion(clientVersion);
                appClient.setLastLoginIp(getIpAddress(handler.getRequest()));
                appClientService.save(appClient);
            } else {
                appClientService.updateLastLogin(sysAppClientId, clientVersion, getIpAddress(handler.getRequest()));
            }
        }
    }

    @Autowired
    private SysAppClientService appClientService;

    @Override
    public boolean needUserToken() {
        return false;
    }

    @Override
    public boolean needAppToken() {
        return true;
    }
}