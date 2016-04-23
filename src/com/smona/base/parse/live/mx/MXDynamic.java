package com.smona.base.parse.live.mx;

import com.smona.base.parse.live.api.AbstractDynamic;
import com.smona.base.parse.live.common.Constants;

public class MXDynamic extends AbstractDynamic {
    protected String getAddFile() {
        return Constants.MX_PROPERTIES;
    }
    
    protected boolean noNeedModify() {
        return false;
    }
}
