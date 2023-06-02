package com.old.business.user;

import com.old.business.user.domain.OldPost;
import com.old.business.user.domain.req.RoleDataScopeReq;
import org.junit.jupiter.api.Test;

public class JunitTests {

    @Test
    public void testAppend() {
        OldPost oldPost = null;
        appendDeleteFlag(oldPost);
        // 不会，
        System.out.println(oldPost);
    }

    private void appendDeleteFlag(OldPost oldPost) {
        if (oldPost == null) {
            oldPost = new OldPost();
        }

        if (oldPost.getDeleteFlag() == null) {
            oldPost.setDeleteFlag(false);
        }
    }

    @Test
    public void testRecordBuild() {
        RoleDataScopeReq req = RoleDataScopeReq.builder()
                .dataScope(null)
                .id(1)
                .build();

        System.out.println(req);

        // @FieldNameConstants 这个对于 record 不生效
    }
}
