package com.smona.base.parse.live.vlife;

import com.smona.base.parse.live.api.AbstractDynamic;
import com.smona.base.parse.live.common.Constants;

public class VLifeDynamic extends AbstractDynamic {
    protected String getAddFile() {
        return Constants.VLIFE_PROPERTIES;
    }

    protected boolean noNeedModify() {
        return true;
    }

}
