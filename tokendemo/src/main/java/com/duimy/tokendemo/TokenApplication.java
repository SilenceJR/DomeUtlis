package com.duimy.tokendemo;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @作者: PJ
 * @创建时间: 2017/12/7 / 16:53
 * @描述: 这是一个 TokenApplication 类.
 */
public class TokenApplication extends TinkerApplication {
    protected TokenApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.duimy.tokendemo.TokenApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader",
                false);
    }
}
