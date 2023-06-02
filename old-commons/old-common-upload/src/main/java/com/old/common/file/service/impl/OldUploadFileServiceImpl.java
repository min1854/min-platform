package com.old.common.file.service.impl;

import com.old.common.file.domain.OldUploadFile;
import com.old.common.file.mapper.OldUploadFileMapper;
import com.old.common.file.service.OldUploadFileService;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件  old_upload_file
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-05-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldUploadFileServiceImpl extends BaseServiceImpl<OldUploadFileMapper, OldUploadFile> implements OldUploadFileService {


}
