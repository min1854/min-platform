package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldOrder;
import com.old.business.user.service.OldOrderService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 订单表 Controller old_order
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@RestController
@RequestMapping("/oldOrder")
public class OldOrderController {
    @Autowired
    OldOrderService oldOrderService;

    @GetMapping("/page")
    public R<PageData<OldOrder>> page(@ModelAttribute OldOrder oldOrder,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldOrderService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldOrder)));
    }


    @GetMapping("/select")
    public R<List<OldOrder>> select(@ModelAttribute OldOrder oldOrder) {
        List<OldOrder> list = oldOrderService.list(Wrappers.lambdaQuery(oldOrder));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldOrder> getById(@RequestParam("id") Integer id) {
        return R.ok(oldOrderService.getById(id));
    }

    @PostMapping("/updateById")
    public R<?> updateById(@RequestBody OldOrder oldOrder) {
        oldOrderService.updateByKey(oldOrder, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }

    @PostMapping("/save")
    public R<?> save(@RequestBody OldOrder oldOrder) {
        oldOrderService.insert(oldOrder, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


    @PostMapping("/removeById")
    public R<?> removeById(@RequestParam("id") Integer id) {
        oldOrderService.removeById(id, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


}
